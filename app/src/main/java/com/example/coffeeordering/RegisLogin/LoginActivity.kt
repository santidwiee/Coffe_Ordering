package com.example.coffeeordering.RegisLogin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffeeordering.Admin.MainAdminActivity
import com.example.coffeeordering.Customer.MainActivity
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    /**
     * linknya : http://www.appsdeveloperblog.com/firebase-authentication-example-kotlin/
     */

    private val TAG = "LoginActivity"

    //Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //mengaktifkan gradient_list
        val animationDrawable2 = login.getBackground() as AnimationDrawable
        animationDrawable2.setEnterFadeDuration(3000)
        animationDrawable2.setExitFadeDuration(3000)
        animationDrawable2.start()

        mAuth = FirebaseAuth.getInstance()

        //perintah button
        Login.setOnClickListener { loginUser() }

        Regis.setOnClickListener { startActivity(Intent(this, RegisActivity::class.java)) }

        ForgetPassword.setOnClickListener { startActivity(Intent(this, ForgetPasswordActivity::class.java)) }
    }

    private fun loginUser(){
        val email = email.text.toString()
        val password = password.text.toString()

        if(email.isEmpty() && password.isEmpty()){

            Toast.makeText(this, "Enter All Your Account Details", Toast.LENGTH_SHORT).show()
        }else{

            val processDialog = ProgressDialog(this)
            processDialog.isIndeterminate = true
            processDialog.setMessage(" Logining User...")
            processDialog.show()

            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    processDialog.hide()

                    if(task.isSuccessful){
                        //sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        if(email == "zpanzaztwizz@gmail.com"){
                            updateUIAdmin()
                        }else{
                            updateUI()
                        }

                    }else{
                        //if sign in fails, display a message to the user
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updateUI(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateUIAdmin(){
        val intent = Intent(this, MainAdminActivity::class.java)
        startActivity(intent)
    }
}
