package com.example.ui.faculty

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.squareup.picasso.Picasso

class FacultyAdapter(
    private val list: List<FacultyData>,
    private val context: Context
) : RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>()  {
    class FacultyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val fName : TextView = itemView.findViewById(R.id.teacherNameTV)
        val fPost : TextView = itemView.findViewById(R.id.teacherPostTV)
        val fEmail : TextView = itemView.findViewById(R.id.teacherEmailTV)
        val fImage : ImageView = itemView.findViewById(R.id.teacherImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.faculty_layout_item,parent,false)
        return FacultyViewHolder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        val items = list[position]
        holder.fName.text = items.name
        holder.fPost.text = items.post
        holder.fEmail.text = items.email
        try {
            Picasso.get().load(items.image)
                .placeholder(R.drawable.faculty)
                .into(holder.fImage)
        }catch (_:Exception)
        {
            Toast.makeText(context, "issue", Toast.LENGTH_SHORT).show()
        }
        holder.fImage.setOnClickListener {
            showCustomLayout(context,holder, items)
        }
    }
    private fun showCustomLayout(context: Context, holder: FacultyViewHolder, items: FacultyData) {
       val dialog = Dialog(context)
        dialog.setContentView(R.layout.show_image) // Replace with your layout XML file
        val cancel:ImageView = dialog.findViewById(R.id.cancel_button)
        val image :ImageView = dialog.findViewById(R.id.image_show)
        try {
            //   Picasso.get().load(data.image).into(holder.image)

            Picasso.get()
                .load(items.image)
                .into(image)
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