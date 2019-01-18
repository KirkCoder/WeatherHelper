package ru.kcoder.weatherhelper.data.reposiries.settings

import ru.kcoder.weatherhelper.data.database.settings.SettingsSource
import ru.kcoder.weatherhelper.data.entity.settings.Settings

class SettingsRepositoryImpl(
    private val settings: SettingsSource
) : SettingsRepository {

    override fun getSettings(): Settings {
        return settings.getSettings()
    }
}