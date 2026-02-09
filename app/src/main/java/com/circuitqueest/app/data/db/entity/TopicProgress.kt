package com.circuitqueest.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_progress")
data class TopicProgress(
    @PrimaryKey
    val topicId: String,
    val lessonCompleted: Boolean = false,
    val quizCompleted: Boolean = false,
    val bestScore: Int = 0,
    val totalQuestions: Int = 0,
    val xpEarned: Int = 0,
    val lastAccessedTimestamp: Long = 0L
)
