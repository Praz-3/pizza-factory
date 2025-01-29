package com.pizzafactory.validator

import com.pizzafactory.model.*
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import kotlin.test.*

class VegetarianRuleValidatorTest {

    private lateinit var vegPizza: Pizza

    @BeforeTest
    fun setup() {
        vegPizza = Pizza("Veg Deluxe", true, mutableMapOf(Size.REGULAR to 150))
    }

    @Test
    fun testValidVegetarianToppings() {
        val order = Order(
            vegPizza,
            Size.REGULAR,
            Crust.NEW_HAND_TOSSED,
            listOf(Topping("Mushroom", 30, true), Topping("Capsicum", 25, true)),
            listOf()
        )
        assertDoesNotThrow { VegetarianRuleValidator.validate(order) }
    }

    @Test
    fun testInvalidVegetarianToppings() {
        val order =
            Order(vegPizza, Size.REGULAR, Crust.NEW_HAND_TOSSED, listOf(Topping("Chicken Tikka", 35, false)), listOf())
        val exception = assertFailsWith<IllegalArgumentException> { VegetarianRuleValidator.validate(order) }
        assertEquals("Vegetarian pizzas cannot have non-vegetarian toppings.", exception.message)
    }
}
