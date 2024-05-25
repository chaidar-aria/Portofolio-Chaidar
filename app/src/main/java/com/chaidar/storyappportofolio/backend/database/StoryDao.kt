package com.chaidar.storyappportofolio.backend.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chaidar.storyappportofolio.backend.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: List<ListStoryItem>)

    @Query("SELECT * FROM liststory")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM listStory")
    suspend fun deleteAll()
}