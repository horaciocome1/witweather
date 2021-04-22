package io.github.horaciocome1.witweather.ui.home

import android.animation.LayoutTransition
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.data.city_weather.GeoCoordinates
import io.github.horaciocome1.witweather.databinding.FragmentHomeBinding
import io.github.horaciocome1.witweather.util.*
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private val citiesAdapter: CitiesAdapter by lazy {
        CitiesAdapter { cityName, cityCountry ->
            getString(R.string.city_name, cityName, cityCountry)
        }
    }

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
        binding.currentCityRequestInclude.enableMyLocationButton.setOnClickListener { enableLocation() }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        validatePermissions()
        getCities()
    }

    private fun validatePermissions() {
        sharedViewModel.isLocationPermissionGranted.observe(viewLifecycleOwner) {
            when {
                it -> {
                    showLoading()
                    getGeoCoordinates()
                }
                else -> showRequest()
            }
        }
    }

    private fun getGeoCoordinates() {
        sharedViewModel.geoCoordinates.observe(viewLifecycleOwner) {
            getCityWeather(it)
        }
    }

    private fun getCityWeather(geoCoordinates: GeoCoordinates) {
        viewModel.getCityWeather(geoCoordinates).observe(viewLifecycleOwner) {
            bindWeather(it)
        }
    }

    private fun getCities() {
        viewModel.getCities().observe(this) {
            citiesAdapter.dataSet = it
        }
    }

    private fun bindWeather(weather: CityWeather) {
        hideRequest()
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

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = citiesAdapter
        binding.recyclerView.addOnItemClickListener { _, position ->
            val city = citiesAdapter.dataSet[position]
            val name = getString(R.string.city_name, city.name, city.country)
            viewModel.navigateToCity(findNavController(), city.id, name)
        }
    }

    private fun showRequest() {
        binding.progressBar.gone()
        binding.currentCityInclude.root.gone()
        binding.currentCityRequestInclude.root.visible()
        binding.currentCityRequestInclude.enableMyLocationButton.enable()
        binding.constraintLayout.changeTopCitiesTextViewConstraints(R.id.currentCityRequestInclude)
    }

    private fun hideRequest() {
        binding.progressBar.gone()
        binding.currentCityInclude.root.visible()
        binding.currentCityRequestInclude.root.gone()
        binding.constraintLayout.changeTopCitiesTextViewConstraints(R.id.currentCityInclude)
    }

    private fun showLoading() {
        binding.progressBar.visible()
        binding.currentCityInclude.root.gone()
        binding.currentCityRequestInclude.enableMyLocationButton.disable()
    }

}