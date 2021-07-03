package com.example.deloitte_flickr_search.networking

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyService {

    @GET("/test/api")
    abstract fun getUser(@Query("text") tag: String): Observable<MainResponse>

}