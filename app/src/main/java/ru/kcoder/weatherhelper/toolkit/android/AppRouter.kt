package ru.kcoder.weatherhelper.toolkit.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.kcoder.weatherhelper.features.main.MainActivity
import ru.kcoder.weatherhelper.features.main.MainFragment
import ru.kcoder.weatherhelper.features.place.FragmentPlace
import ru.kcoder.weatherhelper.features.settings.FragmentSettings
import ru.kcoder.weatherhelper.features.weather.detail.FragmentWeatherDetail
import ru.kcoder.weatherhelper.features.weather.list.FragmentWeatherList

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
        showNewFragment(
            getFragmentManager(activity), MainFragment.FRAGMENT_CONTAINER,
            FragmentPlace.newInstance(),
            FragmentPlace.TAG, true
        )
    }

    fun showWeatherDetailFragment(activity: FragmentActivity, id: Long, needUpdate: Boolean = false) {
        showNewFragment(
            getFragmentManager(activity), MainFragment.FRAGMENT_CONTAINER,
            FragmentWeatherDetail.newInstance(id, needUpdate),
            FragmentWeatherDetail.TAG, true
        )
    }

    fun showSettingsFragment(activity: FragmentActivity){
        showNewFragment(
            getFragmentManager(activity), MainFragment.FRAGMENT_CONTAINER,
            FragmentSettings.newInstance(),
            FragmentSettings.TAG, true
        )
    }

    private fun getFragmentManager(activity: FragmentActivity): androidx.fragment.app.FragmentManager {
        var hostFragment: Fragment? = null
        val fragments = activity.supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            hostFragment = fragments[0]
        }
        return hostFragment?.childFragmentManager
            ?: activity.supportFragmentManager
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

    fun popBackStack(activity: FragmentActivity) {
        getFragmentManager(activity).popBackStack()
    }
}