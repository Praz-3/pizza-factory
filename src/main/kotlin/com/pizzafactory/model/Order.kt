package com.pizzafactory.model

data class Order(
    val pizza: Pizza,
    val size: Size,
    val crust: Crust,
    val toppings: List<Topping>,
    val sides: List<Side>
) {
    fun getTotalPrice(): Long {
        val basePrice =
            pizza.pricesBySize[size] ?: throw IllegalArgumentException("Invalid size for pizza: ${pizza.name}")
        val toppingsCost = toppings.sumOf { it.price }
        val sidesCost = sides.sumOf { it.price }
        return basePrice + toppingsCost + sidesCost
    }

    override fun toString(): String {
        return "Pizza=${pizza.name},\n Size=$size,\n Crust=$crust,\n Toppings=${toppings.map { it.name }}," +
                "\n Sides=${sides.map { it.name }})"
    }
}
