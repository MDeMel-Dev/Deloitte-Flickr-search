package com.example.deloitte_flickr_search

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.deloitte_flickr_search.models.PhotoItem
import com.example.deloitte_flickr_search.repository.PhotoRepository

import com.example.deloitte_flickr_search.networking.MainResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val mainRepo: PhotoRepository) : ViewModel() {

    //START LOAD DATA VALUES
    private val _flickrLiveData = MutableLiveData<PhotoRepository.ViewState>().apply {
        value = null
    }
    val flickrLiveData: LiveData<PhotoRepository.ViewState> = _flickrLiveData

    //PAGINATION VALUES
    private val _paginateLiveData = MutableLiveData<PhotoRepository.ViewState>().apply {
        value = null
    }
    val paginateLiveData: LiveData<PhotoRepository.ViewState> = _paginateLiveData

    var nextPage = 2

    var currentText = "trees"


    init {

            loadAPIData("trees" )

    }

    // API CALL START FOR SEARCH AND ON START
    fun loadAPIData(input: String ) {

        mainRepo.getPhotoListObserver().observeForever(Observer<PhotoRepository.ViewState>{

                _flickrLiveData.value = it

        })
        mainRepo.searchFlickrApi(input)
    }

    //API CALL START FOR PAGINATION
    fun paginateAPIData(input: String , pageNo: Int) {

        mainRepo.paginateObserver().observeForever(Observer<PhotoRepository.ViewState>{


                _paginateLiveData.value = it
//                it.photos.photo.toTypedArray().toCollection(ArrayList())

        })
        mainRepo.paginateFlickrApi(input, pageNo.toString())
    }


    //REMOVES RXJAVA OBSERVER
    override fun onCleared() {
        mainRepo.getPhotoListObserver().removeObserver(Observer {  })
        mainRepo.paginateObserver().removeObserver(Observer {  })
        super.onCleared()
    }
}

