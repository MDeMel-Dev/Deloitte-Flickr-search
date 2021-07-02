package com.example.deloitte_flickr_search.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.deloitte_flickr_search.networking.FlickrApi
import com.example.deloitte_flickr_search.networking.MainResponse
import com.example.deloitte_flickr_search.networking.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class PhotoRepository {

    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

//    fun fetchData(): LiveData<List<PhotoItem>> {
//
//        val responseLiveData: MutableLiveData<List<PhotoItem>> = MutableLiveData()
//        val flickrRequest: Call<MainResponse> = flickrApi.fetchData()
//
//        flickrRequest.enqueue(object : Callback<MainResponse> {
//
////            override fun onResponse( call: Call<String>,
////                                     response: Response<String>
////            )
////            {
////
////                response.body()?.let { Log.d("mane", it) }
////                 }
////
////
////            override fun onFailure(call: Call<String>, t: Throwable)
////            {
////                Log.e("mane", "Failed to fetch photos", t)
////            }
////
////        })
//
//            override fun onResponse( call: Call<MainResponse>,
//                                     response: Response<MainResponse>
//            )
//            {
//
//                Log.d("mane", "Response received")
//                // ADD A PROCESS JSON FUNC HERE
//                val mainResponse: MainResponse? = response.body()
//                val processedResponse = mainResponse?.photos
//                val photoItems: List<PhotoItem> = processedResponse?.photoItems ?: mutableListOf()
//                responseLiveData.value = photoItems
//                Log.d("mane", photoItems.toString())
//            }
//
//
//            override fun onFailure(call: Call<MainResponse>, t: Throwable)
//            {
//                Log.e("mane", "Failed to fetch photos", t)
//            }
//
//        })
//
//        return responseLiveData
//    }

    lateinit var bookList: MutableLiveData<MainResponse>
    init {
        bookList = MutableLiveData()
    }

    fun getBookListObserver(): MutableLiveData<MainResponse> {
        return bookList
    }

    // make api call
    fun searchFlickrApi(query: String) {

        flickrApi.getPhotos(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getPhotoListObserverRx())
    }

    private fun getPhotoListObserverRx():Observer<MainResponse> {
        return object : Observer<MainResponse> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onNext(t: MainResponse) {
                bookList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}


