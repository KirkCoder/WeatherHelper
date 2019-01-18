package ru.kcoder.weatherhelper.data.reposiries.settings

import ru.kcoder.weatherhelper.data.entity.settings.Settings

interface SettingsRepository {
    fun getSettings(): Settings
}