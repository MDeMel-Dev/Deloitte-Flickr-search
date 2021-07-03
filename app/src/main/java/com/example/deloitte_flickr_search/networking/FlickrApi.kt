package com.example.deloitte_flickr_search.networking


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

    // START AND SEARCH API CALLS
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&perpage=101&api_key=59d9871c92cf2bc4640f3fd9195a3481" )
    fun getPhotos(@Query("text") query: String): Observable<MainResponse>

    // PAGINATION API CALL
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&perpage=101&api_key=59d9871c92cf2bc4640f3fd9195a3481" )
    fun getPage(@Query("text") query: String,@Query("page") page: String ): Observable<MainResponse>

    }


