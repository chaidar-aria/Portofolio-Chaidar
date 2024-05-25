package com.chaidar.storyappsubmis.frontend.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chaidar.storyappportofolio.backend.data.respository.StoryRepository
import com.chaidar.storyappportofolio.backend.di.Injection
import com.chaidar.storyappportofolio.backend.response.ListStoryItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(storyRepository: StoryRepository) :
    ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return when {
                modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(Injection.provideRepository(context)) as T
                }

                else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

}