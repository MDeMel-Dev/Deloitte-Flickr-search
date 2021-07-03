package com.example.deloitte_flickr_search

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.example.deloitte_flickr_search.data.PhotoItem
import com.example.deloitte_flickr_search.data.PhotoRepository

import com.example.deloitte_flickr_search.networking.MainResponse

class MainViewModel(application: Application) : ViewModel() {

    private val mainRepo = PhotoRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _flickrLiveData = MutableLiveData<List<PhotoItem>>().apply {
        value = null
    }
    val flickrLiveData: LiveData<List<PhotoItem>> = _flickrLiveData

    //PAGINATION VALUES

    private val _paginateLiveData = MutableLiveData<List<PhotoItem>>().apply {
        value = null
    }
    val paginateLiveData: LiveData<List<PhotoItem>> = _paginateLiveData

    var nextPage = 2

    var currentText = "trees"

    init {

            loadAPIData("trees" , application )

    }

    fun loadAPIData(input: String , context: Context) {

        mainRepo.getPhotoListObserver().observeForever(Observer<MainResponse>{
            if(it != null) {

                _flickrLiveData.value = it.photos.photo.toTypedArray().toCollection(ArrayList())

            }
            else {
                Toast.makeText(context, "Error in fetching photos", Toast.LENGTH_SHORT).show()
            }
        })
        mainRepo.searchFlickrApi(input)
    }

    fun paginateAPIData(input: String , pageNo: Int, context: Context) {

        mainRepo.paginateObserver().observeForever(Observer<MainResponse>{
            if(it != null) {

                _paginateLiveData.value = it.photos.photo.toTypedArray().toCollection(ArrayList())

            }
            else {
                Toast.makeText(context, "Error in fetching photos", Toast.LENGTH_SHORT).show()
            }
        })
        mainRepo.paginateFlickrApi(input, pageNo.toString())
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    override fun onCleared() {
        mainRepo.getPhotoListObserver().removeObserver(Observer {  })
        super.onCleared()
    }
}

