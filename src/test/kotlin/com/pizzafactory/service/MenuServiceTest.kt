package com.pizzafactory.service

import com.pizzafactory.model.*
import kotlin.test.*

class MenuServiceTest {

    @BeforeTest
    fun setup() {
        MenuService.addSide(Side("Cold Drink", 55))
        MenuService.addPizza(
            Pizza(
                "Deluxe Veggie",
                true,
                mutableMapOf(Size.REGULAR to 150, Size.MEDIUM to 200, Size.LARGE to 325)
            )
        )
    }

    @Test
    fun testAddPizza() {
        val pizza = Pizza("Test Pizza", true, mutableMapOf(Size.REGULAR to 150))
        MenuService.addPizza(pizza)
        assertTrue(MenuService.getPizzas().contains(pizza))
    }

    @Test
    fun testUpdatePizzaPrices() {
        MenuService.updatePizzaPrices("Deluxe Veggie", mapOf(Size.REGULAR to 200))
        val updatedPizza = MenuService.getPizzas().find { it.name == "Deluxe Veggie" }
        assertEquals(200, updatedPizza?.pricesBySize?.get(Size.REGULAR))
    }

    @Test
    fun testAddTopping() {
        val topping = Topping("Black Olive", 20, true)
        MenuService.addTopping(topping)
        assertTrue(MenuService.getToppings().contains(topping))
    }

    @Test
    fun testUpdateToppingPrice() {
        MenuService.updateToppingPrice("Black Olive", 30)
        val updatedTopping = MenuService.getToppings().find { it.name == "Black Olive" }
        assertEquals(30, updatedTopping?.price)
    }

    @Test
    fun testAddSide() {
        val side = Side("Fries", 80)
        MenuService.addSide(side)
        assertTrue(MenuService.getSides().contains(side))
    }

    @Test
    fun testUpdateSidePrice() {
        MenuService.updateSidePrice("Cold Drink", 65)
        val updatedSide = MenuService.getSides().find { it.name == "Cold Drink" }
        assertEquals(65, updatedSide?.price)
    }
}
