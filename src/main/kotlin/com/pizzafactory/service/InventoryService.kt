package com.pizzafactory.service

import com.pizzafactory.model.*

object InventoryService {
    private val crustInventory = mutableMapOf<Crust, Int>()
    private val toppingInventory = mutableMapOf<Topping, Int>()
    private val sideInventory = mutableMapOf<Side, Int>()
    private val pizzaInventory = mutableMapOf<Pizza, Int>()

    fun restockPizza(pizza: Pizza, quantity: Int) {
        pizzaInventory[pizza] = pizzaInventory.getOrDefault(pizza, 0) + quantity
    }

    fun restockCrust(crust: Crust, quantity: Int) {
        crustInventory[crust] = crustInventory.getOrDefault(crust, 0) + quantity
    }

    fun restockTopping(topping: Topping, quantity: Int) {
        toppingInventory[topping] = toppingInventory.getOrDefault(topping, 0) + quantity
    }

    fun restockSide(side: Side, quantity: Int) {
        sideInventory[side] = sideInventory.getOrDefault(side, 0) + quantity
    }

    fun checkAvailability(order: Order): Boolean {
        return (checkPizzaAvailability(order) &&
                checkCrustAvailability(order) &&
                checkToppingAvailability(order) &&
                checkSideAvailability(order))
    }

    private fun checkSideAvailability(order: Order) = order.sides.all { sideInventory.getOrDefault(it, 0) > 0 }

    private fun checkToppingAvailability(order: Order) = order.toppings.all { toppingInventory.getOrDefault(it, 0) > 0 }

    private fun checkCrustAvailability(order: Order) = crustInventory.getOrDefault(order.crust, 0) > 0

    private fun checkPizzaAvailability(order: Order) = pizzaInventory.getOrDefault(order.pizza, 0) > 0

    fun consumeInventory(order: Order) {
        pizzaInventory[order.pizza] = pizzaInventory.getOrDefault(order.pizza, 0) - 1
        crustInventory[order.crust] = crustInventory.getOrDefault(order.crust, 0) - 1
        order.toppings.forEach { toppingInventory[it] = toppingInventory.getOrDefault(it, 0) - 1 }
        order.sides.forEach { sideInventory[it] = sideInventory.getOrDefault(it, 0) - 1 }
    }
}