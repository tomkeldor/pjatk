package com.example.helloworld

import androidx.lifecycle.LiveData

class PersonRepository(private val personDao: PersonDao) {

    val allPeople: LiveData<List<Person>> = personDao.getPeople()

    suspend fun insert(person: Person){
        personDao.insert(person)
    }

    suspend fun delete(person: Person){
        personDao.delete(person)
    }
}