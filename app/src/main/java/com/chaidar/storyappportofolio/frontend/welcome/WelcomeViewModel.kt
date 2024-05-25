package com.chaidar.storyappportofolio.frontend.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chaidar.storyappportofolio.backend.data.model.UserModel
import com.chaidar.storyappportofolio.backend.data.preferences.UserPreference

class WelcomeViewModel(private val pref: UserPreference) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return pref.getSession().asLiveData()
    }
}