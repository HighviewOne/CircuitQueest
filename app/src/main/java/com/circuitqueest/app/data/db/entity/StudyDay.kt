package com.circuitqueest.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A local calendar day ([java.time.LocalDate.toEpochDay]) on which the player finished a
 * lesson, submitted a quiz or answered a review question. Streaks are counted from these.
 */
@Entity(tableName = "study_days")
data class StudyDay(
    @PrimaryKey val epochDay: Long
)
