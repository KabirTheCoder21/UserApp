package com.example.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.userapp.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(imageUrl: ArrayList<String>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    // on below line we are creating a
    // new array list and initializing it.
    var sliderList: ArrayList<String> = imageUrl

    class SliderViewHolder(itemView: View?) : ViewHolder(itemView) {
        // on below line we are creating a variable for our
        // image view and initializing it with image id.
        val imageView: ImageView = itemView!!.findViewById(R.id.myimage)
    }

    override fun getCount(): Int {
        // in this method we are returning
        // the size of our slider list.
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        // inside this method we are inflating our layout file for our slider view.
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)

        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        // on below line we are checking if the view holder is null or not.
        if (viewHolder != null) {
            // if view holder is not null we are simply
            // loading the image inside our image view using glide library
            try {
                Picasso.get().load(sliderList[position])
                    .into(viewHolder.imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}