package com.example.helloworld

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.helloworld.databinding.ListElementBinding

class MyAdapter(val personViewModel: PersonViewModel) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var people = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListElementBinding.inflate(inflater)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val currentPerson = people[position]
        holder.binding.rvTv1.text = currentPerson.name
        holder.binding.rvCb1.isChecked = currentPerson.isStudent
        holder.binding.rvTv1.setOnClickListener {
            personViewModel.delete(currentPerson)
        }
    }

    override fun getItemCount(): Int = people.size

    inner class ViewHolder(val binding: ListElementBinding) : RecyclerView.ViewHolder(binding.root)

    fun setPeople(people: List<Person>) {
        this.people = people
        notifyDataSetChanged()
    }
}