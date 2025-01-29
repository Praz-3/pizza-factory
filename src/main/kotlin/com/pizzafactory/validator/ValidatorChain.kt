package com.pizzafactory.validator

import com.pizzafactory.model.Order

object ValidatorChain {
    private val validators: List<Validator> = listOf(VegetarianRuleValidator, NonVegetarianRuleValidator)

    fun validate(order: Order) {
        validators.forEach { it.validate(order) }
    }
}