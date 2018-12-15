package ru.kcoder.weatherhelper.presentation.weather.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import kotlinx.android.synthetic.main.weather_add_fragment.*
import ru.kcoder.weatherhelper.toolkit.debug.log
import ru.kcoder.weatherhelper.toolkit.utils.Permissions




class FragmentAddNewWeather : BaseFragment() {

    private var map: GoogleMap? = null
    private var mapView: MapView? = null
    private var geoData: GeoDataClient? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_add_fragment, container, false)

        activity?.let {
            geoData = Places.getGeoDataClient(it)
        }

        initMap(view, savedInstanceState)
        initPlace()
        return view
    }

    @SuppressLint("MissingPermission") //"smart cast" not work
    private fun initMap(view: View, savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView?.onCreate(mapViewBundle)
        mapView?.getMapAsync { mMap ->
            map = mMap

            map?.let { gMap ->
                gMap.uiSettings?.isZoomControlsEnabled = true
                gMap.uiSettings?.isCompassEnabled = true
                activity?.let {
                    if (Permissions.checkLocation(it)) {
                        gMap.isMyLocationEnabled = true
                    }
                }

                gMap.setOnMapLongClickListener {
                    gMap.clear()
                    gMap.addMarker(MarkerOptions().position(it))
            }

                // marker click
                gMap.setOnInfoWindowClickListener {
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
                map?.let{
                    it.addMarker(
                        MarkerOptions()
                            .position(place.latLng)
                            .title(place.name.toString()).also {option ->
                                place.address?.let { _ -> option.snippet(place.address.toString()) }
                            })
                    val cameraPosition = CameraPosition.Builder().target(place.latLng).zoom(10f).build()
                    it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
                getPhoto(place.id)
            }

            override fun onError(status: Status) {
                log(status.toString())
            }
        });
    }

    fun getPhoto(placeId: String){
        geoData?.let {gd ->
            val photoMetadataResponse = gd.getPlacePhotos(placeId)
            photoMetadataResponse.addOnCompleteListener{t ->
                val photos = t.result
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                val photoMetadataBuffer = photos?.photoMetadata
                // Get the first photo in the list.
                val photoMetadata = photoMetadataBuffer?.get(2)
                // Get the attribution text.
                val attribution = photoMetadata?.getAttributions()
                // Get a full-size bitmap for the photo.
                val photoResponse = gd.getPhoto(photoMetadata!!)
                photoResponse.addOnCompleteListener{
                    val photo = it.result
                    val bitmap = photo?.bitmap
                    mapView!!.visibility = View.GONE
                    frameLayoutPlaceContainer.visibility = View.GONE
                    ivv.visibility = View.VISIBLE
                    ivv.setImageBitmap(bitmap)
                }
            }
        }

    }

    override fun onResume() {
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onStart() {
        mapView?.onStart()
        super.onStart()
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
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    companion object {
        const val TAG = "FRAGMENT_ADD_NEW_WEATHER_TAG"
        private const val PLACES_FRAGMENT_TAG = "PLACES_FRAGMENT_TAG"
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        @JvmStatic
        fun newInstance() = FragmentAddNewWeather()
    }
}