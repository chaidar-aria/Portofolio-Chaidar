package com.chaidar.githubuserportofolio.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaidar.githubuserportofolio.database.FavoriteUser
import com.chaidar.githubuserportofolio.repository.FavoriteUserRepository

class FavoriteUserAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean>
        get() = _isFavoriteLiveData

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun update(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.update(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): Boolean {
        return mFavoriteUserRepository.getFavoriteUserByUsername(username)
    }
}