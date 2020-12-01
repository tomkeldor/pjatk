package com.example.myapp

import kotlinx.serialization.*

@Serializable
class Product(var name: String, var price: Double, var amount: Int, var isBought: Boolean) {
    var id: Long = 0
}