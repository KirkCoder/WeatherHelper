package ru.kcoder.weatherhelper.data.database.settings

import ru.kcoder.weatherhelper.data.entity.settings.Settings


interface SettingsSource {
    fun getSettings(): Settings
}