package com.example.deloitte_flickr_search.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deloitte_flickr_search.R
import com.example.deloitte_flickr_search.data.PhotoItem

class FlickrAdapter : RecyclerView.Adapter<FlickrAdapter.MyViewHolder>() {

    var photoList = ArrayList<PhotoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrAdapter.MyViewHolder {

        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.photo_view, parent, false )

        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: FlickrAdapter.MyViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    class   MyViewHolder(photoview: View): RecyclerView.ViewHolder(photoview) {

        val thumbImageView: ImageView = photoview.findViewById(R.id.glide_view)

        fun bind(data : PhotoItem)
        {
            val url  = "http://farm${data.farm}.static.flickr.com/${data.server}/${data.id}_${data.secret}.jpg"
            Glide.with(thumbImageView)
                .load(url)
                .placeholder(R.drawable.spinner)
                .into(thumbImageView)
        }
    }
}