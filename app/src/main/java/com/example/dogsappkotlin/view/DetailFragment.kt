package com.example.dogsappkotlin.view

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
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
import com.example.dogsappkotlin.databinding.SendSmsDialogBinding
import com.example.dogsappkotlin.model.DogBreed
import com.example.dogsappkotlin.model.DogPalette
import com.example.dogsappkotlin.model.SmsInfo
import com.example.dogsappkotlin.util.getProgressDrawable
import com.example.dogsappkotlin.util.loadImage
import com.example.dogsappkotlin.viewmodel.DogDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DogDetailViewModel
    private var dogUuid = 0
    private lateinit var dataBinding : FragmentDetailBinding

    private var sendSmsPermission = false
    private var currentDog : DogBreed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
            currentDog = dog
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_send_sms -> {

                sendSmsPermission = true
                (activity as MainActivity).checkSmsPermission()
            }

            R.id.action_share -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted : Boolean){

        if (sendSmsPermission && permissionGranted)
        {
            context?.let {
                val smsInfo = SmsInfo("","${currentDog?.dogBreed} bred for ${currentDog?.bredFor}",currentDog?.imageUrl)

                val dialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(LayoutInflater.from(it),R.layout.send_sms_dialog,null,false)

                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send SMS"){dialog,which ->
                        if (!dialogBinding.smsDestination.text.isNullOrEmpty())
                        {
                            smsInfo.to = dialogBinding.smsInfo?.text.toString()
                            sendSms(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){dialog,which -> }
                    .show()

                dialogBinding.smsInfo = smsInfo
            }
        }
    }

    private fun sendSms(smsInfo: SmsInfo)
    {
        val intent = Intent(context,MainActivity::class.java)
        val pi = PendingIntent.getActivity(context,0,intent,0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(smsInfo.to,null,smsInfo.text,pi,null)


    }
}