package com.circuitqueest.app.data.di

import android.content.Context
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
    fun provideProgressRepository(
        @ApplicationContext context: Context
    ): ProgressRepository {
        val database = AppDatabase.getDatabase(context)
        return ProgressRepository(
            progressDao = database.progressDao(),
            quizResultDao = database.quizResultDao()
        )
    }
}
