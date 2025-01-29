package com.pizzafactory.validator

import com.pizzafactory.model.Order

object VegetarianRuleValidator : Validator {
    override fun validate(order: Order) {
        if (order.pizza.isVeg && order.toppings.any { !it.isVeg }) {
            throw IllegalArgumentException("Vegetarian pizzas cannot have non-vegetarian toppings.")
        }
    }
}