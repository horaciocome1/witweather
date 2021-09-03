/*
 * Copyright 2021 Horácio Flávio Comé Júnior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.horaciocome1.witweather.ui.home

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.data.city_weather.GeoCoordinates
import io.github.horaciocome1.witweather.databinding.FragmentHomeBinding
import io.github.horaciocome1.witweather.util.NetworkCallResult
import io.github.horaciocome1.witweather.util.changeTopCitiesTextViewConstraints
import io.github.horaciocome1.witweather.util.disable
import io.github.horaciocome1.witweather.util.enable
import io.github.horaciocome1.witweather.util.gone
import io.github.horaciocome1.witweather.util.visible
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()

    private val sharedViewModel: HomeSharedViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private val citiesAdapter: CitiesAdapter by lazy {
        CitiesAdapter { cityName, cityCountry ->
            getString(R.string.city_name, cityName, cityCountry)
        }
    }

    private val snackbarLoading: Snackbar by lazy {
        Snackbar.make(binding.root, R.string.location_loading, Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
    }

    private val snackbarError: Snackbar by lazy {
        Snackbar.make(binding.root, getString(R.string.message_general_error), Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
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
        snackbarLoading.show()
        binding.container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.currentCityRequestInclude.enableMyLocationButton.setOnClickListener { enableLocation() }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        validatePermissions()
        getCities()
        monitorNetworkCall()
    }

    private fun enableLocation() {
        showLoading()
        sharedViewModel.requestLocationPermission()
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

    private fun bindWeather(weather: CityWeather) {
        hideRequest()
        binding.currentCityInclude.cityNameTextView.text = weather.name
        binding.currentCityInclude.currentTempTextView.text = "${weather.main.temp.roundToInt()}"
        val min = weather.main.tempMin.roundToInt()
        val max = weather.main.tempMax.roundToInt()
        val realFeel = weather.main.feelsLike.roundToInt()
        binding.currentCityInclude.tempTextView.text = getString(R.string.temp_min_max_rf, min, max, realFeel)
    }

    private fun getCities() {
        viewModel.getCities().observe(this) {
            citiesAdapter.dataSet = it
        }
    }

    private fun monitorNetworkCall() {
        viewModel.callResult.observe(viewLifecycleOwner) {
            when (it) {
                NetworkCallResult.ERROR -> snackbarError.show()
                else -> snackbarError.dismiss()
            }
        }
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
        snackbarLoading.dismiss()
    }

    private fun showLoading() {
        binding.progressBar.visible()
        binding.currentCityInclude.root.gone()
        binding.currentCityRequestInclude.enableMyLocationButton.disable()
    }
}
