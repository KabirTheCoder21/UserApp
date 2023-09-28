package com.example.ui.faculty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.userapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FacultyFragment : Fragment() {
    private lateinit var csDepartment: RecyclerView
    private lateinit var itDepartment: RecyclerView
    private lateinit var meDepartment: RecyclerView
    private lateinit var eeDepartment: RecyclerView
    private lateinit var eceDepartment: RecyclerView

    private lateinit var listCs: ArrayList<FacultyData>
    private lateinit var listIt: ArrayList<FacultyData>
    private lateinit var listMe: ArrayList<FacultyData>
    private lateinit var listEe: ArrayList<FacultyData>
    private lateinit var listEce: ArrayList<FacultyData>

    private lateinit var reference: DatabaseReference
    private lateinit var dbref: DatabaseReference

    private lateinit var adapter: FacultyAdapter

    private var isexpandedCs = false
    private var isexpandedIt = false
    private var isexpandedMe = false
    private var isexpandedEe = false
    private var isexpandedEce = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_faculty, container, false)
        csDepartment = view.findViewById(R.id.csDepartment)
        itDepartment = view.findViewById(R.id.itDepartment)
        meDepartment = view.findViewById(R.id.meDepartment)
        eeDepartment = view.findViewById(R.id.eeDepartment)
        eceDepartment = view.findViewById(R.id.eceDepartment)
        reference = FirebaseDatabase.getInstance().getReference().child("Faculty")

        val csTv = view.findViewById<TextView>(R.id.csTv)

        csTv.setOnClickListener {
            if (isexpandedCs) {
                csDepartment.visibility = View.GONE
            } else {
                //animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                csDepartmentData()
            }
            isexpandedCs = !isexpandedCs // Toggle the flag
        }
        val itTv = view.findViewById<TextView>(R.id.itTv)

        itTv.setOnClickListener {
            if (isexpandedIt) {
                // Hide the RecyclerView
                itDepartment.visibility = View.GONE
            } else {
                //animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                itDepartmentData()
            }
            isexpandedIt = !isexpandedIt // Toggle the flag
        }
        val eeTv = view.findViewById<TextView>(R.id.eeTv)

        eeTv.setOnClickListener {
            if (isexpandedEe) {
                // Hide the RecyclerView
                eeDepartment.visibility = View.GONE
            } else {
                //animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                eeDepartmentData()
            }
            isexpandedEe = !isexpandedEe // Toggle the flag
        }
        val eceTv = view.findViewById<TextView>(R.id.eceTv)

        eceTv.setOnClickListener {
            if (isexpandedEce) {
                // Hide the RecyclerView
                eceDepartment.visibility = View.GONE
            } else {
                //animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                eceDepartmentData()
            }
            isexpandedEce = !isexpandedEce // Toggle the flag
        }
        val meTv = view.findViewById<TextView>(R.id.meTv)

        meTv.setOnClickListener {
            if (isexpandedMe) {
                // Hide the RecyclerView
                meDepartment.visibility = View.GONE
            } else {
                //animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
                meDepartmentData()
            }
            isexpandedMe = !isexpandedMe // Toggle the flag
        }
        return view
    }

    private fun meDepartmentData() {
        dbref = reference.child("Mechanical Engineering")
        listMe = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = dbref.get().await() // Fetch data in the background thread

                withContext(Dispatchers.Main) {
                    if (!snapshot.exists()) {
                        Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                        meDepartment.visibility = View.GONE
                    } else {
                        meDepartment.visibility = View.VISIBLE

                        for (it in snapshot.children) {
                            val facultyData: FacultyData? = it.getValue(FacultyData::class.java)
                            if (facultyData != null) {
                                listMe.add(facultyData)
                            }
                        }

                        meDepartment.hasFixedSize()
                        meDepartment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = FacultyAdapter(listMe, requireContext())
                        meDepartment.adapter = adapter
                        adapter.notifyDataSetChanged()
                        // meDepartment.startAnimation(animation)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    // Handle the error here, if needed
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun eceDepartmentData() {
        dbref = reference.child("Electronics Engineering")
        listEce = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = dbref.get().await() // Fetch data in the background thread

                withContext(Dispatchers.Main) {
                    if (!snapshot.exists()) {
                        eceDepartment.visibility = View.GONE
                        Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
                    } else {
                        eceDepartment.visibility = View.VISIBLE

                        for (it in snapshot.children) {
                            val facultyData: FacultyData? = it.getValue(FacultyData::class.java)
                            if (facultyData != null) {
                                listEce.add(facultyData)
                            }
                        }

                        eceDepartment.hasFixedSize()
                        eceDepartment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = FacultyAdapter(listEce, requireContext())
                        eceDepartment.adapter = adapter
                        adapter.notifyDataSetChanged()
                        // eceDepartment.startAnimation(animation)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    // Handle the error here, if needed
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun eeDepartmentData() {
        dbref = reference.child("Electrical Engineering")
        listEe = ArrayList()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = dbref.get().await() // Fetch data in the background thread

                withContext(Dispatchers.Main) {
                    if (!snapshot.exists()) {
                        eeDepartment.visibility = View.GONE
                        Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                    } else {
                        eeDepartment.visibility = View.VISIBLE

                        for (it in snapshot.children) {
                            val facultyData: FacultyData? = it.getValue(FacultyData::class.java)
                            if (facultyData != null) {
                                listEe.add(facultyData)
                            }
                        }

                        eeDepartment.hasFixedSize()
                        eeDepartment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = FacultyAdapter(listEe, requireContext())
                        eeDepartment.adapter = adapter
                        adapter.notifyDataSetChanged()
                        //   eeDepartment.startAnimation(animation)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    // Handle the error here, if needed
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun itDepartmentData() {
        dbref = reference.child("Information Technology")
        listIt = ArrayList()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = dbref.get().await() // Fetch data in the background thread

                withContext(Dispatchers.Main) {
                    if (!snapshot.exists()) {
                        itDepartment.visibility = View.GONE
                        Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                    } else {
                        itDepartment.visibility = View.VISIBLE

                        for (it in snapshot.children) {
                            val facultyData: FacultyData? = it.getValue(FacultyData::class.java)
                            if (facultyData != null) {
                                listIt.add(facultyData)
                            }
                        }

                        itDepartment.hasFixedSize()
                        itDepartment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = FacultyAdapter(listIt, requireContext())
                        itDepartment.adapter = adapter
                        adapter.notifyDataSetChanged()
                        // itDepartment.startAnimation(animation)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun csDepartmentData() {
        dbref = reference.child("Computer Science")
        listCs = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = dbref.get().await() // Fetch data in the background thread
                withContext(Dispatchers.Main) {
                    if (!snapshot.exists()) {
                        csDepartment.visibility = View.GONE
                        Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                    } else {
                        csDepartment.visibility = View.VISIBLE
                        for (it in snapshot.children) {
                            val facultyData: FacultyData? = it.getValue(FacultyData::class.java)
                            if (facultyData != null) {
                                listCs.add(facultyData)
                            }
                        }

                        csDepartment.hasFixedSize()
                        csDepartment.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = FacultyAdapter(listCs, requireContext())
                        csDepartment.adapter = adapter
                        adapter.notifyDataSetChanged()
                        // csDepartment.startAnimation(animation)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    // Handle the error here, if needed
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}