package ru.kcoder.weatherhelper.toolkit.android

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import ru.kcoder.weatherhelper.presentation.main.MainActivity
import ru.kcoder.weatherhelper.presentation.main.MainFragment
import ru.kcoder.weatherhelper.presentation.weather.list.FragmentWeatherList

object AppRouter {
    fun showMainFragment(activity: FragmentActivity) {
        showNewFragment(
            activity, MainActivity.FRAGMENT_CONTAINER,
            MainFragment.newInstance(), MainFragment.TAG, false
        )
    }

    fun showWeatherListFragment(activity: FragmentActivity) {
        showNewFragment(
            activity, MainFragment.FRAGMENT_CONTAINER,
            FragmentWeatherList.newInstance(), FragmentWeatherList.TAG
        )
    }

    private fun showNewFragment(
        activity: FragmentActivity,
        frameLayoutId: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false
    ) {
        val transaction = activity.supportFragmentManager.beginTransaction()

        transaction.replace(frameLayoutId, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
}