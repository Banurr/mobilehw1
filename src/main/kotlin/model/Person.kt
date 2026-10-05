package kbtu.kz.model

open class Person(val id: Int, val name: String) {

    open fun greet() {
        println("Hi, i am person with id: $id and my name is: $name")
    }
}