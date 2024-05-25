package com.chaidar.storyappportofolio.backend.di

import android.content.Context
import com.chaidar.storyappportofolio.backend.api.ApiConfig
import com.chaidar.storyappportofolio.backend.data.respository.StoryRepository
import com.chaidar.storyappportofolio.backend.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getService()
        return StoryRepository(database, apiService)
    }
}