package com.pizzafactory

import com.pizzafactory.model.*
import com.pizzafactory.service.*

object PizzaFactoryConsole {
    fun start() {
        while (true) {
            println("Welcome to PizzaFactory!")
            println("Are you a (1) Customer or (2) Vendor?")
            when (readlnOrNull()?.trim()) {
                "1" -> handleCustomer()
                "2" -> handleVendor()
                else -> println("Invalid choice, exiting.")
            }
        }
    }

    private fun handleCustomer() {
        println("Available Pizzas:")
        val pizzas = MenuService.getPizzas()
        pizzas.forEachIndexed { index, pizza -> println("${index + 1}. ${pizza.name}") }

        print("Select pizza by number: ")
        val pizzaChoice =
            readlnOrNull()?.toIntOrNull()?.let { pizzas.getOrNull(it - 1) } ?: return println("Invalid selection.")

        print("Select size (1. Regular, 2. Medium, 3. Large): ")
        val size = when (readlnOrNull()?.trim()) {
            "1" -> Size.REGULAR
            "2" -> Size.MEDIUM
            "3" -> Size.LARGE
            else -> return println("Invalid size.")
        }

        print("Select crust (1. New Hand Tossed, 2. Wheat Thin Crust, 3. Cheese Burst, 4. Fresh Pan Pizza): ")
        val crust = when (readlnOrNull()?.trim()) {
            "1" -> Crust.NEW_HAND_TOSSED
            "2" -> Crust.WHEAT_THIN_CRUST
            "3" -> Crust.CHEESE_BURST
            "4" -> Crust.FRESH_PAN_PIZZA
            else -> return println("Invalid crust.")
        }

        println("Available Toppings:")
        val toppings = MenuService.getToppings()
        toppings.forEachIndexed { index, topping -> println("${index + 1}. ${topping.name} - Rs.${topping.price}") }

        print("Select toppings (comma separated numbers, or none): ")
        val selectedToppings =
            readlnOrNull()?.split(",")?.mapNotNull { it.trim().toIntOrNull()?.let { i -> toppings.getOrNull(i - 1) } }
                ?: emptyList()

        println("Available Sides:")
        val sides = MenuService.getSides()
        sides.forEachIndexed { index, side -> println("${index + 1}. ${side.name} - Rs.${side.price}") }

        print("Select sides (comma separated numbers, or none): ")
        val selectedSides =
            readlnOrNull()?.split(",")?.mapNotNull { it.trim().toIntOrNull()?.let { i -> sides.getOrNull(i - 1) } }
                ?: emptyList()

        val order = Order(pizzaChoice, size, crust, selectedToppings, selectedSides)
        println("Order Items: $order")
        println("Order total: Rs.${order.getTotalPrice()}")
        print("Confirm order? (yes/no): ")
        if (readlnOrNull()?.trim()?.lowercase() == "yes") {
            try {
                if (OrderService.processOrder(order)) println("Order placed successfully!") else println("Insufficient inventory.")
            } catch (e: Exception) {
                println("Something went wrong! \n${e.message}")
            }
        }
    }

    private fun handleVendor() {
        println("Vendor Options:")
        println("1. Add new pizza")
        println("2. Restock inventory")
        println("3. Update pizza prices")
        when (readlnOrNull()?.trim()) {
            "1" -> addNewPizza()
            "2" -> restockInventory()
            "3" -> updatePizzaPrices()
            else -> println("Invalid choice.")
        }
    }

    private fun addNewPizza() {
        print("Enter pizza name: ")
        val name = readlnOrNull()?.trim() ?: return
        print("Is it vegetarian? (yes/no): ")
        val isVeg = readlnOrNull()?.trim()?.lowercase() == "yes"
        val prices = mutableMapOf<Size, Long>()
        Size.entries.forEach {
            print("Enter price for ${it.name}: ")
            prices[it] = readlnOrNull()?.toLongOrNull() ?: return println("Invalid price.")
        }
        MenuService.addPizza(Pizza(name, isVeg, prices))
        println("Pizza added successfully!")
    }

    private fun restockInventory() {
        print("Enter item name to restock: ")
        val name = readlnOrNull()?.trim() ?: return
        print("Enter quantity: ")
        val quantity = readlnOrNull()?.toIntOrNull() ?: return println("Invalid quantity.")
        println("Restocked $name with $quantity units.")
    }

    private fun updatePizzaPrices() {
        println("Available Pizzas:")
        val pizzas = MenuService.getPizzas()
        pizzas.forEachIndexed { index, pizza -> println("${index + 1}. ${pizza.name}") }
        print("Select pizza by number: ")
        val pizza =
            readlnOrNull()?.toIntOrNull()?.let { pizzas.getOrNull(it - 1) } ?: return println("Invalid selection.")
        Size.entries.forEach {
            print("Enter new price for ${it.name}: ")
            pizza.pricesBySize[it] = readlnOrNull()?.toLongOrNull() ?: return println("Invalid price.")
        }
        println("Pizza prices updated successfully!")
    }
}

private fun loadDefaults() {
    val defaultPizzas: List<Pizza> = listOf(
        Pizza("Deluxe Veggie", true, mutableMapOf(Size.REGULAR to 150, Size.MEDIUM to 200, Size.LARGE to 325)),
        Pizza("Cheese and Corn", true, mutableMapOf(Size.REGULAR to 175, Size.MEDIUM to 375, Size.LARGE to 475)),
        Pizza("Paneer Tikka", true, mutableMapOf(Size.REGULAR to 160, Size.MEDIUM to 290, Size.LARGE to 340)),
        Pizza("Non-Veg Supreme", false, mutableMapOf(Size.REGULAR to 190, Size.MEDIUM to 325, Size.LARGE to 425)),
        Pizza("Chicken Tikka", false, mutableMapOf(Size.REGULAR to 210, Size.MEDIUM to 370, Size.LARGE to 500)),
        Pizza(
            "Pepper Barbecue Chicken",
            false,
            mutableMapOf(Size.REGULAR to 220, Size.MEDIUM to 380, Size.LARGE to 525)
        )
    )
    val defaultToppings: List<Topping> = listOf(
        Topping("Black Olive", 20, true),
        Topping("Capsicum", 25, true),
        Topping("Paneer", 35, true),
        Topping("Mushroom", 30, true),
        Topping("Fresh Tomato", 10, true),
        Topping("Chicken Tikka", 35, false),
        Topping("Barbeque Chicken", 45, false),
        Topping("Grilled Chicken", 40, false),
        Topping("Extra Cheese", 35, true)
    )
    val defaultSides: List<Side> = listOf(
        Side("Cold Drink", 55),
        Side("Mousse Cake", 90)
    )

    defaultPizzas.forEach { pizza ->
        MenuService.addPizza(pizza)
        InventoryService.restockPizza(pizza, 5)
    }
    defaultSides.forEach { side ->
        MenuService.addSide(side)
        InventoryService.restockSide(side, 3)
    }
    defaultToppings.forEach { topping ->
        MenuService.addTopping(topping)
        InventoryService.restockTopping(topping, 5)
    }
    Crust.entries.forEach { crust ->
        InventoryService.restockCrust(crust, 10)
    }
}

fun main() {
    loadDefaults()
    PizzaFactoryConsole.start()
}