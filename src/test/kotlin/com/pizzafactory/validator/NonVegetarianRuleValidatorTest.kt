package com.pizzafactory.validator

import com.pizzafactory.model.*
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.*

class NonVegetarianRuleValidatorTest {

    private lateinit var nonVegPizza: Pizza

    @BeforeTest
    fun setup() {
        nonVegPizza = Pizza("Chicken Feast", false, mutableMapOf(Size.REGULAR to 200))
    }

    @Test
    fun testValidNonVegetarianToppings() {
        val order = Order(
            nonVegPizza,
            Size.REGULAR,
            Crust.NEW_HAND_TOSSED,
            listOf(Topping("Grilled Chicken", 40, false)),
            listOf()
        )
        assertDoesNotThrow { NonVegetarianRuleValidator.validate(order) }
    }

    @Test
    fun testInvalidNonVegetarianToppings() {
        val order =
            Order(nonVegPizza, Size.REGULAR, Crust.NEW_HAND_TOSSED, listOf(Topping("Paneer", 35, true)), listOf())
        val exception = assertFailsWith<IllegalArgumentException> { NonVegetarianRuleValidator.validate(order) }
        assertEquals("Non-Vegetarian pizzas cannot have Paneer topping.", exception.message)
    }
}
