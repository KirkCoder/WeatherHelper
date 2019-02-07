package ru.kcoder.weatherhelper.toolkit.utils


import android.content.Context

/**
 * Helper for layouts calculation
 */
object LayoutUtils {

    fun getDpFromPx(context: Context, px: Int): Float {
        return px / context.resources.displayMetrics.density
    }

    fun getPxFromDp(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }
}