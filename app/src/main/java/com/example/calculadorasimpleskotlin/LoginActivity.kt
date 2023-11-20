package com.example.calculadorasimpleskotlin
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerEmailEditText: EditText
    private lateinit var registerPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerEmailEditText = findViewById(R.id.registerEmailEditText)
        registerPasswordEditText=findViewById(R.id.registerPasswordEditText)
        confirmPasswordEditText=findViewById(R.id.confirmPasswordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton=findViewById(R.id.registerButton)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        fun logFirebaseAnalyticsEvent(eventName: String) {
            val bundle = Bundle()
            bundle.putString("event_name", eventName)
            firebaseAnalytics.logEvent("custom_event", bundle)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
                logFirebaseAnalyticsEvent("login_button_click")
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val email = registerEmailEditText.text.toString()
            val password = registerPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
                registerUser(email, password)
                logFirebaseAnalyticsEvent("register_button_click")
            } else {
                Toast.makeText(this, "Preencha todos os campos e verifique a senha", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Login bem-sucedido como ${user?.email}", Toast.LENGTH_SHORT).show()
                    saveUserIdToFirestore(user?.uid)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(this, "Cadastro bem-sucedido para ${user?.email}", Toast.LENGTH_SHORT).show()
                    saveUserIdToFirestore(user?.uid)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Falha no cadastro. Verifique suas credenciais.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserIdToFirestore(userId: String?) {
        if (userId != null) {
            val userDocRef = db.collection("users").document(userId)
            userDocRef.set(mapOf("userId" to userId))
                .addOnSuccessListener {
                    Log.d("LoginActivity", "UserID saved to Firestore")
                }
                .addOnFailureListener {
                    Log.e("LoginActivity", "Failed to save UserID to Firestore", it)
                }
        }
    }

}
