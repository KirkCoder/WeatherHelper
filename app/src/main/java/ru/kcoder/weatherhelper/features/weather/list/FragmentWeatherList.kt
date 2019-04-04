package ru.kcoder.weatherhelper.features.weather.list

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_list_fragment.*
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.di.WEATHER_LIST_SCOPE
import ru.kcoder.weatherhelper.features.weather.list.adapter.AdapterWeatherList
import ru.kcoder.weatherhelper.features.weather.list.adapter.DecoratorList
import ru.kcoder.weatherhelper.toolkit.farmework.AbstractFragment
import ru.kcoder.weatherhelper.features.weather.list.adapter.TouchCallback
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import ru.kcoder.weatherhelper.toolkit.android.AppRouter
import ru.kcoder.weatherhelper.toolkit.android.mObserver

class FragmentWeatherList : AbstractFragment(), DialogFragmentDelete.Callback {

    override val viewModel: ContractWeatherList.ViewModel by viewModel()

    private val adapter = AdapterWeatherList({
        showDetailFragment(it)
    }, {
        viewModel.forceUpdate(it)
    }, { id, name ->
        askDelete(id, name)
    }, {
        viewModel.changedData(it)
    }, {
        viewModel.notifyChange(it)
    }, this::startMotion)

    private val callback = TouchCallback(adapter::onItemMove)

    private val touchHelper = ItemTouchHelper(callback)

    private var isEdit = false

    private fun showDetailFragment(id: Long) {
        activity?.let { AppRouter.showWeatherDetailHostFragment(it, id) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindScope(getOrCreateScope(WEATHER_LIST_SCOPE))
        return inflater.inflate(R.layout.weather_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(getString(R.string.app_name))
        initView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        initRecycler(view.context)
        fabAdd.setOnClickListener {
            activity?.let { ctx -> AppRouter.showAddWeatherFragment(ctx) }
        }
        fabOk.setOnClickListener { finishEdit() }
    }

    private fun finishEdit() = viewModel.setEditStatus(false)

    private fun initRecycler(context: Context) {
        with(recyclerViewWeatherList) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@FragmentWeatherList.adapter
            touchHelper.attachToRecyclerView(this)
            addItemDecoration(
                DecoratorList(context.resources.getDimension(R.dimen.layout_margin_half).toInt())
            )
        }
    }

    override fun subscribeUi() {
        super.subscribeUi()

        viewModel.weatherHolders.observe(this, mObserver { adapter.setData(it) })

        viewModel.editStatus.observe(this, mObserver { setEditStatus(it) })
    }

    private fun setEditStatus(it: Boolean) {
        isEdit = it
        activity?.invalidateOptionsMenu()
        adapter.setEditStatus(it)
        if (it) {
            fabAdd.hide()
            fabOk.show()
        } else {
            fabAdd.show()
            fabOk.hide()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (isEdit) {
            menu.findItem(R.id.action_change).isVisible = false
        } else {
            menu.findItem(R.id.action_ok).isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                activity?.let { AppRouter.showSettingsFragment(it) }
                true
            }
            R.id.action_change -> {
                viewModel.setEditStatus(true)
                true
            }
            R.id.action_ok -> {
                finishEdit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun askDelete(id: Long, name: String) {
        DialogFragmentDelete.newInstance(id, name)
            .show(childFragmentManager, DialogFragmentDelete.TAG)
    }

    override fun needDelete(id: Long?) {
        id?.let {
            adapter.deleteItem(id)?.let { list -> viewModel.delete(it, list) }
        }
    }

    private fun startMotion(holder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(holder)
    }

    companion object {
        const val TAG = "WEATHER_FRAGMENT_LIST_TAG"
        @JvmStatic
        fun newInstance() = FragmentWeatherList()
    }

}