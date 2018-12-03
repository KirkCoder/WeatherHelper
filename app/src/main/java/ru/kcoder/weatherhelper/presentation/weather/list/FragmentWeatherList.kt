package ru.kcoder.weatherhelper.presentation.weather.list

import android.os.Bundle
import android.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentWeatherList : BaseFragment() {
    val vm: WeatherListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        addCompatActivity?.title = resources.getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "WEATHER_FRAGMENT_LIST_TAG"
        @JvmStatic
        fun newInstance() = FragmentWeatherList()
    }

}