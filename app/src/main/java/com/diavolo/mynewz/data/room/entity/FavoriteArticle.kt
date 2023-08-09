package com.diavolo.mynewz.data.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Written with passion by Ikhsan Hidayat on 09/08/2023.
 */
@Parcelize
@Entity(tableName = "favArticle")
data class FavoriteArticle(
    @PrimaryKey
    var idArticle: Long? = null,
    @ColumnInfo(name = "title")
    var title: String? = null
) : Parcelable