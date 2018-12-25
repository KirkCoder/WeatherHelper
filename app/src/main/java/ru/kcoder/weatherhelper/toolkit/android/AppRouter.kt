package ru.kcoder.weatherhelper.toolkit.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.kcoder.weatherhelper.presentation.main.MainActivity
import ru.kcoder.weatherhelper.presentation.main.MainFragment
import ru.kcoder.weatherhelper.presentation.place.FragmentAddPlace
import ru.kcoder.weatherhelper.presentation.weather.detail.FragmentWeatherDetail
import ru.kcoder.weatherhelper.presentation.weather.list.FragmentWeatherList

object AppRouter {
    fun showMainFragment(activity: androidx.fragment.app.FragmentActivity) {
        activity.supportFragmentManager.beginTransaction()
            .replace(MainActivity.FRAGMENT_CONTAINER, MainFragment.newInstance(), MainFragment.TAG)
            .commit()
    }

    fun showWeatherListFragment(fragmentManager: androidx.fragment.app.FragmentManager) {
        showNewFragment(
            fragmentManager, MainFragment.FRAGMENT_CONTAINER,
            FragmentWeatherList.newInstance(), FragmentWeatherList.TAG, true
        )
    }

    fun showAddWeatherFragment(activity: androidx.fragment.app.FragmentActivity) {
        showNewFragment(
            getFragmentManager(activity), MainFragment.FRAGMENT_CONTAINER,
            FragmentAddPlace.newInstance(),
            FragmentAddPlace.TAG, true
        )
    }

    fun showWeatherDetailFragment(activity: androidx.fragment.app.FragmentActivity, id: Long, needUpdate: Boolean = false) {
        showNewFragment(
            getFragmentManager(activity), MainFragment.FRAGMENT_CONTAINER,
            FragmentWeatherDetail.newInstance(id, needUpdate),
            FragmentWeatherDetail.TAG, true
        )
    }

    private fun getFragmentManager(activity: androidx.fragment.app.FragmentActivity): androidx.fragment.app.FragmentManager {
        var hostFragment: androidx.fragment.app.Fragment? = null
        val fragments = activity.supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            hostFragment = fragments[0]
        }
        return hostFragment?.childFragmentManager
            ?: activity.supportFragmentManager
    }

    private fun showNewFragment(
        fragmentManager: androidx.fragment.app.FragmentManager,
        frameLayoutId: Int,
        fragment: androidx.fragment.app.Fragment,
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

    fun removeFromBackStack(activity: androidx.fragment.app.FragmentActivity, tag: String) {
        getFragmentManager(activity).popBackStack()
    }
}