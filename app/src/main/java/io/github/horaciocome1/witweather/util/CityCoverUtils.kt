package io.github.horaciocome1.witweather.util

import io.github.horaciocome1.witweather.R

object CityCoverUtils {

    fun getRandomCityCoverResId(): Int {
        val covers = mutableListOf(
            R.drawable.ic_undraw_apartment_rent_o0ut,
            R.drawable.ic_undraw_at_home_octe,
            R.drawable.ic_undraw_building_re_xfcm,
            R.drawable.ic_undraw_cabin_hkfr,
            R.drawable.ic_undraw_coming_home_52ir,
            R.drawable.ic_undraw_factory_dy0a,
            R.drawable.ic_undraw_quite_town_mg2q,
            R.drawable.ic_undraw_sweet_home_dkhr
        )
        return covers.random()
    }

}