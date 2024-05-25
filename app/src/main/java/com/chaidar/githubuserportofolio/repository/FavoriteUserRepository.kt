package com.chaidar.githubuserportofolio.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.chaidar.githubuserportofolio.database.FavoriteRoomDatabase
import com.chaidar.githubuserportofolio.database.FavoriteUser
import com.chaidar.githubuserportofolio.database.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUser()

    fun insert(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.insert(favoriteUser)}
    }

    fun delete(favoriteUser: FavoriteUser){
        executorService.execute{mFavoriteUserDao.delete(favoriteUser)}
    }

    fun update(favoriteUser: FavoriteUser){
        executorService.execute { mFavoriteUserDao.update(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String): Boolean {
        return executorService.submit<Boolean> {
            mFavoriteUserDao.isFavorite(username)
        }.get()
    }
}