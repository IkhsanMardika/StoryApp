package com.karyadika.storyapp.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(vararg storyModel: StoryModel)

    @Query("SELECT * FROM story_db")
    fun getAllStories(): PagingSource<Int, StoryModel>

    @Query("DELETE FROM story_db")
    fun deleteAll()
}