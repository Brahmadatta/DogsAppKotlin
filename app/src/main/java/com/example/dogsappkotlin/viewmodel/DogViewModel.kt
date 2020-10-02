package com.example.dogsappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsappkotlin.model.DogBreed

class DogViewModel : ViewModel() {

    val dogs = MutableLiveData<List<DogBreed>>()
    val dogLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val dog1 = DogBreed("1","Labrador","23 years","breedGroup","bredFor","temperament", "")
        val dog2 = DogBreed("2","Labrador","23 years","breedGroup","bredFor","temperament", "")
        val dog3 = DogBreed("3","Labrador","23 years","breedGroup","bredFor","temperament", "")
        val dog4 = DogBreed("4","Labrador","23 years","breedGroup","bredFor","temperament", "")
        val dog5 = DogBreed("5","Labrador","23 years","breedGroup","bredFor","temperament", "")
        val dog6 = DogBreed("6","Labrador","23 years","breedGroup","bredFor","temperament", "")

        val dogsList = arrayListOf<DogBreed>(dog1,dog2,dog3,dog4,dog5,dog6)

        dogs.value = dogsList
        dogLoadError.value = false
        loading.value = false


    }
}