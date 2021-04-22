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