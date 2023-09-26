package com.example.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.userapp.R

class GridAdapter(var context : Context,var itemName : Array<String>,var image : IntArray) : BaseAdapter() {
var inflater : LayoutInflater?=null
    override fun getCount(): Int {
        return itemName.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
      return 0
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        if(inflater==null)
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
          if(convertView==null)
              convertView = inflater!!.inflate(R.layout.custom_grid,null)
        val imageView = convertView?.findViewById<ImageView>(R.id.grid_image)
        val textView  = convertView?.findViewById<TextView>(R.id.item_name)
        if (imageView != null) {
            imageView.setImageResource(image[pos])
        }
        if (textView != null) {
            textView.text = itemName[pos]
        }
        return convertView
    }
}