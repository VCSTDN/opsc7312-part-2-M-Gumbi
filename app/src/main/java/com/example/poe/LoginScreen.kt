package com.example.poe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginScreen : AppCompatActivity() {

    // Variables to store user input
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var createAccountButton: Button
    private lateinit var FacebookButton: ImageButton
    private lateinit var GoogleButton: ImageButton
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginscreen)

        // Initialize Firebase Auth
        auth = Firebase.auth
        databaseReference = FirebaseDatabase.getInstance().reference

        // Getting user input
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        createAccountButton = findViewById(R.id.createAccountButton)
//        FacebookButton = findViewById(R.id.FacebookButton)
//        GoogleButton = findViewById(R.id.GoogleButton)

        // Action taken when login button is clicked
        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Email and password are required.", Toast.LENGTH_SHORT).show()
            }
        }

        // Action taken when create account button is clicked
        createAccountButton.setOnClickListener {
            // Direct to the RegScreen activity
            val intent = Intent(this, RegScreen::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    if (user != null) {
                        // Retrieve additional user information from Firebase Realtime Database
                        val userId = user.uid
                        databaseReference.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    val userFirstName = dataSnapshot.child("firstName").value.toString()
                                    val userLastName = dataSnapshot.child("lastName").value.toString()
                                    // Do something with user's first name and last name
                                    Toast.makeText(baseContext, "Welcome, $userFirstName $userLastName!", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle any errors
                                Toast.makeText(baseContext, "Error fetching user data.", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }

                    // Create an Intent to open the RegScreen activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign-in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
