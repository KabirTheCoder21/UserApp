package com.example.userapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mahfa.dnswitch.DayNightSwitch
import com.mahfa.dnswitch.DayNightSwitchListener

class SignIn : AppCompatActivity() {

    private lateinit var daySky: View
    private lateinit var nightSky: View
    private lateinit var dayNightSwitch:DayNightSwitch
    private lateinit var button:Button
    private lateinit var login:TextView

    private lateinit var name:EditText
    private lateinit var branch:EditText
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var photo:ImageView
    private lateinit var year:Spinner
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var dbref:DatabaseReference
    private lateinit var progressDialog: ProgressDialog
    private lateinit var branchh : String
    private lateinit var yearr : String
    private var downloadurl : String = ""
    private  var imageUri : Uri? = null

    private val selectImage =registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it != null) {
            imageUri =it
            photo.setImageURI(imageUri)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        daySky = findViewById(R.id.day_bg)
        nightSky = findViewById(R.id.night_bg)
        dayNightSwitch = findViewById(R.id.day_night_swithch)
        button = findViewById(R.id.signin)
        login = findViewById(R.id.logini)

        name = findViewById(R.id.name_et)
        email = findViewById(R.id.email_et)
        password = findViewById(R.id.pass_et)
        branch = findViewById(R.id.branchi)
        year = findViewById(R.id.year)
        photo = findViewById(R.id.pic)
        firebaseAuth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference("Users")

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading !")

        dayNightSwitch.setListener(object : DayNightSwitchListener {
            override fun onSwitch(isNight: Boolean) {
                if (isNight) {
//                    dayLand.animate().alpha(0f).setDuration(1300)
                    daySky.animate().alpha(0f).setDuration(1300)
                } else {
//                    dayLand.animate().alpha(1f).setDuration(1300)
                    daySky.animate().alpha(1f).setDuration(1300)
                }
            }
        })

       photo.setOnClickListener{
//           override fun onClick(v: View?) {
//               ImagePicker.with(this@SignIn)
//                   .crop()
//                   .cropOval()
//                   .compress(1024)
//                   .maxResultSize(1080,1080)
//                   .start()
//           }
           selectImage.launch("image/*")


       }
        login.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        button.setOnClickListener {
            val mail = email.text.toString()
            val pass = password.text.toString()
             branchh = branch.text.toString()
             yearr = year.selectedItem.toString()
            val namee = name.text.toString()
            val urii=imageUri.toString()
            Log.d("image", "onCreate: ${urii}")
//            val pic = pic.


            if (mail.isNotEmpty() && pass.isNotEmpty() && branchh!="Select Year" && yearr.isNotEmpty()&&namee.isNotEmpty() && urii!="null" ) {

                    // Check if the email is already in use

                progressDialog.setMessage("Loading !")
                progressDialog.show()
                    firebaseAuth.fetchSignInMethodsForEmail(mail)
                        .addOnCompleteListener { checkTask ->
                            if (checkTask.isSuccessful) {
                                val result = checkTask.result
                                if (result?.signInMethods?.isEmpty() == true) {
                                    // No user with this email address exists, you can proceed with user registration
                                    firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                                        .addOnCompleteListener { registrationTask ->
                                            if (registrationTask.isSuccessful) {
                                                // Registration was successful
                                                uploadImage()
                                                val intent = Intent(this, MainActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                                progressDialog.dismiss()
                                                email.text?.clear()
                                                password.text?.clear()
                                                branch.text?.clear()

                                            } else {
                                                // Registration failed
                                                progressDialog.dismiss()
                                                Toast.makeText(this, "Registration failed: " + registrationTask.exception?.message, Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                } else {
                                    progressDialog.dismiss()
                                    // A user with this email address already exists, display an error message to the user
                                    Toast.makeText(this, "A user with this email already exists", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                progressDialog.dismiss()
                                // An error occurred while checking the email
                                val exception = checkTask.exception
                                Toast.makeText(this, "Error: " + exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
            }
            else{
                Toast.makeText(this,"Enter all Credentials",Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadImage() {
        val storageRef=FirebaseStorage.getInstance().getReference("profile")
            .child(FirebaseAuth.getInstance().currentUser!!.uid).child("profile.jpg")

                storageRef.putFile(imageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener {
                    downloadurl = it.toString()
                    Toast.makeText(this, "Uploded image", Toast.LENGTH_SHORT).show()
                    storeData(it)
                }.addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
    }

    private fun storeData(imageUrl: Uri?) {
            val data =User(
                name = name.text.toString(),
                branch = branchh,
                year = yearr,
                uri = downloadurl
            )
        val uid = firebaseAuth.currentUser?.uid
        if(uid!=null){
            dbref.child(uid).setValue(data).addOnCompleteListener {
                Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener{
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
