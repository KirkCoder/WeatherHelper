package ru.kcoder.weatherhelper.presentation.weather.detail

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_common.*
import kotlinx.android.synthetic.main.weather_detail_fragment.*
import org.koin.android.ext.android.setProperty
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentWeatherDetail : BaseFragment() {

    private lateinit var viewModel: ViewModelWeatherDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setProperty(ID_KEY, it.getLong(ID_KEY))
            setProperty(NEED_UPDATE, it.getBoolean(NEED_UPDATE))
            viewModel = getViewModel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_White)
        val inf = inflater.cloneInContext(contextThemeWrapper)
        return inf.inflate(R.layout.weather_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeUi()
    }

    private fun initViews() {
        swipeLayoutDetail.setOnRefreshListener { viewModel.forceUpdate() }
    }

    private fun subscribeUi() {
        viewModel.weather.observe(this, Observer { holder ->
            holder?.let {
                if (it.hours.isNotEmpty()){
                    textViewTimeDescription.text = it.hours[0].dateAndDescription
                    textViewTemp.text = it.hours[0].tempNow
                    textViewCalvin.text = it.hours[0].degreeThumbnail
                    imageViewIco.setImageResource(it.hours[0].icoRes)
                    textViewHumidityDescription.text = it.hours[0].humidity
                    textViewWindDescription.text = it.hours[0].wind
                }
            }
        })

        viewModel.status.observe(this, Observer { status ->
            status?.let { swipeLayoutDetail.isRefreshing = it }
        })

        viewModel.errorLiveData.observe(this, Observer {error ->
            error?.let { showError(it) }
        })
    }

    companion object {
        const val TAG = "FRAGMENT_WEATHER_DETAIL_TAG"
        const val ID_KEY = "id_key"
        const val NEED_UPDATE = "need_update_key"
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