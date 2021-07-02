package com.example.deloitte_flickr_search.networking

import com.example.deloitte_flickr_search.data.PhotoItem
import com.google.gson.annotations.SerializedName

class ProcessResponse {
    @SerializedName("photo")
    lateinit var photoItems: List<PhotoItem>
}