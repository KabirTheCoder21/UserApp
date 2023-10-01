package com.example.userapp


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
class EbookAdapter(private val context : Context, private val list : MutableList<EbookData>) : RecyclerView.Adapter<EbookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ebook_item,parent,false)
        return EbookViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {
        val data = list[position]
       holder.titl.text = data.pdfTitle
        holder.itemview.setOnClickListener {
            Toast.makeText(context, data.pdfTitle, Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onBindViewHolder:${data.pdfTitle}")
        }
        holder.open.setOnClickListener {
            Toast.makeText(context, "Open", Toast.LENGTH_SHORT).show()
            val intent = Intent(context,PdfShow::class.java)
           intent.putExtra("title",data.pdfTitle)
            intent.putExtra("url",data.pdfUrl)
            context.startActivity(intent)
        }
    }
}

class EbookViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
    val titl : TextView = itemView.findViewById(R.id.pdfName)
    val open : ImageView = itemView.findViewById(R.id.open)
    val itemview : LinearLayout = itemView.findViewById(R.id.itemview)
}
