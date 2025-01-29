package com.pizzafactory.validator

import com.pizzafactory.model.Order

interface Validator {
    fun validate(order: Order)
}