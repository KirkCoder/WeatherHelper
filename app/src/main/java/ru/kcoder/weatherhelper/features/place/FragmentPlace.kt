package ru.kcoder.weatherhelper.features.place

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.place_add_fragment.*
import org.koin.androidx.scope.ext.android.bindScope
import org.koin.androidx.scope.ext.android.getOrCreateScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kcoder.weatherhelper.data.entity.place.PlaceMarker
import ru.kcoder.weatherhelper.di.PLACE_SCOPE
import ru.kcoder.weatherhelper.toolkit.farmework.AbstractFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig
import ru.kcoder.weatherhelper.toolkit.android.AppRouter
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.Permissions

class FragmentPlace : AbstractFragment(), DialogFragmentPlace.Callback {

    private val viewModel: ContaractPlace.ViewModel by viewModel()
    override lateinit var errorLiveData: LiveData<Int>
    private var map: GoogleMap? = null
    private var mapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindScope(getOrCreateScope(PLACE_SCOPE))
        val view = inflater.inflate(R.layout.place_add_fragment, container, false)
        initMap(view, savedInstanceState)
        initPlace()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle(getString(R.string.app_name))
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("MissingPermission") //"smart cast" not work
    private fun initMap(view: View, savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync { mMap ->
            map = mMap
            with(mMap) {
                if (BuildConfig.DEBUG) uiSettings?.isZoomControlsEnabled = true // just for emulator
                activity?.let {
                    if (Permissions.checkLocation(it)) {
                        isMyLocationEnabled = true
                    }
                }
                setMarker(this)

                setOnMapLongClickListener { it ->
                    clear()
                    updateViewModel(PlaceMarker(it.latitude, it.longitude))
                }
            }
        }
    }

    private fun initPlace() {
        val placeFragment =
            childFragmentManager.findFragmentByTag(PLACES_FRAGMENT_TAG)
                    as? SupportPlaceAutocompleteFragment?
                ?: SupportPlaceAutocompleteFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutPlaceContainer, placeFragment, PLACES_FRAGMENT_TAG)
            .commit()

        placeFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                map?.let {
                    it.clear()
                    updateViewModel(
                        PlaceMarker(
                            place.latLng.latitude, place.latLng.longitude,
                            place.name.toString(), place.address?.toString()
                        )
                    )
                    val cameraPosition = CameraPosition.Builder().target(place.latLng).zoom(10f).build()
                    it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }

            override fun onError(status: Status) {
                showError(R.string.place_add_error_google_places)
                log(status.statusMessage ?: status.toString())
            }
        })
    }

    private fun initView() {
        errorLiveData = viewModel.errorLiveData
        fabSelectPlace.setOnClickListener { _ ->
            viewModel.savePlace()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.fabVisibility.observe(this, Observer { loading ->
            loading?.let {
                if (it) {
                    fabSelectPlace.visibility = View.VISIBLE
                } else {
                    fabSelectPlace.visibility = View.GONE
                }
            }
        })

        viewModel.addedPlaceIdLiveData.observe(this, Observer { id ->
            activity?.let {
                if (id != null) {
                    AppRouter.popBackStack(it)
                    AppRouter.showWeatherDetailHostFragment(it, id, true)
                }
            }
        })

        viewModel.showDialog.observe(this, Observer { bool ->
            bool?.let { if (it) showPlaceDialog() }
        })

        viewModel.progressLiveData.observe(this, Observer {
            if (it) progressBar.show()
            else progressBar.hide()
        })

        viewModel.errorLiveData.observe(this, Observer { error ->
            error?.let { showError(it) }
        })
    }

    private fun showPlaceDialog() {
        DialogFragmentPlace.newInstance()
            .show(childFragmentManager, DialogFragmentPlace.TAG)
    }

    @SuppressLint("RestrictedApi")
    private fun setMarker(mMap: GoogleMap) {
        viewModel.markerLiveData.observe(this, androidx.lifecycle.Observer {
            it?.let { place ->
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(place.lat, place.lon))
                        .title(place.name).also { option ->
                            place.address?.let { option.snippet(place.address.toString()) }
                        })
            }
        })
    }

    private fun updateViewModel(place: PlaceMarker) {
        viewModel.updateViewModel(place)
    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onStart() {
        mapView?.onStart()
        super.onStart()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStop() {
        mapView?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    override fun selectName(name: String?) {
        viewModel.updatePlaceName(name)
    }

    companion object {
        const val TAG = "FRAGMENT_ADD_WEATHER_TAG"
        private const val PLACES_FRAGMENT_TAG = "PLACES_FRAGMENT_TAG"
        private const val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
        @JvmStatic
        fun newInstance() = FragmentPlace()
    }
}