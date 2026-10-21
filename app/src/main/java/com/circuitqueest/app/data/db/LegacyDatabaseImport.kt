package com.circuitqueest.app.data.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.circuitqueest.app.data.db.entity.QuizResult
import com.circuitqueest.app.data.db.entity.TopicProgress
import java.io.File

/**
 * v1.0–v2.0 stored progress in [AppDatabase.LEGACY_DATABASE_NAME]. v2.2 switched to
 * [AppDatabase.DATABASE_NAME] without moving the data, so players who updated from those
 * versions started from zero. When the database opens, this copies the old file's progress
 * in (keeping the better result per topic) and renames the old file so it runs only once.
 *
 * The old file has the same tables as schema version 1. The merge is idempotent, so if the
 * rename fails the next launch repeats it without duplicating anything.
 */
class LegacyDatabaseImport(private val legacyFile: File) : RoomDatabase.Callback() {

    override fun onOpen(db: SupportSQLiteDatabase) {
        if (!legacyFile.exists()) return
        val legacy = try {
            readLegacy(legacyFile)
        } catch (e: SQLiteException) {
            Log.w(TAG, "Couldn't read ${legacyFile.name}; leaving it in place", e)
            return
        }

        db.beginTransaction()
        try {
            legacy.progress.forEach { old ->
                val merged = mergeProgress(readProgress(db, old.topicId), old)
                db.execSQL(
                    "INSERT OR REPLACE INTO topic_progress (topicId, lessonCompleted, quizCompleted, " +
                        "bestScore, totalQuestions, xpEarned, lastAccessedTimestamp) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    arrayOf<Any>(
                        merged.topicId, merged.lessonCompleted.toInt(), merged.quizCompleted.toInt(),
                        merged.bestScore, merged.totalQuestions, merged.xpEarned, merged.lastAccessedTimestamp
                    )
                )
            }
            legacy.results.forEach { r ->
                db.execSQL(
                    "INSERT INTO quiz_results (topicId, score, totalQuestions, timestamp) " +
                        "SELECT ?, ?, ?, ? WHERE NOT EXISTS " +
                        "(SELECT 1 FROM quiz_results WHERE topicId = ? AND timestamp = ?)",
                    arrayOf<Any>(r.topicId, r.score, r.totalQuestions, r.timestamp, r.topicId, r.timestamp)
                )
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        retire(legacyFile)
        Log.i(TAG, "Imported ${legacy.progress.size} topics and ${legacy.results.size} quiz results")
    }

    private class Legacy(val progress: List<TopicProgress>, val results: List<QuizResult>)

    private fun readLegacy(file: File): Legacy {
        // Read-write so SQLite can fold a leftover WAL into the file; close() checkpoints it.
        val old = SQLiteDatabase.openDatabase(file.path, null, SQLiteDatabase.OPEN_READWRITE)
        try {
            val progress = old.rawQuery("SELECT * FROM topic_progress", null).use { c ->
                generateSequence { if (c.moveToNext()) c.toProgress() else null }.toList()
            }
            val results = old.rawQuery("SELECT * FROM quiz_results", null).use { c ->
                generateSequence { if (c.moveToNext()) c.toQuizResult() else null }.toList()
            }
            return Legacy(progress, results)
        } finally {
            old.close()
        }
    }

    private fun readProgress(db: SupportSQLiteDatabase, topicId: String): TopicProgress? =
        db.query("SELECT * FROM topic_progress WHERE topicId = ?", arrayOf<Any>(topicId)).use { c ->
            if (c.moveToFirst()) c.toProgress() else null
        }

    /** Keeps the old file (renamed) rather than deleting it, in case the import ever needs redoing. */
    private fun retire(file: File) {
        File(file.path + "-shm").delete()
        File(file.path + "-journal").delete()
        val wal = File(file.path + "-wal")
        if (wal.exists()) wal.renameTo(File(file.path + IMPORTED_SUFFIX + "-wal"))
        if (!file.renameTo(File(file.path + IMPORTED_SUFFIX))) {
            Log.w(TAG, "Couldn't rename ${file.name}; the import will repeat (harmlessly) next launch")
        }
    }

    companion object {
        private const val TAG = "LegacyDatabaseImport"
        const val IMPORTED_SUFFIX = ".imported"

        /** The better of each field, so neither copy's progress is lost. */
        fun mergeProgress(current: TopicProgress?, legacy: TopicProgress): TopicProgress =
            if (current == null) {
                legacy
            } else {
                TopicProgress(
                    topicId = current.topicId,
                    lessonCompleted = current.lessonCompleted || legacy.lessonCompleted,
                    quizCompleted = current.quizCompleted || legacy.quizCompleted,
                    bestScore = maxOf(current.bestScore, legacy.bestScore),
                    totalQuestions = maxOf(current.totalQuestions, legacy.totalQuestions),
                    xpEarned = maxOf(current.xpEarned, legacy.xpEarned),
                    lastAccessedTimestamp = maxOf(current.lastAccessedTimestamp, legacy.lastAccessedTimestamp)
                )
            }

        private fun Boolean.toInt() = if (this) 1 else 0

        private fun Cursor.toProgress() = TopicProgress(
            topicId = getString(getColumnIndexOrThrow("topicId")),
            lessonCompleted = getInt(getColumnIndexOrThrow("lessonCompleted")) != 0,
            quizCompleted = getInt(getColumnIndexOrThrow("quizCompleted")) != 0,
            bestScore = getInt(getColumnIndexOrThrow("bestScore")),
            totalQuestions = getInt(getColumnIndexOrThrow("totalQuestions")),
            xpEarned = getInt(getColumnIndexOrThrow("xpEarned")),
            lastAccessedTimestamp = getLong(getColumnIndexOrThrow("lastAccessedTimestamp"))
        )

        private fun Cursor.toQuizResult() = QuizResult(
            topicId = getString(getColumnIndexOrThrow("topicId")),
            score = getInt(getColumnIndexOrThrow("score")),
            totalQuestions = getInt(getColumnIndexOrThrow("totalQuestions")),
            timestamp = getLong(getColumnIndexOrThrow("timestamp"))
        )
    }
}
