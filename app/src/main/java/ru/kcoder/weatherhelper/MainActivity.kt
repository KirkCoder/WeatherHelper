package ru.kcoder.weatherhelper

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.presentation.weather.list.WeatherListViewModel
import ru.kcoder.weatherhelper.ru.weatherhelper.R

//http://api.openweathermap.org/data/2.5/forecast?q=London&APPID=10fad01b4946d8ffa0c27d14d69a0333
//api.openweathermap.org/data/2.5/forecast?lat=35&lon=139
//API_KEY = "10fad01b4946d8ffa0c27d14d69a0333"
//    45.04484, 38.97603
class MainActivity : AppCompatActivity() {

    val vm : WeatherListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
// todo test logic

            vm.weatherList.observe(this, Observer {
                val u = 0
                it
            })

            vm.weather.observe(this, Observer {
                val u = 0
                it
            })

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
