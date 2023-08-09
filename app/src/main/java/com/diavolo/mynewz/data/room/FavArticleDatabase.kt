package com.diavolo.mynewz.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diavolo.mynewz.data.room.dao.FavoriteArticleDao
import com.diavolo.mynewz.data.room.entity.FavoriteArticle

/**
 * Written with passion by Ikhsan Hidayat on 09/08/2023.
 */
@Database(entities = [FavoriteArticle::class], version = 1, exportSchema = false)
abstract class FavArticleDatabase : RoomDatabase() {
    abstract fun favoriteArticleDao(): FavoriteArticleDao
}