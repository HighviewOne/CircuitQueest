package com.circuitqueest.app.data.db.entity

import androidx.room.Entity

/**
 * A question the player last answered wrong. Question ids are only unique within a
 * topic (e.g. "sp_mc1" exists in two topics), hence the composite key.
 */
@Entity(tableName = "missed_questions", primaryKeys = ["topicId", "questionId"])
data class MissedQuestion(
    val topicId: String,
    val questionId: String,
    val missedAt: Long = System.currentTimeMillis()
)
