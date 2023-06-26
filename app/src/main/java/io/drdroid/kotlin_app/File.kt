package io.drdroid.kotlin_app

import androidx.core.text.isDigitsOnly
import java.util.Calendar
import java.util.Scanner

enum class Company(var title: String, var code: Int) {
    ACME("ACME", 1121), ALFA("ALFA", 1111), AMEX("AMEX", 3796)
}

//enum class Card(var exp: Int) {
//    ACME(1121), ALFA(1111), AMEX(3796)
//}

fun main() {
//    println("test")

//    val companies = mapOf("ACME" to 1121, "ALFA" to 1111, "AMEX" to 3796)

//    val name = Company.ACME.name

    print("Please, enter your card number: ")
    val reader = Scanner(System.`in`)
    var input = reader.next()
    //check input length
    while (input.length != 16 && input.length != 19) {
        print("Your format is wrong, please try again: ")
        input = reader.next()
    }
    //check for input content (ln16)
//    while (input.length == 16 && !input.isDigitsOnly()) { -> causes runtime error Stub!
    while (input.length == 16 && !input.matches(Regex("\\d{16}"))) {
        //force digit only
        print("Only numbers are allowed for that format: ")
        input = reader.next()
    }
    //check for input content (ln19)
//    while (input.length == 19 && input.isDigitsOnly()) { -> causes runtime error Stub!
    while (input.length == 19 && !input.matches(Regex("\\d{4}\\D\\d{4}\\D\\d{4}\\D\\d{4}"))) {
        //force separator
        print("16 numbers separated in group of 4: ")
        input = reader.next()
    }
    //get only the digits
    if (input.length > 16) {
        input = input.filter { it.isDigit() }
    }

    val cardInput = input.chunked(4)
    val cardCompany = cardInput.first()
    val carExp = cardInput.last()
    val month: Int = carExp.chunked(2).first().toInt()
    val year: Int = carExp.chunked(2).last().toInt()

    val calendar = Calendar.getInstance()
    if (month !in 1..12 || month < calendar.get(Calendar.MONTH) || year < (calendar.get(Calendar.YEAR) % 10)) {
        //wrong date
        println("Check your expiry date and try again: ")
        main()
        return
    }
//    println(cardInput)

    if (Company.values().any { it.code == cardCompany.toInt() }) {
        println("You are good to go!")
    } else {
        //wrong code
        println("You are not authorized in here!")

        print("Do you want to try again? (Y/N): ")
        if (reader.next().equals("Y", true)) {
            main()
        }
    }
}