package com.example.helloworld

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(var name: String, var isStudent: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}