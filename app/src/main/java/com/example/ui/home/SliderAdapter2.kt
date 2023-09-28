package com.example.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.example.userapp.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter2(imageUrl: ArrayList<String>) :
    SliderViewAdapter<SliderAdapter2.SliderViewHolder>() {
    // on below line we are creating a
    // new array list and initializing it.
    var sliderList: ArrayList<String> = imageUrl

    class SliderViewHolder(itemView: View?) : ViewHolder(itemView) {
        // on below line we are creating a variable for our
        // image view and initializing it with image id.
        var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
    }

    override fun getCount(): Int {
        // in this method we are returning
        // the size of our slider list.
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapter2.SliderViewHolder? {
        // inside this method we are inflating our layout file for our slider view.
        val inflate: View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)

        // on below line we are simply passing
        // the view to our slider view holder.
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        val extras = Bundle()
        extras.putString("key_name", "some_value")
        extras.putInt("count", 42)
        if (viewHolder != null) {
            // if view holder is not null we are simply
            // loading the image inside our image view using glide library
            try {
                val item = sliderList[position]

                if (position==0) {
                    viewHolder.itemView.setOnClickListener {
                        // Handle item click here
//                    Toast.makeText(context, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://upcmo.up.nic.in/")
                        intent.putExtras(extras)
                        viewHolder.itemView.context.startActivity(intent)

                    }
                }
                if (position==1) {
                    viewHolder.itemView.setOnClickListener {
                        // Handle item click
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("https://www.ecellfoet.com/")
                        intent.putExtras(extras)
                            viewHolder.itemView.context.startActivity(intent)
                    }
                }
                if (position==2) {
                    viewHolder.itemView.setOnClickListener {
                        // Handle item click here
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("https://slate.lkouniv.ac.in/")
                        intent.putExtras(extras)
                        viewHolder.itemView.context.startActivity(intent)
                    }
                }
                if (position==3) {
                    viewHolder.itemView.setOnClickListener {
                        // Handle item click here
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("https://udrc.lkouniv.ac.in/Department/DepartmentDetail/History?dept=76")
                        intent.putExtras(extras)
                        viewHolder.itemView.context.startActivity(intent)
                    }
                }

                Picasso.get().load(sliderList[position])
                    .into(viewHolder.imageView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}