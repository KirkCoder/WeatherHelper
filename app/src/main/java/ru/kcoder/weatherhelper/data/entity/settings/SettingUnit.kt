package ru.kcoder.weatherhelper.data.entity.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_unit")
data class SettingUnit(
    @PrimaryKey
    val id: Int,
    val name: String,
    var valueId: Int
)