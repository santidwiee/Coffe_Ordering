package com.example.coffeeordering.RegisLogin

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.email

class ForgetPasswordActivity : AppCompatActivity() {

    private val TAG = "ForgotPasswordActivity"

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        //mengaktifkan gradient_list
        val animationDrawable3 = forgetpassword.getBackground() as AnimationDrawable
        animationDrawable3.setEnterFadeDuration(3000)
        animationDrawable3.setExitFadeDuration(3000)
        animationDrawable3.start()

        mAuth = FirebaseAuth.getInstance()

        reset.setOnClickListener { sendPasswordResetEmail() }
    }

    private fun sendPasswordResetEmail() {
        val email = email.text.toString()

        if (email.isNotEmpty()) {
            mAuth!!.sendPasswordResetEmail(email)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val message = "Email sent..."
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Log.w(TAG, task.exception!!.message)
                        Toast.makeText(this, "No User found with this email", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
