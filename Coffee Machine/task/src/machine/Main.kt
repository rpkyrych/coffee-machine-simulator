package machine

import java.util.*

fun main() {

    val scanner = Scanner(System.`in`)
    val coffeeMachine = CoffeeMachine()

    var isExit = false
    menu@ do {
        println("Write action (buy, fill, take, remaining, exit): ")
        when (scanner.nextLine()) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ")
                when (scanner.nextLine()) {
                    "1" -> coffeeMachine.buyDrink(CoffeeMachine.CoffeeType.Espresso)
                    "2" -> coffeeMachine.buyDrink(CoffeeMachine.CoffeeType.Latte)
                    "3" -> coffeeMachine.buyDrink(CoffeeMachine.CoffeeType.Cappuccino)
                    "back" -> continue@menu
                }
            }
            "fill" -> {
                printFillMessage("ml", "water")
                coffeeMachine.water += scanner.nextInt()
                printFillMessage("ml", "milk")
                coffeeMachine.milk += scanner.nextInt()
                printFillMessage("grams", "coffee beans")
                coffeeMachine.beans += scanner.nextInt()
                printFillMessage("disposable cups", "coffee")
                coffeeMachine.cups += scanner.nextInt()
            }
            "take" -> {
                println("I gave you ${coffeeMachine.money}")
                coffeeMachine.money = 0
            }
            "remaining" -> coffeeMachine.printStatus()
            "exit" -> isExit = true
            else -> println("Try one more time")
        }
    } while (!isExit)
}

class CoffeeMachine {
    var water = 400
    var milk = 540
    var beans = 120
    var money = 550
    var cups = 9

    enum class CoffeeType(val water: Int, val milk: Int, val beans: Int, val money: Int) {
        Espresso(250, 0, 16, 4),
        Latte(350, 75, 20, 7),
        Cappuccino(200, 100, 12, 6)
    }

    fun buyDrink(type: CoffeeType) {
        val isEnoughWater = checkIfEnough(this.water, type.water, "water")
        val isEnoughMilk = checkIfEnough(this.water, type.milk, "milk")
        val isEnoughBeans = checkIfEnough(this.beans, type.beans, "beans")
        val isEnoughCups = checkIfEnough(this.cups, 1, "cups")
        if (isEnoughWater && isEnoughMilk && isEnoughBeans && isEnoughCups) {
            println("I have enough resources, making you a coffee!")
            this.water -= type.water
            this.milk -= type.milk
            this.beans -= type.beans
            --this.cups
            this.money += type.money
        }
    }

    private fun checkIfEnough(param: Int, quantity: Int, paramName: String): Boolean {
        return if (param - quantity >= 0) {
            true
        } else {
            println("Sorry, not enough $paramName!")
            false
        }
    }

    fun printStatus() {
        println("The coffee machine has:\n" +
                "$water of water\n" +
                "$milk of milk\n" +
                "$beans of coffee beans\n" +
                "$cups of disposable cups\n" +
                "$money of money\n")
    }
}

fun printFillMessage(measure: String, product: String) {
    println("Write how many $measure of $product the coffee machine has: ")
}
