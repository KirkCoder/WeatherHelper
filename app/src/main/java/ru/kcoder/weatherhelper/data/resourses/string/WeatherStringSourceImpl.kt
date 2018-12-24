package ru.kcoder.weatherhelper.data.resourses.string

import android.content.Context
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class WeatherStringSourceImpl(
    private val context: Context
) : WeatherStringSource {

    private val stringMap = mapOf(
        200 to R.string.thunderstorm_with_light_rain,
        201 to R.string.thunderstorm_with_rain,
        202 to R.string.thunderstorm_with_heavy_rain,
        210 to R.string.light_thunderstorm,
        211 to R.string.thunderstorm,
        212 to R.string.heavy_thunderstorm,
        221 to R.string.ragged_thunderstorm,
        230 to R.string.thunderstorm_with_light_drizzle,
        231 to R.string.thunderstorm_with_drizzle,
        232 to R.string.thunderstorm_with_heavy_drizzle,
        300 to R.string.light_intensity_drizzle,
        301 to R.string.drizzle,
        302 to R.string.heavy_intensity_drizzle,
        310 to R.string.light_intensity_drizzle_rain,
        311 to R.string.drizzle_rain,
        312 to R.string.heavy_intensity_drizzle_rain,
        313 to R.string.shower_rain_and_drizzle,
        314 to R.string.heavy_shower_rain_and_drizzle,
        321 to R.string.shower_drizzle,
        500 to R.string.light_rain,
        501 to R.string.moderate_rain,
        502 to R.string.heavy_intensity_rain,
        503 to R.string.very_heavy_rain,
        504 to R.string.extreme_rain,
        511 to R.string.freezing_rain,
        520 to R.string.light_intensity_shower_rain,
        521 to R.string.shower_rain,
        522 to R.string.heavy_intensity_shower_rain,
        531 to R.string.ragged_shower_rain,
        600 to R.string.light_snow,
        601 to R.string.snow,
        602 to R.string.heavy_snow,
        611 to R.string.sleet,
        612 to R.string.shower_sleet,
        615 to R.string.light_rain_and_snow,
        616 to R.string.rain_and_snow,
        620 to R.string.light_shower_snow,
        621 to R.string.shower_snow,
        622 to R.string.heavy_shower_snow,
        701 to R.string.mist,
        711 to R.string.smoke,
        721 to R.string.haze,
        731 to R.string.sand_dust_whirls,
        741 to R.string.fog,
        751 to R.string.sand,
        761 to R.string.dust,
        762 to R.string.volcanic_ash,
        771 to R.string.squalls,
        781 to R.string.tornado,
        800 to R.string.clear_sky,
        801 to R.string.few_clouds,
        802 to R.string.scattered_clouds,
        803 to R.string.broken_clouds,
        804 to R.string.overcast_clouds
    )

    override fun getDescriptionByCod(cod: Int): String {
        return context.getString(stringMap[cod] ?: R.string.empty)
    }

    override fun getWindDescription(): String {
        return context.getString(R.string.wind)
    }

    override fun getHumidityDescription(): String {
        return context.getString(R.string.humidity)
    }
}