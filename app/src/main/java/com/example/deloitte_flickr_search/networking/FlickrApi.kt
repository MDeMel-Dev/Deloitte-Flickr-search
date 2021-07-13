package com.example.deloitte_flickr_search.networking


import com.example.deloitte_flickr_search.BuildConfig
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

    companion object{
        const val KEY = BuildConfig.API_KEY
    }

    // START AND SEARCH API CALLS
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&perpage=101&api_key=$KEY" )
    fun getPhotos(@Query("text") query: String): Observable<MainResponse>

    // PAGINATION API CALL
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&perpage=101&api_key=$KEY" )
    fun getPage(@Query("text") query: String,@Query("page") page: String ): Observable<MainResponse>

    }


