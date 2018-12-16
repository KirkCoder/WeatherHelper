package ru.kcoder.weatherhelper.toolkit.android

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import ru.kcoder.weatherhelper.presentation.main.MainActivity
import ru.kcoder.weatherhelper.presentation.main.MainFragment
import ru.kcoder.weatherhelper.presentation.place.FragmentAddPlace
import ru.kcoder.weatherhelper.presentation.weather.list.FragmentWeatherList

object AppRouter {
    fun showMainFragment(activity: FragmentActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(MainActivity.FRAGMENT_CONTAINER, MainFragment.newInstance(), MainFragment.TAG)
            .commit()
    }

    fun showWeatherListFragment(fragmentManager: FragmentManager) {
        showNewFragment(
            fragmentManager, MainFragment.FRAGMENT_CONTAINER,
            FragmentWeatherList.newInstance(), FragmentWeatherList.TAG, true
        )
    }

    fun showAddWeatherFragment(activity: FragmentActivity) {
        val fragmentManager = getFragmentManager(activity)

        showNewFragment(
            fragmentManager, MainFragment.FRAGMENT_CONTAINER,
            FragmentAddPlace.newInstance(),
            FragmentAddPlace.TAG, true
        )
    }

    fun showWeatherDetailFragment(activity: FragmentActivity, id: Long) {

    }

    private fun getFragmentManager(activity: FragmentActivity): FragmentManager {
        var hostFragment: Fragment? = null
        val fragments = activity.supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            hostFragment = fragments[0]
        }
        val fragmentManager = hostFragment?.childFragmentManager
            ?: activity.supportFragmentManager
        return fragmentManager
    }

    private fun showNewFragment(
        fragmentManager: FragmentManager,
        frameLayoutId: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false
    ) {
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(frameLayoutId, fragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }
}