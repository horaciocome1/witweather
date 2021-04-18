package io.github.horaciocome1.witweather.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.horaciocome1.simplerecyclerviewtouchlistener.addOnItemClickListener
import io.github.horaciocome1.witweather.R
import io.github.horaciocome1.witweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

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
        binding.currentCityInclude.refreshButton.setOnClickListener { getCityWeather() }
        binding.currentCityRequestInclude.enableMyLocationButton.setOnClickListener { enableLocation() }
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        getCities()
        getCityWeather()
    }

    private fun getCities() {
        viewModel.getCities().observe(this) { citiesAdapter.dataSet = it }
    }

    private fun getCityWeather() {

    }

    private fun enableLocation() {

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

}