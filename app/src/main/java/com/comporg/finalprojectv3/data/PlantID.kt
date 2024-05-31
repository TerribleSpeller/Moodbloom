package com.comporg.finalprojectv3.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.comporg.finalprojectv3.R

//Placeholder until we get the Database UP

/**
 * A data class to represent the information presented in the dog card
 */
data class PlantIDClass(
    @DrawableRes val StateIconImg: Int,
    @DrawableRes val StateImage: Int,
    @StringRes val id: Int
)
val examplePlant = PlantIDClass(R.drawable.angery_, R.drawable.plantexample, R.string.plantID)

val plants = listOf(
    PlantIDClass(R.drawable.angery_, R.drawable.plantexample, R.string.plantID)
)