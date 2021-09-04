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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.horaciocome1.network.cities.City
import io.github.horaciocome1.witweather.databinding.ItemCityBinding
import io.github.horaciocome1.witweather.util.CityCoverUtils


class CitiesAdapter(
    private val cityNameBuilder: (cityName: String, cityCountry: String) -> String
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    var dataSet: MutableList<io.github.horaciocome1.network.cities.City> = mutableListOf()
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

