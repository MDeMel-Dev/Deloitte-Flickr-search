package com.example.deloitte_flickr_search.networking

import okhttp3.*
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response as Response1

class MockRetrofit {

    var path: String = ""

    fun <T> create(clazz: Class<T>): T {

        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val content = TestData.readFile(path)
                val body = ResponseBody.create(MediaType.parse("application/x-www-form-urlencoded"), content)
                val response = Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .body(body)
                    .message("Test Message")
                    .build()
                response
            }).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.***.com")
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }
}