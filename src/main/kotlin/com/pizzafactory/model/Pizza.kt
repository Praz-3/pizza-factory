package com.pizzafactory.model

data class Pizza(
    val name: String,
    val isVeg: Boolean,
    val pricesBySize: MutableMap<Size, Long>
)