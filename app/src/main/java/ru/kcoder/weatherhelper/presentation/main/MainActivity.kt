package ru.kcoder.weatherhelper.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.AppRouter

//http://api.openweathermap.org/data/2.5/forecast?q=London&APPID=10fad01b4946d8ffa0c27d14d69a0333
//api.openweathermap.org/data/2.5/forecast?lat=35&lon=139
//API_KEY = "10fad01b4946d8ffa0c27d14d69a0333"
//    45.04484, 38.97603

class MainActivity : AppCompatActivity() {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            AppRouter.showMainFragment(this)
        }
    }

    override fun onBackPressed() {
        if(currentFragment?.onBackPressed() != false){
            super.onBackPressed()
        }
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    companion object {
        const val FRAGMENT_CONTAINER = R.id.fragmentContainer
    }
}
