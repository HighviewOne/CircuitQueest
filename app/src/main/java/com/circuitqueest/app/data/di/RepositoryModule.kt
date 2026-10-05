package com.circuitqueest.app.data.di

import android.content.Context
import androidx.room.Room
import com.circuitqueest.app.data.db.AppDatabase
import com.circuitqueest.app.data.repository.ProgressRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideProgressRepository(database: AppDatabase): ProgressRepository =
        ProgressRepository(
            progressDao = database.progressDao(),
            quizResultDao = database.quizResultDao(),
            missedQuestionDao = database.missedQuestionDao()
        )
}
