package com.example.jobify

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var firebaseDatabase : FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        val emailEditText = findViewById<EditText>(R.id.email_register)
        val passwordEditText = findViewById<EditText>(R.id.password_register)
        val firstNameEditText = findViewById<TextInputEditText>(R.id.firstname_register)
        val lastNameEditText = findViewById<TextInputEditText>(R.id.lastname_register)
        val dobEditText = findViewById<EditText>(R.id.date_input)
        val countryEditText = findViewById<TextInputEditText>(R.id.country_register)
        val signUpButton = findViewById<Button>(R.id.button_signup)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        saveUserData(user?.uid, firstNameEditText.text.toString(), lastNameEditText.text.toString(), dobEditText.text.toString(), countryEditText.text.toString())
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Registration failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

            val intent = Intent(this@RegisterActivity, MainJobActivity::class.java)
            startActivity(intent)
        }
    }
    private fun saveUserData(userId: String?, firstName: String, lastName: String, dob: String, country: String) {
        userId?.let { uid ->
            val database = firebaseDatabase
            val usersRef = database.getReference("users")
            // Define the data structure for user data
            val userData = hashMapOf(
                "firstName" to firstName,
                "lastName" to lastName,
                "dob" to dob,
                "country" to country
            )

            // Save user data to the database
            usersRef.child(uid).setValue(userData)
                .addOnSuccessListener {
                    // Data successfully saved
                    println("User data saved successfully")
                }
                .addOnFailureListener { exception ->
                    // Failed to save data
                    println("Error saving user data: $exception")
                }
        } ?: run {
            println("User ID is null, cannot save user data")
        }
    }
}