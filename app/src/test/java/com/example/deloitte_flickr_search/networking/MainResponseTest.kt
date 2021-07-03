package com.example.deloitte_flickr_search.networking

import org.junit.Assert.*
import org.junit.Test

class MainResponseTest{

    @Test
    fun mockRetrofitTest() {
        // This test is to ensure that Retrofit can successfully intercept API requests and return local Mock data
        val retrofit = MockRetrofit()
        val service = retrofit.create(DummyService::class.java)
        retrofit.path = TestData.USER_DATA //Set the Path, after setting, retrofit will intercept the API and return the data of the Json file under the corresponding Path

        service.getUser("test")
            .test()
            .assertValue { it ->
                it.photos.photo.toTypedArray()[0].id.equals("51185195873")
            }
    }
}