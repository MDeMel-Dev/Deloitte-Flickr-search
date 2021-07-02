package com.example.deloitte_flickr_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deloitte_flickr_search.data.PhotoItem
import com.example.deloitte_flickr_search.data.PhotoRepo

import com.example.deloitte_flickr_search.networking.FlickrApi
import com.example.deloitte_flickr_search.networking.MainResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepo = PhotoRepo()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    lateinit var flickrLiveData: LiveData<List<PhotoItem>>

    init {

            flickrLiveData =   mainRepo.fetchData()

    }
}