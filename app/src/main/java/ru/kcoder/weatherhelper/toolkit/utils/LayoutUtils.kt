package ru.kcoder.weatherhelper.toolkit.utils


import android.content.Context

/**
 * Helper for layouts calculation
 */
object LayoutUtils {

    fun calculateNoOfColumns(context: Context, width: Int = 180): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / width).toInt()
    }

    fun getDpFromPx(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.density
    }

    fun getPxFromDp(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }
}