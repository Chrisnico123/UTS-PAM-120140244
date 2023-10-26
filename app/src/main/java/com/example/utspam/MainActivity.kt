package com.example.utspam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog

    private var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null ) {
            startActivity(Intent(this   ,HomeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.btn_login)
        val regButton = findViewById<Button>(R.id.btn_register)
        val emailValue = findViewById<EditText>(R.id.input_email)
        val passwordValue = findViewById<EditText>(R.id.input_password)

        regButton.setOnClickListener {
            val changePage = Intent(this, RegisterActivity::class.java)
            startActivity(changePage)
            finish()
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu")

        loginButton.setOnClickListener {
            if(emailValue.text.isNotEmpty() && passwordValue.text.isNotEmpty()) {
                processLogin()
            } else {
                Toast.makeText(this , "Silahkan Isi Semua Field" , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processLogin() {
        val emailValue = findViewById<EditText>(R.id.input_email)
        val passwordValue = findViewById<EditText>(R.id.input_password)
        val email = emailValue.text.toString()
        val password = passwordValue.text.toString()


        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email , password)
            .addOnSuccessListener {
                startActivity(Intent(this , HomeActivity::class.java))
            }
            .addOnFailureListener{ error2 ->
                Toast.makeText(this , error2.localizedMessage , Toast.LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                progressDialog.dismiss()
            }
    }
}
