package ru.kcoder.weatherhelper.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.AppRouter

//http://api.openweathermap.org/data/2.5/forecast?q=London&APPID=10fad01b4946d8ffa0c27d14d69a0333
//http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&APPID=10fad01b4946d8ffa0c27d14d69a0333
//API_KEY = "10fad01b4946d8ffa0c27d14d69a0333"
//    45.04484, 38.97603

class MainActivity : AppCompatActivity() {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentMainContainer) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        requestPermission()

        if (savedInstanceState == null) {
            AppRouter.showMainFragment(this)
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1 &&
            !(grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermission()
        }
        return
    }

    override fun onBackPressed() {
        val res = currentFragment?.onBackPressed() ?: super.onBackPressed()
        if (res != false) {
            super.onBackPressed()
        }
    }

    companion object {
        const val FRAGMENT_CONTAINER = R.id.fragmentMainContainer
    }
}
