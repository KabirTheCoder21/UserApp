package com.example.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.google.firebase.database.*


class GalleryFragment : Fragment() {
    private lateinit var convoRecyclerView: RecyclerView
    private lateinit var indRecyclerView: RecyclerView
    private lateinit var otherRecyclerView: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var dbref : DatabaseReference
    private lateinit var convoDB : DatabaseReference
    private lateinit var indDB : DatabaseReference
    private lateinit var otherDB : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        convoRecyclerView = view.findViewById(R.id.convo_rv)
        indRecyclerView = view.findViewById(R.id.ind_rv)
        otherRecyclerView = view.findViewById(R.id.other_rv)

        dbref = FirebaseDatabase.getInstance().reference.child("gallery")
        convoDB = dbref.child("Convocation")
        indDB = dbref.child("Independence Day")
        otherDB = dbref.child("Other Events")

        Thread{
            getConvoImage()
            getIndImage()
            getOtherImage()
        }.start()
        return view
    }

    private fun getOtherImage() {
        otherDB.addValueEventListener(
            object : ValueEventListener
            {
                var imageList : ArrayList<String> = ArrayList()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (it in snapshot.children)
                    {
                        val data = it.value.toString()
                        imageList.add(data)
                    }
                    galleryAdapter = GalleryAdapter(requireContext(), imageList)
                    otherRecyclerView.layoutManager = GridLayoutManager(requireContext(),4)
                    otherRecyclerView.adapter = galleryAdapter
                    otherRecyclerView.hasFixedSize()
                    ViewCompat.setNestedScrollingEnabled(otherRecyclerView, false);
                }

                override fun onCancelled(error: DatabaseError) {
                    error.message
                }

            }
        )
    }

    private fun getIndImage() {
        indDB.addValueEventListener(
            object : ValueEventListener
            {
                var imageList : ArrayList<String> = ArrayList()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (it in snapshot.children)
                    {
                        val data = it.value.toString()
                        imageList.add(data)
                    }
                    galleryAdapter = GalleryAdapter(requireContext(), imageList)
                    indRecyclerView.layoutManager = GridLayoutManager(requireContext(),4)
                    indRecyclerView.adapter = galleryAdapter
                    indRecyclerView.hasFixedSize()
                    ViewCompat.setNestedScrollingEnabled(indRecyclerView, false);
                }

                override fun onCancelled(error: DatabaseError) {
                    error.message
                }

            }
        )
    }

    private fun getConvoImage() {

        convoDB.addValueEventListener(
            object : ValueEventListener
            {
                var imageList : ArrayList<String> = ArrayList()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (it in snapshot.children)
                    {
                        val data = it.value.toString()
                        imageList.add(data)
                    }
                    galleryAdapter = GalleryAdapter(requireContext(),imageList)
                    convoRecyclerView.layoutManager = GridLayoutManager(requireContext(),4)
                    convoRecyclerView.adapter = galleryAdapter
                    convoRecyclerView.hasFixedSize()
                    ViewCompat.setNestedScrollingEnabled(convoRecyclerView, false);
                }

                override fun onCancelled(error: DatabaseError) {
                    error.message
                }

            }
        )
    }

    override fun onResume() {
        super.onResume()
        getConvoImage()
        getIndImage()
        getOtherImage()
    }
}