package com.circuitqueest.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.circuitqueest.app.data.db.entity.StudyDay
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDayDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(day: StudyDay)

    @Query("SELECT epochDay FROM study_days ORDER BY epochDay")
    fun getDays(): Flow<List<Long>>
}
