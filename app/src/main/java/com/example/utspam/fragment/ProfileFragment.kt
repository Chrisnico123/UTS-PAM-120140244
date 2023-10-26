package com.example.utspam.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.utspam.MainActivity
import com.example.utspam.R
import com.example.utspam.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {

    private var firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val logout_button = view.findViewById<Button>(R.id.logout_button)
        val text_user = view.findViewById<TextView>(R.id.text_user)
        val text_git = view.findViewById<TextView>(R.id.text_git)
        val text_nim = view.findViewById<TextView>(R.id.text_nim)
        val text_email = view.findViewById<TextView>(R.id.text_email)

        val currentUserUid = firebaseAuth.currentUser?.uid

        databaseReference.child(currentUserUid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    text_user.text = user?.name
                    text_git.text = user?.gitUsername
                    text_nim.text = user?.nim
                    text_email.text = user?.email
                } else {
                    // Tambahkan log atau pesan toast untuk mendeteksi jika data tidak ada.
                    Log.d("ProfileFragment", "Data tidak ditemukan di Firebase.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("ProfileFragment", "Error: ${error.message}")
            }
        })

        logout_button.setOnClickListener {
            performLogout()
        }

        return view
    }

    private fun performLogout() {
        firebaseAuth.signOut()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}