package com.example.deloitte_flickr_search.networking

import java.io.File

object TestData {

    //File path of simulated json data corresponding to User API
    val USER_DATA = "/mockflickrdata"

    //Read Json data through the file path
    fun readFile(path: String): String {
        val content = file2String(File(path))
        return content
    }
    //kotlin's rich I/O API, we can get the result directly through file.readText (charset)
    fun file2String(f: File, charset: String = "UTF-8"): String {
        return f.readText(Charsets.UTF_8)
    }
}