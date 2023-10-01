package com.example.ui.notice

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.example.userapp.Utility
import com.google.firebase.database.*

class NoticeFragment : Fragment() {
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var list: ArrayList<NoticeData>
    private lateinit var adapter: NoticeAdapter
    private lateinit var dbref : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notice, container, false)
        recyclerView = view!!.findViewById(R.id.notice_rv)
        progressBar = view.findViewById(R.id.progressBar)
        dbref = FirebaseDatabase.getInstance().getReference().child("Notice")
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.hasFixedSize()
        if(!Utility.isNetworkAvailable(requireContext()))
        {
            Toast.makeText(requireContext(), "Check internet Connection !", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
        getNotice()
        return view
    }

    private fun getNotice() {
        dbref.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list = ArrayList()
                    for (it in snapshot.children) {
                        val data: NoticeData? = it.getValue(NoticeData::class.java)
                        if (data != null) {
                            list.add(data)
                        }
                    }
                    list.reverse()
                    requireActivity().runOnUiThread {
                        adapter = NoticeAdapter(list, requireContext(), recyclerView)
                        progressBar.visibility = View.GONE
                        recyclerView.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            }
        )

    }
}