package com.chaidar.githubuserportofolio.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)
    @Delete
    fun delete(favoriteUser: FavoriteUser)
    @Query("SELECT * from favoriteUser ORDER BY username ASC")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT EXISTS(SELECT 1 FROM FavoriteUser WHERE username = :username LIMIT 1)")
    fun isFavorite(username: String): Boolean
}