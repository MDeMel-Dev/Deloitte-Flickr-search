package com.example.deloitte_flickr_search.networking

import com.example.deloitte_flickr_search.models.PhotoItem

data class MainResponse(val photos: ProcessedResponse)
data class ProcessedResponse(val photo: List<PhotoItem>)
