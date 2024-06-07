package com.comporg.finalprojectv3


data class ItemsViewModel(val image: Int, val text: String) {
}

data class plantItem(
    val Img: String = "",
    val MoistMax: Int = 0,
    val MoistMin: Int = 0,
    val Name: String = ""
)