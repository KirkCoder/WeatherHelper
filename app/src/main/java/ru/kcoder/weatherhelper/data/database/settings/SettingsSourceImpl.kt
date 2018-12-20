package ru.kcoder.weatherhelper.data.database.settings

import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.entity.settings.Settings

class SettingsSourceImpl(
    private val database: WeatherHelperRoomDb
): SettingsSource {
    override fun getSettings(): Settings {
        return Settings()
    }
}