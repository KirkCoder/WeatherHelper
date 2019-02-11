package ru.kcoder.weatherhelper.data.database.settings

import android.content.Context
import android.preference.PreferenceManager
import ru.kcoder.weatherhelper.data.entity.settings.Settings
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class SettingsSourceImpl(
    private val context: Context
) : SettingsSource {

    override fun getSettings(): Settings {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        return Settings().apply {
            degreeThumbnail = sharedPreferences.getString(
                context.getString(R.string.settings_unit_key), Settings.CELSIUS
            ) ?: Settings.CELSIUS
            degreeDifference = if (degreeThumbnail == Settings.CELSIUS) {
                context.getString(R.string.settings_degree_difference_celsius).toDouble()
            } else {
                context.getString(R.string.settings_degree_difference_kelvin).toDouble()
            }
            maxHourPoints = sharedPreferences.getString(
                context.getString(R.string.settings_hours_key),
                context.getString(R.string.settings_hours_default)
            )?.toInt() ?: Settings.DEFAULT_HOURS_POINTS
            updateTime = context.getString(R.string.settings_bg_update_time).toLong()
            startNight = context.resources.getInteger(R.integer.settings_start_night)
            endNight = context.resources.getInteger(R.integer.settings_end_night)
            serverTimeJump = context.resources.getInteger(R.integer.settings_server_time_jump)
        }
    }
}