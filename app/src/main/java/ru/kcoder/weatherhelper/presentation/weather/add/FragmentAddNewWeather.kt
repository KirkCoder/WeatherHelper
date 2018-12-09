package ru.kcoder.weatherhelper.presentation.weather.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import ru.kcoder.weatherhelper.presentation.common.BaseFragment
import ru.kcoder.weatherhelper.ru.weatherhelper.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView




class FragmentAddNewWeather : BaseFragment() {

    private lateinit var map: GoogleMap
    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_add_fragment, container, false)
        initMap(view, savedInstanceState)
        return view
    }

    private fun initMap(view: View, savedInstanceState: Bundle?) {
//        mapView.onCreate(savedInstanceState)

        mapView = view.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        mapView.getMapAsync { mMap ->
            map = mMap

            // For showing a move to my location button
            //            map.setMyLocationEnabled(true)

            // For dropping a marker at a point on the Map
            val sydney = LatLng(-34.0, 151.0)
            map.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }



//    override fun onResume() {
//        mapView.onResume()
//        super.onResume()
//    }
//
//    override fun onPause() {
//        mapView.onPause()
//        super.onPause()
//    }
//
//    override fun onStop() {
//        mapView.onStop()
//        super.onStop()
//    }
//
//    override fun onDestroy() {
//        mapView.onDestroy()
//        super.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        mapView.onLowMemory()
//        super.onLowMemory()
//    }

    companion object {
        const val TAG = "FRAGMENT_ADD_NEW_WEATHER_TAG"
        @JvmStatic
        fun newInstance() = FragmentAddNewWeather()
    }
}