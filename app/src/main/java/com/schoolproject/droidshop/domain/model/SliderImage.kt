package com.schoolproject.droidshop.domain.model

import com.schoolproject.droidshop.R

data class SliderImage(
    val image: Int
)

val dummySliderImage = listOf(
    SliderImage(R.drawable.image_slider_one),
    SliderImage(R.drawable.image_slider_two),
    SliderImage(R.drawable.image_slider_three),
    SliderImage(R.drawable.image_slider_one),
)
