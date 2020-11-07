package com.example.helloworld

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PersonDB : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        private var instance: PersonDB? = null

        fun getDatabase(context: Context): PersonDB {
            val tmpInstance = instance
            if (tmpInstance != null)
                return tmpInstance
            val inst = Room.databaseBuilder(context.applicationContext, PersonDB::class.java, "personDB")
                    .allowMainThreadQueries().build()
            instance = inst
            return instance as PersonDB
        }
    }
}