package com.example.dogsappkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsappkotlin.R
import com.example.dogsappkotlin.databinding.ItemDogBinding
import com.example.dogsappkotlin.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

class DogBreedAdapter(private val dogList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogBreedAdapter.DogBreedViewHolder>(), DogClickListener {


    fun updateDogsList(newlist: ArrayList<DogBreed>) {
        dogList.clear()
        dogList.addAll(newlist)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_dog,parent,false)
        val view =
            DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogBreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogBreedViewHolder, position: Int) {

        holder.view.dog = dogList[position]
        holder.view.listener = this

//        holder.itemView.name.text = dogList[position].dogBreed
//        holder.itemView.lifeSpan.text = dogList[position].lifeSpan
//        holder.itemView.setOnClickListener {
//            val action = ListFragmentDirections.actionDetailFragment()
//            action.dogUuid = dogList[position].uuid
//            Navigation.findNavController(it).navigate(action)
//        }
//        holder.itemView.imageView.loadImage(dogList[position].imageUrl, getProgressDrawable(holder.itemView.context))
    }

    override fun getItemCount() = dogList.size

    class DogBreedViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onDogClicked(v: View) {

        val uuid = v.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }
}