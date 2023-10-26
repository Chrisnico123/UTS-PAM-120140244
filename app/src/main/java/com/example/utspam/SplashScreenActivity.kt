package com.example.utspam

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val icon = findViewById<ImageView>(R.id.splashIcon)
        icon.alpha = 0f
        icon.animate().setDuration(3000).alpha(1f).withEndAction {
            val i = Intent(this , MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(i , options.toBundle())
            finish()
        }
    }
}