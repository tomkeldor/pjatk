package com.example.helloworld

import com.google.firebase.database.Exclude

data class Shop(var name: String, var desc: String, var coords: String, var radius: Long) {
    var id: String = ""
    var favorite: Boolean = false

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "desc" to desc,
            "coords" to coords,
            "radius" to radius,
            "favorite" to favorite
        )
    }
}
