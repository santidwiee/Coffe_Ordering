package com.example.coffeeordering.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.coffeeordering.R
import com.example.coffeeordering.RegisLogin.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_admin.*
import kotlinx.android.synthetic.main.activity_main_customer.*
import kotlinx.android.synthetic.main.activity_menu_customer.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    /**
     * link : https://www.codepolitan.com/belajar-membuat-aplikasi-android-dengan-kotlin-untuk-pemula-bagian-5-crud-dengan-firebase-real-time-database-part-3-update-dan-delete-data
     * link2 : https://www.codepolitan.com/belajar-membuat-aplikasi-android-dengan-kotlin-untuk-pemula-bagian-4-crud-dengan-firebase-real-time-database-part-1-insert-data
     */
    private var imageList = intArrayOf(R.drawable.iklan1, R.drawable.iklan2, R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_customer)

        //flipper
        val viewFlipper = findViewById<ViewFlipper>(R.id.viewFlipper)
        if (viewFlipper != null) {
            viewFlipper.setInAnimation(applicationContext, android.R.anim.slide_in_left)
            viewFlipper.setOutAnimation(applicationContext, android.R.anim.slide_out_right)
        }

        if (viewFlipper != null) {
            for (image in imageList) {
                val imageView = ImageView(this)
                val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(30, 30, 30, 30)
                layoutParams.gravity = Gravity.CENTER
                imageView.layoutParams = layoutParams
                imageView.setImageResource(image)
                viewFlipper.addView(imageView)
            }
        }

        //animation
        imgMenuCustomer.visibility = View.VISIBLE
        imgPesananCustomer.visibility = View.VISIBLE
        imgProfileCustomer.visibility = View.VISIBLE
        imgLogoutCustomer.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this, R.anim.in_from_right)
        imgMenuCustomer.startAnimation(anim)
        imgPesananCustomer.startAnimation(anim)
        imgProfileCustomer.startAnimation(anim)
        imgLogoutCustomer.startAnimation(anim)

        //button command
        menuCustomer.setOnClickListener {
            startActivity(Intent(this, MenuCustomerActivity::class.java))
        }

        pesananCustomer.setOnClickListener {
            startActivity(Intent(this, CartCustomerActivity::class.java))
        }

        profileCustomer.setOnClickListener {

        }

        logoutCustomer.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Logout()
        }
    }

    private fun Logout(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
