package com.example.jobify

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_page)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val signUpRealButton: Button = findViewById(R.id.button_signup)

        signUpRealButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainJobActivity::class.java)
            startActivity(intent)
        }
    }
}