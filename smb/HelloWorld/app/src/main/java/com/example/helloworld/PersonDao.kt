package com.example.helloworld

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {

    @Query("SELECT * FROM person_table")
    fun getPeople(): LiveData<List<Person>>

    @Insert
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)
}