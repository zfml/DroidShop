package com.schoolproject.droidshop.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.schoolproject.droidshop.data.local.dao.CartDao
import com.schoolproject.droidshop.data.local.dao.FavouriteDao
import com.schoolproject.droidshop.data.model.CartEntity
import com.schoolproject.droidshop.data.model.FavouriteEntity

@Database(
    entities = [CartEntity::class,FavouriteEntity::class],
    version = 1
)
abstract class DroidShopDatabase  : RoomDatabase(){
    abstract fun cartDao(): CartDao
    abstract fun favouriteDao(): FavouriteDao
}