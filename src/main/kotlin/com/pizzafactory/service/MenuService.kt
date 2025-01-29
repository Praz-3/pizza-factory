package com.pizzafactory.service

import com.pizzafactory.model.*

object MenuService {
    private val pizzas = mutableListOf<Pizza>()
    private val toppings = mutableListOf<Topping>()
    private val sides = mutableListOf<Side>()

    fun addPizza(pizza: Pizza) {
        pizzas.add(pizza)
    }

    fun addTopping(topping: Topping) {
        toppings.add(topping)
    }

    fun addSide(side: Side) {
        sides.add(side)
    }

    fun updatePizzaPrices(pizzaName: String, newPrices: Map<Size, Long>) {
        val pizza = pizzas.find { it.name == pizzaName }
        if (pizza != null) {
            pizza.pricesBySize.putAll(newPrices)
        } else {
            println("Pizza not found")
        }
    }

    fun updateToppingPrice(toppingName: String, newPrice: Long) {
        val topping = toppings.find { it.name == toppingName }
        if (topping != null) {
            topping.price = newPrice
        } else {
            println("Topping not found")
        }
    }

    fun updateSidePrice(sideName: String, newPrice: Long) {
        val side = sides.find { it.name == sideName }
        if (side != null) {
            side.price = newPrice
        } else {
            println("Side not found")
        }
    }

    fun getPizzas(): List<Pizza> = pizzas
    fun getToppings(): List<Topping> = toppings
    fun getSides(): List<Side> = sides
}