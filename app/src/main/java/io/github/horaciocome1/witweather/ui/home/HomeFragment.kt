package io.github.horaciocome1.witweather.ui.home

import android.Manifest
import android.animation.LayoutTransition
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.databinding.FragmentHomeBinding
import io.github.horaciocome1.witweather.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private val citiesAdapter: CitiesAdapter by lazy { CitiesAdapter(this::cityNameBuilder) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
//        binding.currentCityInclude.refreshButton.setOnClickListener { getCityWeather() }
        binding.currentCityRequestInclude.enableMyLocationButton.setOnClickListener { enableLocation() }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        sharedViewModel.isLocationPermissionGranted.observe(viewLifecycleOwner) {
            if (it) {
                hideRequest()
            } else {
                showRequest()
            }
        }
        getCities()
        sharedViewModel.geoCoordinates.observe(viewLifecycleOwner) {
            binding.currentCityInclude.refreshButton.enable()
            getCityWeather(it.latitude, it.longitude)
        }
    }

    private fun getCities() {
        viewModel.getCities().observe(this) { citiesAdapter.dataSet = it }
    }

    private fun getCityWeather(latitude: Double, longitude: Double) {
        viewModel.getCityWeather(latitude, longitude).observe(viewLifecycleOwner) { setWeatherToUI(it) }
    }

    private fun setWeatherToUI(weather: CityWeather) {
        binding.currentCityInclude.cityNameTextView.text = weather.name
        binding.currentCityInclude.currentTempTextView.text = "${weather.main.temp.roundToInt()}"
        val min = weather.main.tempMin.roundToInt()
        val max = weather.main.tempMax.roundToInt()
        val realFeel = weather.main.feelsLike.roundToInt()
        binding.currentCityInclude.tempTextView.text = getString(R.string.temp_min_max_rf, min, max, realFeel)
    }

    private fun enableLocation() {
        showLoading()
        sharedViewModel.requestLocationPermission()
    }

    private fun cityNameBuilder(cityName: String, country: String): String {
        return getString(R.string.city_name, cityName, country)
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = citiesAdapter
        binding.recyclerView.addOnItemClickListener { _, position ->
            val city = citiesAdapter.dataSet[position]
            val name = cityNameBuilder(city.name, city.country)
            viewModel.navigateToCity(findNavController(), city.id, name)
        }
    }

    private fun changeTopCitiesTextViewConstraints(resId: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintLayout)
        constraintSet.connect(R.id.topCitiesTextView, ConstraintSet.TOP, resId, ConstraintSet.BOTTOM)
        constraintSet.applyTo(binding.constraintLayout)
    }

    private fun showRequest() {
        binding.progressBar.gone()
        binding.currentCityInclude.root.gone()
        binding.currentCityRequestInclude.root.visible()
        binding.currentCityRequestInclude.enableMyLocationButton.enable()
        changeTopCitiesTextViewConstraints(R.id.currentCityRequestInclude)
    }

    private fun hideRequest() {
        binding.progressBar.gone()
        binding.currentCityInclude.root.visible()
        binding.currentCityRequestInclude.root.gone()
        changeTopCitiesTextViewConstraints(R.id.currentCityInclude)
    }

    private fun showLoading() {
        binding.progressBar.visible()
        binding.currentCityInclude.root.gone()
        binding.currentCityRequestInclude.enableMyLocationButton.disable()
    }

}