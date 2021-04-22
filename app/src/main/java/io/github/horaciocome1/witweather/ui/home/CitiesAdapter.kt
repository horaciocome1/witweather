package io.github.horaciocome1.witweather.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.horaciocome1.witweather.data.cities.City
import io.github.horaciocome1.witweather.databinding.ItemCityBinding
import io.github.horaciocome1.witweather.util.CityCoverUtils


class CitiesAdapter(
    private val cityNameBuilder: (cityName: String, cityCountry: String) -> String
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    var dataSet: MutableList<City> = mutableListOf()
        set(value) {
            val callback = CitiesDiffCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field = value
            result.dispatchUpdatesTo(this)
        }

    private lateinit var binding: ItemCityBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemCityBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val city = dataSet[position]
        binding.cityNameTextView.text = cityNameBuilder(city.name, city.country)
        val coverResId = CityCoverUtils.getRandomCityCoverResId()
        binding.coverImageView.load(coverResId)
    }

    override fun getItemCount() = dataSet.size

}

