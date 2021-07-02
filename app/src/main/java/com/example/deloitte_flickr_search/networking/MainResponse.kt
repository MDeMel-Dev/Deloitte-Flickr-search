package com.example.deloitte_flickr_search.networking

import com.example.deloitte_flickr_search.data.PhotoItem

//class MainResponse {
//    lateinit var photos: ProcessedResponse
//}

data class MainResponse(val photos: ProcessedResponse)
data class ProcessedResponse(val photo: List<PhotoItem>)
