package com.example.dogsappkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogsappkotlin.R
import com.example.dogsappkotlin.viewmodel.DogViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: DogViewModel
    private val dogBreedAdapter = DogBreedAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val action = ListFragmentDirections.actionDetailFragment();
//        Navigation.findNavController(id).navigate(action)

        viewModel = ViewModelProviders.of(this).get(DogViewModel::class.java)
        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogBreedAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.dogs.observe(viewLifecycleOwner, Observer {dogs ->
            dogs?.let {

                dogsList.visibility = View.VISIBLE
                dogBreedAdapter.updateDogsList(dogs)
                listError.visibility = View.GONE
                loadingView.visibility = View.GONE
            }
        })

        viewModel.dogLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
                dogsList.visibility = View.GONE
                loadingView.visibility = View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE

                if (it)
                {
                    dogsList.visibility = View.GONE
                    listError.visibility = View.GONE
                }
            }
        })
    }

}