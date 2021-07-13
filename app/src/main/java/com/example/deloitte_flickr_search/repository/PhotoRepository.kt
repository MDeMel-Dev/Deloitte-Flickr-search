package com.example.deloitte_flickr_search.repository

import androidx.lifecycle.MutableLiveData
import com.example.deloitte_flickr_search.models.PhotoItem
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

    //MAIN NETWORK SERVICE OBJECT
    val flickrApi = flickrApi

    sealed class ViewState {
        data class stateLoaded(val list : List<PhotoItem>) : ViewState()
        data class stateLoading(val string : String = "loading") : ViewState()
        data class stateError(val e : Exception) : ViewState()
    }


    //DATA RETAIN ONSTART OR ON SEARCH
    lateinit var photoData: MutableLiveData<ViewState>
    init {
        photoData = MutableLiveData()
    }

    fun getPhotoListObserver(): MutableLiveData<ViewState> {
        return photoData
    }

    //DATA RETAIN ON PAGINATION
    lateinit var paginateData: MutableLiveData<ViewState>
    init {
        paginateData = MutableLiveData()
    }

    fun paginateObserver(): MutableLiveData<ViewState> {
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
                photoData.postValue(ViewState.stateError(e as Exception))
            }

            override fun onNext(t: MainResponse) {
                photoData.postValue(ViewState.stateLoaded(t.photos.photo))
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
                paginateData.postValue(ViewState.stateError(e as Exception))
            }

            override fun onNext(t: MainResponse) {
                paginateData.postValue(ViewState.stateLoaded(t.photos.photo))
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}


