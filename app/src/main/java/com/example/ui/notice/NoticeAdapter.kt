package com.example.ui.notice

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NoticeAdapter(
    private val list: List<NoticeData>,
    private val context: Context,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<NoticeAdapter.NoteiceViewHolder>(){
  private lateinit var dialog: Dialog
    class NoteiceViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.notice_title)
        val image = itemView.findViewById<ImageView>(R.id.notice_iv)
        val date = itemView.findViewById<TextView>(R.id.date)
        val time = itemView.findViewById<TextView>(R.id.time)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.pb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_feed_item,parent,false)
        return NoteiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: NoteiceViewHolder, position: Int) {
        val data : NoticeData = list[position]
        holder.title.text = data.title
        holder.date.text = data.date
        holder.time.text = data.time
        try {
            //   Picasso.get().load(data.image).into(holder.image)

            Picasso.get()
                .load(data.image)
                .into(holder.image, object : Callback {
                    override fun onSuccess() {
                        // Image loaded successfully, hide the progress dialog
                        holder.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        // Handle error (e.g., show an error message)
                        if (e != null) {
                            e.printStackTrace()
                        }
                       holder.progressBar.visibility = View.GONE
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            holder.progressBar.visibility = View.GONE
        }
        holder.image.setOnClickListener {

            showCustomLayout(holder.itemView.context,holder,data)
        }
    }

    private fun showCustomLayout(context: Context, holder: NoteiceViewHolder, data: NoticeData) {
         dialog = Dialog(context)
        dialog.setContentView(R.layout.show_image) // Replace with your layout XML file
        val cancel:ImageView = dialog.findViewById(R.id.cancel_button)
        val image :ImageView = dialog.findViewById(R.id.image_show)
        try {
            //   Picasso.get().load(data.image).into(holder.image)

            Picasso.get()
                .load(data.image)
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

