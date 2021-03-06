package com.example.dogsappkotlin.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val action = ListFragmentDirections.actionDetailFragment();
//        Navigation.findNavController(id).navigate(action)

        viewModel = ViewModelProviders.of(this).get(DogViewModel::class.java)
        observeViewModel()

        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogBreedAdapter
            dogsList.adapter = dogBreedAdapter
        }

        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            listError.visibility = View.GONE
            viewModel.refreshByPassCache()
            refreshLayout.isRefreshing = false
        }

    }

//    fun observeViewModel() {
//
//        viewModel.dogs.observe(viewLifecycleOwner, Observer {dogs ->
//            dogs?.let {
//                dogsList.visibility = View.VISIBLE
//                dogBreedAdapter.updateDogsList(dogs)
//            }
//        })
//
//        viewModel.dogLoadError.observe(viewLifecycleOwner, Observer {isError ->
//            isError?.let {
//                listError.visibility = if(it) View.VISIBLE else View.GONE
//            }
//        })
//
//        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
//            isLoading?.let {
//                loadingView.visibility = if(it) View.VISIBLE else View.GONE
//                if(it) {
//                    listError.visibility = View.GONE
//                    dogsList.visibility = View.GONE
//                }
//            }
//        })
//    }

    fun observeViewModel()
    {
        viewModel.dogs.observe(viewLifecycleOwner, Observer { dogs ->
            dogs?.let {
                dogsList.visibility = View.VISIBLE
                dogBreedAdapter.updateDogsList(dogs)
            }
        })

        viewModel.dogLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it)
                {
                    listError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.actionSettings -> {
                view?.let {
                    Navigation.findNavController(it).navigate(ListFragmentDirections.actionSettings())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}