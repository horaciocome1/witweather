package io.github.horaciocome1.witweather.util

import io.github.horaciocome1.witweather.R

object CityCoverUtils {

    fun getCityCoverResId(): Int {
        val covers = mutableListOf(
            R.drawable.ic_undraw_apartment_rent_o0ut,
            R.drawable.ic_undraw_at_home_octe,
            R.drawable.ic_undraw_building_re_xfcm,
            R.drawable.ic_undraw_cabin_hkfr,
        )
        val randomPosition = covers.random()
        return covers[randomPosition]
    }

}