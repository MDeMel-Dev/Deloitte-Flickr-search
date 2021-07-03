package com.example.deloitte_flickr_search.repository

import androidx.lifecycle.MutableLiveData
import com.example.deloitte_flickr_search.networking.FlickrApi
import com.example.deloitte_flickr_search.networking.MainResponse
import com.example.deloitte_flickr_search.networking.util.Constants.Companion.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class PhotoRepository(flickrApi: FlickrApi) {

    val flickrApi = flickrApi
    //ONSTART OR ON SEARCH
    lateinit var photoData: MutableLiveData<MainResponse>
    init {
        photoData = MutableLiveData()
    }

    fun getPhotoListObserver(): MutableLiveData<MainResponse> {
        return photoData
    }

    //ON PAGINATION
    lateinit var paginateData: MutableLiveData<MainResponse>
    init {
        paginateData = MutableLiveData()
    }

    fun paginateObserver(): MutableLiveData<MainResponse> {
        return paginateData
    }



    // MAKE API CALL ON START OR ON SEARCH
    fun searchFlickrApi(query: String) {

        flickrApi.getPhotos(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getPhotoListObserverRx())
    }

    // OBSERVE API CALL
    private fun getPhotoListObserverRx():Observer<MainResponse> {
        return object : Observer<MainResponse> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                photoData.postValue(null)
            }

            override fun onNext(t: MainResponse) {
                photoData.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }

    // MAKE API CALL ON PAGINATION
    fun paginateFlickrApi(query: String , page: String) {

        flickrApi.getPage(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(paginateObserverRx())
    }

    //OBSERVE PAGINATION API CALL
    private fun paginateObserverRx():Observer<MainResponse> {
        return object : Observer<MainResponse> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                paginateData.postValue(null)
            }

            override fun onNext(t: MainResponse) {
                paginateData.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}


