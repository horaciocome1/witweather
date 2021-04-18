package io.github.horaciocome1.witweather.ui.city_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.data.CityWeather
import io.github.horaciocome1.witweather.databinding.FragmentCityWeatherBinding
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
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
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
        viewModel.getCityWeather(cityId).observe(this) { setWeatherToUI(it)}
    }

    private fun setWeatherToUI(weather: CityWeather) {
        val min = weather.temp.min.roundToInt()
        val max = weather.temp.max.roundToInt()
        binding.tempTextView.text = getString(R.string.temp_min_max, min, max)
        binding.currentTempTextView.text = "${weather.temp.current.roundToInt()}"
        binding.mainTextView.text = weather.main
//        binding.sunriseTextView.text =
        val wind = weather.wind.roundToInt()
        binding.windTextView.text = getString(R.string.wind, wind)
        val realFeel = weather.temp.realFeel.roundToInt()
        binding.windTextView.text = getString(R.string.temp, realFeel)
    }

}