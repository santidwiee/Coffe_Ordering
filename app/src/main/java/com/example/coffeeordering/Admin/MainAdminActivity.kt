package com.example.coffeeordering.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.coffeeordering.RegisLogin.LoginActivity
import com.example.coffeeordering.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_admin.*


class MainAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)


        //animation of Icon
        imgMenu.visibility = View.VISIBLE
        imgPesanan.visibility = View.VISIBLE
        imgLogout.visibility = View.VISIBLE
        val anim = AnimationUtils.loadAnimation(this, R.anim.in_from_right)
        imgMenu.startAnimation(anim)
        imgPesanan.startAnimation(anim)
        imgLogout.startAnimation(anim)

        //button Command
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Logout()
        }

        menu.setOnClickListener { startActivity(Intent(this, MenuAdminActivity::class.java)) }

        pesanan.setOnClickListener { startActivity(Intent(this, ListPesananActivity::class.java)) }
    }


    private fun Logout(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
