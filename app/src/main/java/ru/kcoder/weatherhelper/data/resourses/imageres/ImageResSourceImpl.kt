package ru.kcoder.weatherhelper.data.resourses.imageres

import ru.kcoder.weatherhelper.ru.weatherhelper.R

class ImageResSourceImpl : ImageResSource {

    override fun getImageIdByCod(cod: Int?, isDay: Boolean): Int {
        return if (isDay) icoIdMapDay[cod] ?: R.drawable.ic_sun
        else icoIdMapNight[cod] ?: R.drawable.ic_crescent
    }

    override fun getColoredImageIdByCod(cod: Int?, isDay: Boolean): Int {
        return if (isDay) icoIdMapDayColored[cod] ?: R.drawable.ic_sun
        else icoIdMapNightColored[cod] ?: R.drawable.ic_crescent
    }

    private val icoIdMapDay = mapOf(
        200 to R.drawable.storm,
        201 to R.drawable.storm,
        202 to R.drawable.storm,
        210 to R.drawable.storm,
        211 to R.drawable.storm,
        212 to R.drawable.storm,
        221 to R.drawable.storm,
        230 to R.drawable.storm,
        231 to R.drawable.storm,
        232 to R.drawable.storm,
        300 to R.drawable.drizzle,
        301 to R.drawable.drizzle,
        302 to R.drawable.drizzle,
        310 to R.drawable.drizzle,
        311 to R.drawable.drizzle,
        312 to R.drawable.drizzle,
        313 to R.drawable.drizzle,
        314 to R.drawable.drizzle,
        321 to R.drawable.drizzle,
        500 to R.drawable.rain,
        501 to R.drawable.rain,
        502 to R.drawable.rain,
        503 to R.drawable.rain,
        504 to R.drawable.rain
        ,
        511 to R.drawable.drizzle,
        520 to R.drawable.drizzle,
        521 to R.drawable.drizzle,
        522 to R.drawable.drizzle,
        531 to R.drawable.drizzle,
        600 to R.drawable.ic_snow,
        601 to R.drawable.ic_snow,
        602 to R.drawable.ic_snow,
        611 to R.drawable.ic_snow,
        612 to R.drawable.ic_snow,
        615 to R.drawable.ic_snow,
        616 to R.drawable.ic_snow,
        620 to R.drawable.ic_snow,
        621 to R.drawable.ic_snow,
        622 to R.drawable.ic_snow,
        701 to R.drawable.mist,
        711 to R.drawable.mist,
        721 to R.drawable.mist,
        731 to R.drawable.mist,
        741 to R.drawable.mist,
        751 to R.drawable.mist,
        761 to R.drawable.mist,
        762 to R.drawable.mist,
        771 to R.drawable.mist,
        781 to R.drawable.mist,
        800 to R.drawable.ic_sun,
        801 to R.drawable.ic_fiew_cloud,
        802 to R.drawable.ic_cloud,
        803 to R.drawable.ic_brocken_clouds,
        804 to R.drawable.ic_brocken_clouds
    )

    private val icoIdMapDayColored = mapOf(
        200 to R.drawable.storm_colored,
        201 to R.drawable.storm_colored,
        202 to R.drawable.storm_colored,
        210 to R.drawable.storm_colored,
        211 to R.drawable.storm_colored,
        212 to R.drawable.storm_colored,
        221 to R.drawable.storm_colored,
        230 to R.drawable.storm_colored,
        231 to R.drawable.storm_colored,
        232 to R.drawable.storm_colored,
        300 to R.drawable.drizzle_colored,
        301 to R.drawable.drizzle_colored,
        302 to R.drawable.drizzle_colored,
        310 to R.drawable.drizzle_colored,
        311 to R.drawable.drizzle_colored,
        312 to R.drawable.drizzle_colored,
        313 to R.drawable.drizzle_colored,
        314 to R.drawable.drizzle_colored,
        321 to R.drawable.drizzle_colored,
        500 to R.drawable.rain_colored,
        501 to R.drawable.rain_colored,
        502 to R.drawable.rain_colored,
        503 to R.drawable.rain_colored,
        504 to R.drawable.rain_colored,
        511 to R.drawable.drizzle_colored,
        520 to R.drawable.drizzle_colored,
        521 to R.drawable.drizzle_colored,
        522 to R.drawable.drizzle_colored,
        531 to R.drawable.drizzle_colored,
        600 to R.drawable.ic_snow_colored,
        601 to R.drawable.ic_snow_colored,
        602 to R.drawable.ic_snow_colored,
        611 to R.drawable.ic_snow_colored,
        612 to R.drawable.ic_snow_colored,
        615 to R.drawable.ic_snow_colored,
        616 to R.drawable.ic_snow_colored,
        620 to R.drawable.ic_snow_colored,
        621 to R.drawable.ic_snow_colored,
        622 to R.drawable.ic_snow_colored,
        701 to R.drawable.mist_colored,
        711 to R.drawable.mist_colored,
        721 to R.drawable.mist_colored,
        731 to R.drawable.mist_colored,
        741 to R.drawable.mist_colored,
        751 to R.drawable.mist_colored,
        761 to R.drawable.mist_colored,
        762 to R.drawable.mist_colored,
        771 to R.drawable.mist_colored,
        781 to R.drawable.mist_colored,
        800 to R.drawable.ic_sun,
        801 to R.drawable.ic_fiew_cloud_colored,
        802 to R.drawable.ic_cloud_colored,
        803 to R.drawable.ic_brocken_clouds_colored,
        804 to R.drawable.ic_brocken_clouds_colored
    )

    private val icoIdMapNight = mapOf(
        200 to R.drawable.storm,
        201 to R.drawable.storm,
        202 to R.drawable.storm,
        210 to R.drawable.storm,
        211 to R.drawable.storm,
        212 to R.drawable.storm,
        221 to R.drawable.storm,
        230 to R.drawable.storm,
        231 to R.drawable.storm,
        232 to R.drawable.storm,
        300 to R.drawable.drizzle,
        301 to R.drawable.drizzle,
        302 to R.drawable.drizzle,
        310 to R.drawable.drizzle,
        311 to R.drawable.drizzle,
        312 to R.drawable.drizzle,
        313 to R.drawable.drizzle,
        314 to R.drawable.drizzle,
        321 to R.drawable.drizzle,
        500 to R.drawable.rain,
        501 to R.drawable.rain,
        502 to R.drawable.rain,
        503 to R.drawable.rain,
        504 to R.drawable.rain,
        511 to R.drawable.drizzle,
        520 to R.drawable.drizzle,
        521 to R.drawable.drizzle,
        522 to R.drawable.drizzle,
        531 to R.drawable.drizzle,
        600 to R.drawable.ic_snow,
        601 to R.drawable.ic_snow,
        602 to R.drawable.ic_snow,
        611 to R.drawable.ic_snow,
        612 to R.drawable.ic_snow,
        615 to R.drawable.ic_snow,
        616 to R.drawable.ic_snow,
        620 to R.drawable.ic_snow,
        621 to R.drawable.ic_snow,
        622 to R.drawable.ic_snow,
        701 to R.drawable.mist,
        711 to R.drawable.mist,
        721 to R.drawable.mist,
        731 to R.drawable.mist,
        741 to R.drawable.mist,
        751 to R.drawable.mist,
        761 to R.drawable.mist,
        762 to R.drawable.mist,
        771 to R.drawable.mist,
        781 to R.drawable.mist,
        800 to R.drawable.ic_crescent,
        801 to R.drawable.ic_fiew_cloud_night,
        802 to R.drawable.ic_cloud,
        803 to R.drawable.ic_brocken_clouds_night,
        804 to R.drawable.ic_brocken_clouds_night
    )

    private val icoIdMapNightColored = mapOf(
        200 to R.drawable.storm_colored,
        201 to R.drawable.storm_colored,
        202 to R.drawable.storm_colored,
        210 to R.drawable.storm_colored,
        211 to R.drawable.storm_colored,
        212 to R.drawable.storm_colored,
        221 to R.drawable.storm_colored,
        230 to R.drawable.storm_colored,
        231 to R.drawable.storm_colored,
        232 to R.drawable.storm_colored,
        300 to R.drawable.drizzle_colored,
        301 to R.drawable.drizzle_colored,
        302 to R.drawable.drizzle_colored,
        310 to R.drawable.drizzle_colored,
        311 to R.drawable.drizzle_colored,
        312 to R.drawable.drizzle_colored,
        313 to R.drawable.drizzle_colored,
        314 to R.drawable.drizzle_colored,
        321 to R.drawable.drizzle_colored,
        500 to R.drawable.rain_colored,
        501 to R.drawable.rain_colored,
        502 to R.drawable.rain_colored,
        503 to R.drawable.rain_colored,
        504 to R.drawable.rain_colored,
        511 to R.drawable.drizzle_colored,
        520 to R.drawable.drizzle_colored,
        521 to R.drawable.drizzle_colored,
        522 to R.drawable.drizzle_colored,
        531 to R.drawable.drizzle_colored,
        600 to R.drawable.ic_snow_colored,
        601 to R.drawable.ic_snow_colored,
        602 to R.drawable.ic_snow_colored,
        611 to R.drawable.ic_snow_colored,
        612 to R.drawable.ic_snow_colored,
        615 to R.drawable.ic_snow_colored,
        616 to R.drawable.ic_snow_colored,
        620 to R.drawable.ic_snow_colored,
        621 to R.drawable.ic_snow_colored,
        622 to R.drawable.ic_snow_colored,
        701 to R.drawable.mist_colored,
        711 to R.drawable.mist_colored,
        721 to R.drawable.mist_colored,
        731 to R.drawable.mist_colored,
        741 to R.drawable.mist_colored,
        751 to R.drawable.mist_colored,
        761 to R.drawable.mist_colored,
        762 to R.drawable.mist_colored,
        771 to R.drawable.mist_colored,
        781 to R.drawable.mist_colored,
        800 to R.drawable.ic_crescent,
        801 to R.drawable.ic_fiew_cloud_night_colored,
        802 to R.drawable.ic_cloud_colored,
        803 to R.drawable.ic_brocken_clouds_night_colored,
        804 to R.drawable.ic_brocken_clouds_night_colored
    )
}