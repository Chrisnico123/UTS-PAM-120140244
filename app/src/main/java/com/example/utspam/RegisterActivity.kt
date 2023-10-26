package com.example.utspam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var progressDialog: ProgressDialog

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null ) {
            startActivity(Intent(this   ,HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.btn_register)
        val nameValue: EditText = findViewById(R.id.input_name)
        val emailValue: EditText = findViewById(R.id.input_email)
        val passwordValue: EditText = findViewById(R.id.input_password)

        val backButton = findViewById<ImageView>(R.id.button_back)

        backButton.setOnClickListener {
            val changePage = Intent(this, MainActivity::class.java)
            startActivity(changePage)
            finish()
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu")

        registerButton.setOnClickListener {
            if(nameValue.text.isNotEmpty() && emailValue.text.isNotEmpty() && passwordValue.text.isNotEmpty()) {
                processRegister()
            } else {
                Toast.makeText(this , "Silahkan Isi Semua Field" , LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister() {
        val nameValue: EditText = findViewById(R.id.input_name)
        val emailValue: EditText = findViewById(R.id.input_email)
        val passwordValue: EditText = findViewById(R.id.input_password)
        val githubValue: EditText = findViewById(R.id.input_git)
        val nikValue: EditText = findViewById(R.id.input_nik)
        val name = nameValue.text.toString()
        val email = emailValue.text.toString()
        val pass = passwordValue.text.toString()
        val git = githubValue.text.toString()
        val nik = nikValue.text.toString()


        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email , pass)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val userUpdate = userProfileChangeRequest {
                        displayName = name
                        displayName = git
                        displayName = nik
                    }

                    val user = task.result.user
                    user!!.updateProfile(userUpdate)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this , HomeActivity::class.java))
                        }
                        .addOnFailureListener{ error2 ->
                            Toast.makeText(this , error2.localizedMessage , LENGTH_SHORT).show()
                        }
                } else {
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener{ error ->
                Toast.makeText(this , error.localizedMessage , LENGTH_SHORT).show()
            }
    }
}