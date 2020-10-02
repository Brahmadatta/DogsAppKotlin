package com.example.dogsappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsappkotlin.model.DogBreed

class DogViewModel : ViewModel() {

    val dogs = MutableLiveData<ArrayList<DogBreed>>()
    val dogLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val dog1 = DogBreed("1","rotweiler","23 years","breedGroup","bredFor","temperament", "")
        val dog2 = DogBreed("2","Labrador","24 years","breedGroup","bredFor","temperament", "")
        val dog3 = DogBreed("3","dobberman","25 years","breedGroup","bredFor","temperament", "")

        val dogsList = arrayListOf<DogBreed>(dog1,dog2,dog3)

        dogs.value = dogsList
        dogLoadError.value = false
        loading.value = false


    }
}