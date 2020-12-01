package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PersonViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: PersonRepository
    val allPeople: LiveData<List<Person>>

    init {
        val personDao = PersonDB.getDatabase(app).personDao()
        repo = PersonRepository(personDao)
        allPeople = repo.allPeople
    }

    fun insert(person: Person) {
        CoroutineScope(IO).launch {
            repo.insert(person)
        }
    }
    fun delete(person: Person) {
        CoroutineScope(IO).launch {
            repo.delete(person)
        }
    }
}