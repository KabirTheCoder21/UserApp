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
                    imageList.reverse()
                    requireActivity().runOnUiThread {
                        galleryAdapter = GalleryAdapter(requireContext(), imageList)
                        otherRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
                        otherRecyclerView.adapter = galleryAdapter
                        otherRecyclerView.setHasFixedSize(true)
                        galleryAdapter.notifyDataSetChanged()
                        otherRecyclerView.addOnScrollListener(object :
                            RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                otherRecyclerView.isNestedScrollingEnabled = true
                                // This method is called whenever the RecyclerView is scrolled.
                                // You can use 'dy' to determine the direction of scrolling (positive or negative).
                                // 'dy' > 0 means scrolling up, 'dy' < 0 means scrolling down.
                                // Implement your scrolling logic here.
                            }

                            override fun onScrollStateChanged(
                                recyclerView: RecyclerView,
                                newState: Int
                            ) {
                                super.onScrollStateChanged(recyclerView, newState)

                                // This method is called when the scroll state changes (e.g., idle, dragging, settling).
                                // Implement your logic based on the new state if needed.
                            }
                        })
                    }
                   // ViewCompat.setNestedScrollingEnabled(otherRecyclerView, false);
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
                    imageList.reverse()
                    requireActivity().runOnUiThread {
                        galleryAdapter = GalleryAdapter(requireContext(), imageList)
                        indRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
                        indRecyclerView.adapter = galleryAdapter
                        indRecyclerView.hasFixedSize()
                        galleryAdapter.notifyDataSetChanged()
                        indRecyclerView.addOnScrollListener(object :
                            RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                indRecyclerView.isNestedScrollingEnabled = true
                                // This method is called whenever the RecyclerView is scrolled.
                                // You can use 'dy' to determine the direction of scrolling (positive or negative).
                                // 'dy' > 0 means scrolling up, 'dy' < 0 means scrolling down.
                                // Implement your scrolling logic here.
                            }

                            override fun onScrollStateChanged(
                                recyclerView: RecyclerView,
                                newState: Int
                            ) {
                                super.onScrollStateChanged(recyclerView, newState)

                                // This method is called when the scroll state changes (e.g., idle, dragging, settling).
                                // Implement your logic based on the new state if needed.
                            }
                        })
                    }
                    // ViewCompat.setNestedScrollingEnabled(indRecyclerView, false);
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
                    imageList.reverse()
                    requireActivity().runOnUiThread{
                        galleryAdapter = GalleryAdapter(requireContext(), imageList)
                        convoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
                        convoRecyclerView.adapter = galleryAdapter
                        convoRecyclerView.hasFixedSize()
                        galleryAdapter.notifyDataSetChanged()
                        convoRecyclerView.addOnScrollListener(object :
                            RecyclerView.OnScrollListener() {
                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                convoRecyclerView.isNestedScrollingEnabled = true
                                // This method is called whenever the RecyclerView is scrolled.
                                // You can use 'dy' to determine the direction of scrolling (positive or negative).
                                // 'dy' > 0 means scrolling up, 'dy' < 0 means scrolling down.
                                // Implement your scrolling logic here.
                            }

                            override fun onScrollStateChanged(
                                recyclerView: RecyclerView,
                                newState: Int
                            ) {
                                super.onScrollStateChanged(recyclerView, newState)

                                // This method is called when the scroll state changes (e.g., idle, dragging, settling).
                                // Implement your logic based on the new state if needed.
                            }
                        })
                    }
                 //   ViewCompat.setNestedScrollingEnabled(convoRecyclerView, false);
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