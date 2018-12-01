package ru.kcoder.weatherhelper.data.database.room

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(list: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(item: T)

    @Delete
    fun delete(item: T)
}