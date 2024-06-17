package com.comporg.finalprojectv3


data class ItemsViewModel(val image: Int, val text: String) {
}

data class plantItem(
    val Img: String = "",
    val MoistMax: Int = 0,
    val MoistMin: Int = 0,
    val Name: String = ""
)

data class userPlantItem(
    val name: String = "",
    val img: String = "",
    val humidity: Int = 0,
    val moisture: Int = 0,
    val temperature: Int = 0,
    val id: String = ""
)