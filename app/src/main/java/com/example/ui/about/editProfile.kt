package com.example.ui.about

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.userapp.R
import com.example.userapp.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.lang.Exception

class editProfile : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtBranch: EditText
    private lateinit var txtEmail: TextView
    private lateinit var txtYear: EditText
    private lateinit var imageView: ImageView

    private lateinit var submit: Button
    private lateinit var cancel: Button

    private lateinit var progressDialog:ProgressDialog
    private lateinit var authProfile:FirebaseAuth

    private lateinit var Name:String
    private lateinit var Email:String
    private lateinit var Branch:String
    private lateinit var Year:String
    private lateinit var uri:String
    private lateinit var dbref: DatabaseReference
    private var downloadurl : String = ""
    private var imageUri : Uri? = null
    private lateinit var url : String

    private val selectImage =registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it != null) {
            imageUri =it
            imageView.setImageURI(imageUri)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        txtName = findViewById(R.id.tv_name)
        txtBranch = findViewById(R.id.tv_branch)
        txtEmail= findViewById(R.id.tv_email)
        txtYear= findViewById(R.id.tv_year)
        imageView = findViewById(R.id.profile_dp)
        submit = findViewById(R.id.submit_profile)
        cancel = findViewById(R.id.btn_cancel)

        url = intent.getStringExtra("image").toString()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading !")

        //progressDialog.show()
        authProfile = FirebaseAuth.getInstance()
        val firebaseUser = authProfile.currentUser

        if (firebaseUser==null){
            Toast.makeText(this, "Something Went wrong, Unable to fetch data", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }else{
            showUserProfile(firebaseUser)
        }

        imageView.setOnClickListener {

            selectImage.launch("image/*")
        }
        submit.setOnClickListener {
            progressDialog.show()
            uploadImage()
            finish()
        }
        cancel.setOnClickListener {
            finish()
        }
    }

    private fun uploadImage() {
        val storageRef = FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        if (imageUri != null) {
            storageRef.putFile(imageUri!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        downloadurl = it.toString()
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploded image", Toast.LENGTH_SHORT).show()
                        storeData()
                    }.addOnFailureListener {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }
        else{
            downloadurl = url
            storeData()
        }
    }

    private fun storeData() {
        val data = User(
            email = txtEmail.text.toString(),
            name = txtName.text.toString(),
            branch = txtBranch.text.toString(),
            year = txtYear.text.toString(),
            uri = downloadurl
        )
        val uid = authProfile.currentUser?.uid
        if(uid!=null){
            dbref.child(uid).setValue(data).addOnCompleteListener {
                Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
                progressDialog.dismiss()

            }.addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun showUserProfile(firebaseUser: FirebaseUser) {
        val userId= firebaseUser.uid
        dbref= FirebaseDatabase.getInstance().getReference("Users")
        dbref.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(UserData::class.java)
                if(value!=null){
                    Email = firebaseUser.email.toString()
                    Name = value.name
                    Branch = value.branch
                    Year = value.year
                    uri = value.uri
//                    txtWelcome.setText("Welcome, "+Name + " !")

                    txtEmail.setText(Email)
                    txtName.setText(Name)
                    txtBranch.setText(Branch)
                    txtYear.setText(Year)

                    try {
                        Glide.with(this@editProfile).load(url).into(imageView)
                    }catch (e:Exception){
                        e.message
                    }

                    val storageRef = FirebaseStorage.getInstance().reference
// Create a reference to the user's profile image
                    val profileImageRef = storageRef.child("profile/$userId")

// Download the image from Firebase Storage
                    /*profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Use the URI to load or display the user's profile image
                        val imageUrl = uri.toString()
                        imageUri=uri

                        // You can use a library like Glide or Picasso to load and display the image
                        // For example, using Glide:
                        Glide.with(this@editProfile).load(imageUrl).into(imageView)
                        progressDialog.dismiss()
                    }.addOnFailureListener { exception ->
                        // Handle any errors that occur during the download
                        // For example, you can show an error message to the user
                        Toast.makeText(this@editProfile, "Unable to load DP", Toast.LENGTH_SHORT).show()
                    }*/
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@editProfile, "Something went wrong from fething Database", Toast.LENGTH_SHORT).show()
            }

        })
    }
}