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
class MainViewModel @Inject constructor( @ApplicationContext private val application: Context , private val mainRepo: PhotoRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    //START LOAD DATA VALUES
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

    // API CALL START FOR SEARCH AND ON START
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

    //API CALL START FOR PAGINATION
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

    //FACTORY METHOD
//    class Factory(val app: Application) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return MainViewModel(app) as T
//            }
//            throw IllegalArgumentException("Unable to construct viewmodel")
//        }
//    }

    //REMOVES RXJAVA OBSERVER
    override fun onCleared() {
        mainRepo.getPhotoListObserver().removeObserver(Observer {  })
        super.onCleared()
    }
}

