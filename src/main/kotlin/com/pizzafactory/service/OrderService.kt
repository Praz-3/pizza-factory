package com.pizzafactory.service

import com.pizzafactory.model.Order
import com.pizzafactory.validator.ValidatorChain

object OrderService {
    @Synchronized
    fun processOrder(order: Order): Boolean {
        ValidatorChain.validate(order)
        return if (InventoryService.checkAvailability(order)) {
            InventoryService.consumeInventory(order)
            true
        } else {
            false
        }
    }
}