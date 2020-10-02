package com.example.dogsappkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsappkotlin.R
import com.example.dogsappkotlin.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

class DogBreedAdapter(val dogList : ArrayList<DogBreed>): RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>(){


    fun updateDogsList(newlist : ArrayList<DogBreed>)
    {
        dogList.clear()
        dogList.addAll(newlist)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dog,parent,false)
        return DogBreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {

        holder.itemView.name.text = dogList[position].dogBreed
        holder.itemView.lifeSpan.text = dogList[position].lifeSpan
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
        }
    }

    override fun getItemCount() = dogList.size

    class DogBreedViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {

    }
}