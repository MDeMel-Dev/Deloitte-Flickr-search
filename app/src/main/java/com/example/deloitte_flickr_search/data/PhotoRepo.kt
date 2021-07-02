package com.example.deloitte_flickr_search.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.deloitte_flickr_search.networking.FlickrApi
import com.example.deloitte_flickr_search.networking.MainResponse
import com.example.deloitte_flickr_search.networking.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class PhotoRepo {

    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }

    fun fetchData(): LiveData<List<PhotoItem>> {

        val responseLiveData: MutableLiveData<List<PhotoItem>> = MutableLiveData()
        val flickrRequest: Call<MainResponse> = flickrApi.fetchData()

        flickrRequest.enqueue(object : Callback<MainResponse> {

//            override fun onResponse( call: Call<String>,
//                                     response: Response<String>
//            )
//            {
//
//                response.body()?.let { Log.d("mane", it) }
//                 }
//
//
//            override fun onFailure(call: Call<String>, t: Throwable)
//            {
//                Log.e("mane", "Failed to fetch photos", t)
//            }
//
//        })

            override fun onResponse( call: Call<MainResponse>,
                                     response: Response<MainResponse>
            )
            {

                Log.d("mane", "Response received")
                // ADD A PROCESS JSON FUNC HERE
                val mainResponse: MainResponse? = response.body()
                val processedResponse = mainResponse?.photos
                val photoItems: List<PhotoItem> = processedResponse?.photoItems ?: mutableListOf()
                responseLiveData.value = photoItems
                Log.d("mane", photoItems.toString())
            }


            override fun onFailure(call: Call<MainResponse>, t: Throwable)
            {
                Log.e("mane", "Failed to fetch photos", t)
            }

        })

        return responseLiveData
    }

}
