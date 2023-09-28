package com.example.ui.gallery

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class GalleryAdapter(
    private val context: Context,
    private val image: List<String>) : RecyclerView.Adapter<GalleryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.gallery_image,parent,false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return image.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        try {
            Picasso.get().load(image[position]).into(holder.imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.imageView.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.show_image)
            val cancel:ImageView = dialog.findViewById(R.id.cancel_button)
            val imagenew :ImageView = dialog.findViewById(R.id.image_show)
            val pb : ProgressBar = dialog.findViewById(R.id.progressBar)
            try {
                //   Picasso.get().load(data.image).into(holder.image)

                Picasso.get()
                    .load(image[position])
                    .into(imagenew,object : Callback{
                        override fun onSuccess() {
                            pb.visibility = View.GONE
                        }

                        override fun onError(e: java.lang.Exception?) {
                            if (e != null) {
                                e.printStackTrace()
                            }
                            pb.visibility = View.GONE
                        }

                    })
            } catch (e: Exception) {
                e.printStackTrace()
                // holder.progressBar.visibility = View.GONE
            }
            dialog.show()
            dialog.setCancelable(true)
            cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
}

class GalleryViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
val imageView = itemView.findViewById<ImageView>(R.id.imageGallery)
}
