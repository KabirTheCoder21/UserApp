package com.example.ui.about

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.userapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.smarteist.autoimageslider.IndicatorView.animation.data.Value
import kotlin.properties.ReadWriteProperty

class AboutFragment : Fragment() {

    private lateinit var txtWelcome:TextView
    private lateinit var txtName:TextView
    private lateinit var txtBranch:TextView
    private lateinit var txtEmail:TextView
    private lateinit var txtYear:TextView
    private var downloadurl : String = ""
    private lateinit var progressDialog:ProgressDialog

    private lateinit var Name:String
    private lateinit var Email:String
    private lateinit var Branch:String
    private lateinit var Year:String
    private lateinit var uri:String
    private lateinit var dbref:DatabaseReference

    private lateinit var imageUrl : String

    private lateinit var edit:ImageView

    private lateinit var imageView:ImageView
    private lateinit var authProfile:FirebaseAuth
//    private lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)
//        supportActionBar?.title = "New Title"
        txtWelcome = view.findViewById(R.id.tv_welcome)
        txtName = view.findViewById(R.id.tv_name)
        txtBranch = view.findViewById(R.id.tv_branch)
        txtEmail= view.findViewById(R.id.tv_email)
        txtYear= view.findViewById(R.id.tv_year)
        imageView = view.findViewById(R.id.profile_dp)
        edit = view.findViewById(R.id.edit_profile)

//        progressBarDialog= AlertDialog(requireContext())
        progressDialog =ProgressDialog(requireContext())
    progressDialog.setMessage("Loading !")

    progressDialog.show()
        authProfile =FirebaseAuth.getInstance()
        val firebaseUser = authProfile.currentUser

        if (firebaseUser==null){
            Toast.makeText(requireContext(), "Something Went wrong, Unable to fetch data", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }else{
            showUserProfile(firebaseUser)
        }

        edit.setOnClickListener {
            val intent = Intent(requireContext(),editProfile::class.java)
            intent.putExtra("image",imageUrl)
            startActivity(intent)
        }

        return view
    }

    private fun showUserProfile(firebaseUser: FirebaseUser) {
        val userId= firebaseUser.uid
        dbref=FirebaseDatabase.getInstance().getReference("Users")
        dbref.child(userId).addListenerForSingleValueEvent(object :ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(UserData::class.java)
                if(value!=null){
                    Email = value.email
                    Name = value.name
                    Branch = value.branch
                    Year = value.year
                    uri = value.uri
                    txtWelcome.setText("Welcome, "+Name + " !")

                    txtEmail.setText(Email)
                    txtName.setText(Name)
                    txtBranch.setText(Branch)
                    txtYear.setText(Year)

                    val storageRef = FirebaseStorage.getInstance().reference
// Create a reference to the user's profile image
                    val profileImageRef = storageRef.child("profile/$userId")

// Download the image from Firebase Storage
                    profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Use the URI to load or display the user's profile image
                        imageUrl = uri.toString()

                        // You can use a library like Glide or Picasso to load and display the image
                        // For example, using Glide:
                         Glide.with(requireContext()).load(imageUrl).into(imageView)
                        progressDialog.dismiss()
                    }.addOnFailureListener { exception ->
                        // Handle any errors that occur during the download
                        // For example, you can show an error message to the user
                        Toast.makeText(requireContext(), "Unable to load DP", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Something went wrong from fething Database", Toast.LENGTH_SHORT).show()
            }

        })
    }
}