package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PersonViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: PersonRepository
    val allPeople: LiveData<List<Person>>

    init {
        val personDao = PersonDB.getDatabase(app).personDao()
        repo = PersonRepository(personDao)
        allPeople = repo.allPeople
    }

    fun insert(person: Person) = repo.insert(person)
    fun delete(person: Person) = repo.delete(person)
}