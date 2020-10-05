package com.example.dogsappkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsappkotlin.R
import com.example.dogsappkotlin.model.DogBreed
import com.example.dogsappkotlin.util.getProgressDrawable
import com.example.dogsappkotlin.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogBreedAdapter(private val dogList : ArrayList<DogBreed>): RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>(){


    fun updateDogsList(newlist : ArrayList<DogBreed>)
    {
        dogList.clear()
        dogList.addAll(newlist)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog,parent,false)
        return DogBreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {

        holder.itemView.name.text = dogList[position].dogBreed
        holder.itemView.lifeSpan.text = dogList[position].lifeSpan
        holder.itemView.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = dogList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.loadImage(dogList[position].imageUrl, getProgressDrawable(holder.itemView.context))
    }

    override fun getItemCount() = dogList.size

    class DogBreedViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {

    }
}