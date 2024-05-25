package com.chaidar.storyappportofolio.backend.data.respository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.chaidar.storyappportofolio.backend.api.ApiService
import com.chaidar.storyappportofolio.backend.database.StoryDatabase
import com.chaidar.storyappportofolio.backend.response.ListStoryItem

class StoryRepository (

    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
){

    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
//            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
//                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

}