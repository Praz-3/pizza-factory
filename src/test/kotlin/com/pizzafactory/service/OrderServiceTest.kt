package com.pizzafactory.service

import com.pizzafactory.model.*
import kotlin.test.*

class OrderServiceTest {

    private lateinit var testPizza: Pizza
    private lateinit var testOrder: Order

    @BeforeTest
    fun setup() {
        testPizza = Pizza("Test Pizza", true, mutableMapOf(Size.REGULAR to 150))
        val testCrust = Crust.NEW_HAND_TOSSED
        val testToppings = listOf(Topping("Extra Cheese", 35, true))
        val testSides = listOf(Side("Cold Drink", 55))

        testOrder = Order(testPizza, Size.REGULAR, testCrust, testToppings, testSides)

        InventoryService.restockPizza(testPizza, 1)
        InventoryService.restockCrust(testCrust, 1)
        InventoryService.restockTopping(Topping("Extra Cheese", 35, true), 1)
        InventoryService.restockSide(Side("Cold Drink", 55), 1)
    }

    @Test
    fun testProcessOrder_Success() {
        val result = OrderService.processOrder(testOrder)
        assertTrue(result, "Order should be successfully processed")
    }

    @Test
    fun testProcessOrder_Failure() {
        val result = OrderService.processOrder(testOrder)
        assertTrue(result, "First order should be processed")

        val secondOrder = OrderService.processOrder(testOrder)
        assertFalse(secondOrder, "Second order should fail due to insufficient inventory")
    }
}
