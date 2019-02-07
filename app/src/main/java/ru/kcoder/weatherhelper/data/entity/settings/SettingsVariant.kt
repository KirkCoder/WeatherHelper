package ru.kcoder.weatherhelper.data.entity.settings

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_values")
data class SettingsVariant(
    @PrimaryKey
    val id: Int,
    val settingsUnitId: Int,
    val name: String,
    val value: Int
)