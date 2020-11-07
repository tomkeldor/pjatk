package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.databinding.ActivityListBinding
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rv1.layoutManager = LinearLayoutManager(baseContext)
        binding.rv1.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
        val personViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PersonViewModel::class.java)
        personViewModel.allPeople.observe(this, Observer { people ->
            people?.let {
                (binding.rv1.adapter as MyAdapter).setPeople(it)
            }

        })
        personViewModel.insert(Person("Michail", true))
        personViewModel.insert(Person("Radek", false))
        binding.rv1.adapter = MyAdapter(personViewModel)
    }
}