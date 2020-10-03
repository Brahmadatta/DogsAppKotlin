package com.example.dogsappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsappkotlin.model.DogBreed
import com.example.dogsappkotlin.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DogViewModel : ViewModel() {

    private val dogsApiService = DogsApiService()

    private val compositeDisposable = CompositeDisposable()

    val dogs = MutableLiveData<ArrayList<DogBreed>>()
    val dogLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){

        fetchRemote()
    }


    private fun fetchRemote()
    {
        compositeDisposable.add(dogsApiService.getDogs().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ArrayList<DogBreed>>(){
                override fun onSuccess(list : ArrayList<DogBreed>) {

                    dogs.value = list
                    dogLoadError.value = false
                    loading.value = false
                }

                override fun onError(e: Throwable) {

                    dogLoadError.value = true
                    loading.value = false
                }

            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}