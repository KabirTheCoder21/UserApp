package com.example.userapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EbookActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbref : DatabaseReference
    private lateinit var list: MutableList<EbookData>
    private lateinit var adapter: EbookAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebook)
        recyclerView = findViewById(R.id.rv_ebook)
        dbref = FirebaseDatabase.getInstance().getReference().child("pdf")
        getData()
    }

    private fun getData() {
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list = mutableListOf()
                for(it in snapshot.children)
                {
                    val data = it.getValue(EbookData::class.java)
                    if (data != null) {
                        list.add(data)
                    }
                }
                adapter = EbookAdapter(this@EbookActivity,list)
                recyclerView.layoutManager = LinearLayoutManager(this@EbookActivity)
                recyclerView.adapter = adapter
                recyclerView.setHasFixedSize(true)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EbookActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}