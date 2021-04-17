package io.github.horaciocome1.witweather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CityWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CityWeatherFragment()
    }

    private lateinit var viewModel: CityWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CityWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}