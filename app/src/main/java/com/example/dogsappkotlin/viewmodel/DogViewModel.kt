package com.example.dogsappkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsappkotlin.model.DogBreed
import com.example.dogsappkotlin.model.DogDatabase
import com.example.dogsappkotlin.model.DogsApiService
import com.example.dogsappkotlin.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DogViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferenceHelper(getApplication())

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

                    storeDogsLocally(list)


                }

                override fun onError(e: Throwable) {

                    dogLoadError.value = true
                    loading.value = false
                }

            })
        )
    }

    private fun dogsRetrieved(dogsList: ArrayList<DogBreed>)
    {
        dogs.value = dogsList
        dogLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(dogsList: ArrayList<DogBreed>)
    {

        //coroutine function to execute in separate thread.
        launch {

            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAllDogs(*dogsList.toTypedArray())
            var i = 0
            while (i < dogsList.size)
            {
                dogsList[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(dogsList)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}