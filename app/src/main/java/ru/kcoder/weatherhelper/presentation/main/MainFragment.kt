package ru.kcoder.weatherhelper.presentation.main

import android.os.Bundle
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.presentation.weather.list.WeatherListViewModel
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import android.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.toolkit.android.AppRouter


class MainFragment : BaseFragment() {
    val vm: WeatherListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCompatActivity?.setSupportActionBar(toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            activity?.let {
                AppRouter.showWeatherListFragment(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
        const val TAG = "main_fragment_tag"
        const val FRAGMENT_CONTAINER = R.id.fragmentMainContainer
    }
}