package com.example.coffeeordering.RegisLogin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_regis.*
import kotlinx.android.synthetic.main.activity_regis.Regis
import kotlinx.android.synthetic.main.activity_regis.email
import kotlinx.android.synthetic.main.activity_regis.password

class RegisActivity : AppCompatActivity() {


    private val TAG = "RegisActivity"

    //Firebase references
    lateinit var ref : DatabaseReference
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        //mengaktifkan gradient_list
        val animationDrawable = regis.getBackground() as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3000)
        animationDrawable.setExitFadeDuration(3000)
        animationDrawable.start()


        ref = FirebaseDatabase.getInstance().getReference().child("Users")
        mAuth = FirebaseAuth.getInstance()

        Regis.setOnClickListener { createNewAccount() }
    }

    private fun createNewAccount() {
        val firstname = firstname.text.toString()
        val lastname = lastname.text.toString()
        val email = email.text.toString()
        val password = password.text.toString()

        if (firstname.isEmpty() && lastname.isEmpty() && email.isEmpty() && password.isEmpty()) {

            Toast.makeText(this, "Create this Field with Your Account", Toast.LENGTH_SHORT).show()

        }else{

            val processDialog = ProgressDialog(this)
            processDialog.isIndeterminate = true
            processDialog.setMessage(" Registering user...")
            processDialog.show()

            mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    processDialog.hide()


                    if (task.isSuccessful) {
                        //sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEMail:success")
                        val userId = mAuth!!.currentUser!!.uid

                        //Verify Email
                        verifyEmail()

                        //update user profile information
                        val currentUserDb = ref.child(userId)
                        currentUserDb.child("firstname").setValue(firstname)
                        currentUserDb.child("lastname").setValue(lastname)

                        updateUserInfoAndUI()
                    } else {
                        //if sign in fails, display a message to tthe user
                        Log.e(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication Failed...", Toast.LENGTH_SHORT).show()
                    }

                }

        }
    }

    private fun updateUserInfoAndUI(){
        //start next activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->

                if(task.isSuccessful){
                    Toast.makeText(this, "Verification email sent to " + mUser.email,
                        Toast.LENGTH_SHORT).show()
                }else{
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(this, "Failed to send verification email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
