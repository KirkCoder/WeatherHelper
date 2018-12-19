package ru.kcoder.weatherhelper.presentation.weather.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentWeatherDetail : BaseFragment() {

    private val viewModel: ViewModelWeatherDetail by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getLong(ID_KEY)?.let {
            viewModel.updateWeather(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_White)
        val inf = inflater.cloneInContext(contextThemeWrapper)
        return inf.inflate(R.layout.weather_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun subscribeData() {
        viewModel.weather.observe(this, Observer {weatherHolder ->
            weatherHolder?.let {
                textViewTitle.text = it.name
                textViewTimeDescription.text = it.data?.first()?.dt?.toString() ?: ""
                textViewTemp.text = it.data?.first()?.main?.tempMin?.toString() ?: "nonono"
            }
        })
    }

    companion object {
        const val TAG = "FRAGMENT_WEATHER_DETAIL_TAG"
        private const val ID_KEY = "id_key"
        @JvmStatic
        fun newInstance(weatherId: Long): FragmentWeatherDetail {
            val fragment = FragmentWeatherDetail()
            val bundle = Bundle()
            bundle.putLong(ID_KEY, weatherId)
            fragment.arguments = bundle
            return fragment
        }
    }
}