package com.example.helloworld

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(var name: String, var price: Double, var amount: Int, var isBought: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        fun fromContentValues(values: ContentValues?): Product {
            values?.let{
                return Product(
                        values.getAsString("name"),
                        values.getAsDouble("price"),
                        values.getAsInteger("amount"),
                        values.getAsBoolean("isBought"))
            } ?: throw IllegalArgumentException()
        }
    }
}