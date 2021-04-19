package io.github.horaciocome1.witweather.ui.city_weather

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.data.city_weather.CityWeather
import io.github.horaciocome1.witweather.databinding.FragmentCityWeatherBinding
import io.github.horaciocome1.witweather.util.gone
import io.github.horaciocome1.witweather.util.visible
import kotlin.math.roundToInt

class CityWeatherFragment : Fragment() {

    private val viewModel: CityWeatherViewModel by viewModels()

    private lateinit var binding: FragmentCityWeatherBinding

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
        viewModel.getCityWeather(cityId).observe(viewLifecycleOwner) { setWeatherToUI(it) }
    }

    private fun setWeatherToUI(weather: CityWeather) {
        Log.d("CityWeatherF", "setWeatherToUI: $weather")
        val min = weather.main.tempMin.roundToInt()
        val max = weather.main.tempMax.roundToInt()
        binding.contentInclude.tempTextView.text = getString(R.string.temp_min_max, min, max)
        binding.contentInclude.currentTempTextView.text = "${weather.main.temp.roundToInt()}"
        binding.contentInclude.mainTextView.text = weather.weather.first().main
//        binding.sunriseTextView.text =
        val wind = weather.wind.speed.roundToInt()
        binding.contentInclude.windTextView.text = getString(R.string.wind_mps, wind)
        val realFeel = weather.main.feelsLike.roundToInt()
        binding.contentInclude.reealFeelTextView.text = getString(R.string.temp, realFeel)
        binding.contentInclude.root.visible()
        binding.progressBar.gone()
    }

}