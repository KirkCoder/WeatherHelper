package ru.kcoder.weatherhelper.data.database.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(item: T)

    @Delete
    fun delete(item: T)
}