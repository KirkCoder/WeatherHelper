package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentWeatherDetail : BaseFragment() {

    private val viewModel: ViewModelWeatherDetail by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewModel.updateWeather(it.getLong(ID_KEY), it.getBoolean(NEED_UPDATE))
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
        viewModel.weather.observe(this, Observer { holder ->
            holder?.let {
                textViewTitle.text = it.name
                textViewTimeDescription.text = it.hours[0].dateAndDescription
                textViewTemp.text = it.hours[0].tempNow
            }
        })
    }

    companion object {
        const val TAG = "FRAGMENT_WEATHER_DETAIL_TAG"
        private const val ID_KEY = "id_key"
        private const val NEED_UPDATE = "need_update_key"
        @JvmStatic
        fun newInstance(weatherId: Long, needUpdate: Boolean): FragmentWeatherDetail {
            val fragment = FragmentWeatherDetail()
            val bundle = Bundle()
            bundle.putLong(ID_KEY, weatherId)
            bundle.putBoolean(NEED_UPDATE, needUpdate)
            fragment.arguments = bundle
            return fragment
        }
    }
}