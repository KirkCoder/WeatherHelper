package ru.kcoder.weatherhelper.features.weather.detail.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.weather_detail_host_fragment.*
import org.koin.android.ext.android.setProperty
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ru.kcoder.weatherhelper.data.entity.weather.detail.WeatherDetailModel
import ru.kcoder.weatherhelper.di.WEATHER_DETAIL_HOST_SCOPE
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.mObserver
import ru.kcoder.weatherhelper.toolkit.android.selectedPageListener
import ru.kcoder.weatherhelper.toolkit.farmework.AbstractFragment

class FragmentWeatherDetailHost : AbstractFragment() {

    override lateinit var viewModel: ContractWeatherDetailHost.ViewModel
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindScope(getOrCreateScope(WEATHER_DETAIL_HOST_SCOPE))
        arguments?.let {
            setProperty(
                ID_KEY, it.getLong(ID_KEY)
            )
            setProperty(
                UPDATE_KEY, it.getBoolean(UPDATE_KEY)
            )
            viewModel = getViewModel()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme_White)
        val inf = inflater.cloneInContext(contextThemeWrapper)
        return inf.inflate(R.layout.weather_detail_host_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        adapter = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter
        viewPager.selectedPageListener { viewModel.selectedPage(it) }
    }

    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.positions.observe(this, mObserver { model ->
            adapter.setData(model)
            subscribeForSelectedItem(model)
        })
    }

    private fun subscribeForSelectedItem(model: WeatherDetailModel) {
        model.selectedItem?.let {
            viewModel.selectedPage(it.position)
            viewPager.currentItem = it.position
        }
        viewModel.selected.observe(this, mObserver { item ->
            viewPager.currentItem = item.position
            setTitle(item.name)
        })
    }

    companion object {
        const val TAG = "FRAGMENT_WEATHER_DETAIL_HOST_TAG"
        const val ID_KEY = "id_key"
        const val UPDATE_KEY = "update_key"

        @JvmStatic
        fun newInstance(selectedId: Long?, isNeedUpdate: Boolean): Fragment {
            val fragment = FragmentWeatherDetailHost()
            val bundle = Bundle()
            selectedId?.let { bundle.putLong(ID_KEY, it) }
            bundle.putBoolean(UPDATE_KEY, isNeedUpdate)
            fragment.arguments = bundle
            return fragment
        }
    }
}