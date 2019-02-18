package ru.kcoder.weatherhelper.features.weather.detail.item

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.weather_detail_fragment.*
import org.koin.android.ext.android.setProperty
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.kcoder.weatherhelper.di.WEATHER_DETAIL_SCOPE
import ru.kcoder.weatherhelper.toolkit.farmework.AbstractFragment
import ru.kcoder.weatherhelper.features.weather.detail.item.recycler.AdapterWeatherDetail
import ru.kcoder.weatherhelper.ru.weatherhelper.R

class FragmentWeatherDetailItem : AbstractFragment() {

    private lateinit var viewModel: ContractWeatherDetailItem.ViewModel
    override lateinit var errorLiveData: LiveData<Int>
    private val adapter =
        AdapterWeatherDetail { viewModel.clickInform(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getOrCreateScope(WEATHER_DETAIL_SCOPE))
        arguments?.let {
            setProperty(
                ID_KEY, it.getLong(ID_KEY)
            )
            setProperty(
                NEED_UPDATE, it.getBoolean(NEED_UPDATE)
            )
            viewModel = getViewModel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_White)
        val inf = inflater.cloneInContext(contextThemeWrapper)
        return inf.inflate(R.layout.weather_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        errorLiveData = viewModel.errorLiveData
        swipeLayoutDetail.setOnRefreshListener { viewModel.updateWeather() }
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FragmentWeatherDetailItem.adapter
        }
    }

    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.weather.observe(this, Observer { list ->
            if (!list.isNullOrEmpty()) adapter.setData(list)
        })

        viewModel.status.observe(this, Observer { status ->
            status?.let { swipeLayoutDetail.isRefreshing = it }
        })

        viewModel.checked.observe(this, Observer { data ->
            data?.let { adapter.setChecked(it) }
        })
    }

    companion object {
        const val ID_KEY = "id_key"
        const val NEED_UPDATE = "need_update_key"
        @JvmStatic
        fun newInstance(weatherId: Long, needUpdate: Boolean): FragmentWeatherDetailItem {
            val fragment = FragmentWeatherDetailItem()
            val bundle = Bundle()
            bundle.putLong(ID_KEY, weatherId)
            bundle.putBoolean(NEED_UPDATE, needUpdate)
            fragment.arguments = bundle
            return fragment
        }
    }
}