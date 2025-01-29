package com.pizzafactory.validator

import com.pizzafactory.model.Order

object NonVegetarianRuleValidator : Validator {
    override fun validate(order: Order) {
        if (!order.pizza.isVeg && order.toppings.any { it.name == "Paneer" }) {
            throw IllegalArgumentException("Non-Vegetarian pizzas cannot have Paneer topping.")
        }
    }
}