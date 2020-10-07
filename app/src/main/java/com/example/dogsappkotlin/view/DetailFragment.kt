package com.example.dogsappkotlin.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.dogsappkotlin.R
import com.example.dogsappkotlin.databinding.FragmentDetailBinding
import com.example.dogsappkotlin.model.DogPalette
import com.example.dogsappkotlin.util.getProgressDrawable
import com.example.dogsappkotlin.util.loadImage
import com.example.dogsappkotlin.viewmodel.DogDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DogDetailViewModel
    private var dogUuid = 0
    private lateinit var dataBinding : FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false)
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DogDetailViewModel::class.java)
        viewModel.fetch(dogUuid)



        observeViewModel()

//        val action = DetailFragmentDirections.actionListFragment()
//        Navigation.findNavController(id).navigate(action)
    }

    private fun observeViewModel() {

        viewModel.dogLiveData.observe(viewLifecycleOwner, Observer { dog ->
            dog?.let { it ->
//                dogName.text = dog.dogBreed
//                dogPurpose.text = dog.bredFor
//                dogTemperament.text = dog.temperament
//                dogLifeSpan.text = dog.lifeSpan
//                context?.let {
//                    dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))
//                }
                dataBinding.dogDetail = dog

                it.imageUrl?.let {
                    setUpBackgroundColor(it)
                }
            }
        })
    }

    fun setUpBackgroundColor(url : String)
    {

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate {palette ->
                            var intColor = palette?.vibrantSwatch?.rgb ?: 0
                            var paletteColor = DogPalette(intColor)
                            dataBinding.palette = paletteColor

                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
    }
}