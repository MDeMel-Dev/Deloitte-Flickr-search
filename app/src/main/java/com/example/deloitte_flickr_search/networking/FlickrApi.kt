package com.example.deloitte_flickr_search.networking


import com.example.deloitte_flickr_search.data.PhotoItem
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FlickrApi {

        @GET("services/rest/?method=flickr.photos.search" +
        "&api_key=59d9871c92cf2bc4640f3fd9195a3481" + "&format=json" + "&nojsoncallback=1" + "&perpage=101" )
        fun getPhotos(@Query("text") query: String): Observable<MainResponse>


    @GET("services/rest/?method=flickr.photos.search" +
            "&api_key=59d9871c92cf2bc4640f3fd9195a3481" + "&format=json" + "&nojsoncallback=1" + "&perpage=101" )
    fun getPage(@Query("text") query: String,@Query("page") page: String ): Observable<MainResponse>

    }

//+ "&text=mustangs"
