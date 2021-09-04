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

package io.github.horaciocome1.witweather.ui.city_weather

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.github.horaciocome1.storage.model.CityWeather
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.databinding.FragmentCityWeatherBinding
import io.github.horaciocome1.witweather.util.gone
import io.github.horaciocome1.witweather.util.visible
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt

class CityWeatherFragment : Fragment() {

    private val viewModel: CityWeatherViewModel by inject()

    private lateinit var binding: FragmentCityWeatherBinding

    private val snackbarError: Snackbar by lazy {
        Snackbar.make(binding.root, getString(R.string.message_general_error), Snackbar.LENGTH_INDEFINITE)
            .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.container.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.contentInclude.refreshButton.setOnClickListener { viewModel.refreshWeather() }
    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val args = CityWeatherFragmentArgs.fromBundle(it)
            binding.cityNameTextView.text = args.cityName
            getCityWeather(args.cityId)
        }
    }

    private fun getCityWeather(cityId: Int) {
        Log.d("Fragment", "getCityWeather: $cityId")
        viewModel.getCityWeather(cityId).observe(viewLifecycleOwner) { bindWeather(it) }
        viewModel.callResult.observe(viewLifecycleOwner) {}
    }

    private fun bindWeather(cityWeather: CityWeather) {
        val min = cityWeather.tempMin.roundToInt()
        val max = cityWeather.tempMax.roundToInt()
        binding.contentInclude.tempTextView.text = getString(R.string.temp_min_max, min, max)
        binding.contentInclude.currentTempTextView.text = "${cityWeather.temp.roundToInt()}"
        binding.contentInclude.mainTextView.text = cityWeather.weatherMain
        binding.contentInclude.sunriseTextView.text = cityWeather.sysSunrise
        val wind = cityWeather.windSpeed.roundToInt()
        binding.contentInclude.windTextView.text = getString(R.string.wind_mps, wind)
        val realFeel = cityWeather.feelsLike.roundToInt()
        binding.contentInclude.reealFeelTextView.text = getString(R.string.temp, realFeel)
        binding.contentInclude.root.visible()
        binding.progressBar.gone()
    }
}
