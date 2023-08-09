package com.diavolo.mynewz.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diavolo.mynewz.data.room.entity.FavoriteArticle

/**
 * Written with passion by Ikhsan Hidayat on 09/08/2023.
 */
@Dao
interface FavoriteArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteArticle(favRecipe: FavoriteArticle) : Long

    @Delete
    suspend fun deleteFavoriteArticle(favRecipe: FavoriteArticle) : Int

    @Query("SELECT * FROM favArticle")
    suspend fun getAllFavoriteArticle(): List<FavoriteArticle>
}