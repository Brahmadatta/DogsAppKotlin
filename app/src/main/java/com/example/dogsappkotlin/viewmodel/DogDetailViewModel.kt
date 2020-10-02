package com.example.dogsappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsappkotlin.model.DogBreed

class DogDetailViewModel : ViewModel() {

    val dogLiveData = MutableLiveData<DogBreed>()

    fun fetch()
    {
        val dog = DogBreed("1","rotweiler","23 years","breedGroup","bredFor","temperament", "")
        dogLiveData.value = dog
    }
}