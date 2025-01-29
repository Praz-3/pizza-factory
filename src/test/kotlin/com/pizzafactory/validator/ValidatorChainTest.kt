package com.pizzafactory.validator

import com.pizzafactory.model.*
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*

class ValidatorChainTest {

    private lateinit var vegPizza: Pizza
    private lateinit var nonVegPizza: Pizza

    @BeforeTest
    fun setup() {
        vegPizza = Pizza("Veg Supreme", true, mutableMapOf(Size.REGULAR to 150))
        nonVegPizza = Pizza("Chicken Feast", false, mutableMapOf(Size.REGULAR to 200))
    }

    @Test
    fun testValidVegetarianOrder() {
        val order =
            Order(vegPizza, Size.REGULAR, Crust.NEW_HAND_TOSSED, listOf(Topping("Mushroom", 30, true)), listOf())
        assertDoesNotThrow { ValidatorChain.validate(order) }
    }

    @Test
    fun testValidNonVegetarianOrder() {
        val order = Order(
            nonVegPizza,
            Size.REGULAR,
            Crust.NEW_HAND_TOSSED,
            listOf(Topping("Grilled Chicken", 40, false)),
            listOf()
        )
        assertDoesNotThrow { ValidatorChain.validate(order) }
    }

    @Test
    fun testInvalidVegetarianOrder() {
        val order =
            Order(vegPizza, Size.REGULAR, Crust.NEW_HAND_TOSSED, listOf(Topping("Chicken Tikka", 35, false)), listOf())
        val exception = assertFailsWith<IllegalArgumentException> { ValidatorChain.validate(order) }
        assertEquals("Vegetarian pizzas cannot have non-vegetarian toppings.", exception.message)
    }

    @Test
    fun testInvalidNonVegetarianOrder() {
        val order =
            Order(nonVegPizza, Size.REGULAR, Crust.NEW_HAND_TOSSED, listOf(Topping("Paneer", 35, true)), listOf())
        val exception = assertFailsWith<IllegalArgumentException> { ValidatorChain.validate(order) }
        assertEquals("Non-Vegetarian pizzas cannot have Paneer topping.", exception.message)
    }
}
