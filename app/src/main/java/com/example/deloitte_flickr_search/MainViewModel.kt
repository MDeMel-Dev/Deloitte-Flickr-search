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

    init {

            loadAPIData("fireworks" , application )

    }

    fun loadAPIData(input: String , context: Context) {
//        viewModel =  ViewModelProvider(this.requireActivity(), com.example.kiddster.MainViewModel.Factory(requireActivity()!!.application))
//            .get(MainViewModel::class.java)
        mainRepo.getBookListObserver().observeForever(Observer<MainResponse>{
            if(it != null) {
                //update adapter...
                _flickrLiveData.value = it.photos.photo.toTypedArray().toCollection(ArrayList())
//                bookListAdapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(context, "Error in fetching photos", Toast.LENGTH_SHORT).show()
            }
        })
        mainRepo.searchFlickrApi(input)
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
        mainRepo.getBookListObserver().removeObserver(Observer {  })
        super.onCleared()
    }
}

// must observe repo data and once posted must update live data that is
// being observed by recyclerview adapter
// once adapter is updated notify data set changed