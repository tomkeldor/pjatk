package com.example.helloworld

import android.content.ContentValues
import com.google.firebase.database.Exclude

data class Product(var name: String, var price: Double, var amount: Long, var bought: Boolean) {
    var id: String = ""
    companion object {
        fun fromContentValues(values: ContentValues?): Product {
            values?.let{
                return Product(
                        values.getAsString("name"),
                        values.getAsDouble("price"),
                        values.getAsLong("amount"),
                        values.getAsBoolean("bought"))
            } ?: throw IllegalArgumentException()
        }
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "amount" to amount,
            "bought" to bought
        )
    }
}