package com.pizzafactory.service

import com.pizzafactory.model.*
import kotlin.test.*

class InventoryServiceTest {

    private lateinit var testPizza: Pizza
    private lateinit var testCrust: Crust
    private lateinit var testTopping: Topping
    private lateinit var testSide: Side

    @BeforeTest
    fun setup() {
        testPizza = Pizza("Test Pizza", true, mutableMapOf(Size.REGULAR to 150))
        testCrust = Crust.NEW_HAND_TOSSED
        testTopping = Topping("Extra Cheese", 35, true)
        testSide = Side("Cold Drink", 55)
        InventoryService.restockPizza(testPizza, 5)
        InventoryService.restockCrust(testCrust, 3)

    }

    @Test
    fun testRestockAndConsumePizza() {
        assertTrue(InventoryService.checkAvailability(Order(testPizza, Size.REGULAR, testCrust, listOf(), listOf())))
    }

    @Test
    fun testRestockCrust() {
        assertTrue(InventoryService.checkAvailability(Order(testPizza, Size.REGULAR, testCrust, listOf(), listOf())))
    }

    @Test
    fun testRestockTopping() {
        InventoryService.restockTopping(testTopping, 2)
        assertTrue(InventoryService.checkAvailability(Order(testPizza, Size.REGULAR, testCrust, listOf(testTopping), listOf())))
    }

    @Test
    fun testRestockSide() {
        InventoryService.restockSide(testSide, 4)
        assertTrue(InventoryService.checkAvailability(Order(testPizza, Size.REGULAR, testCrust, listOf(), listOf(testSide))))
    }
}
