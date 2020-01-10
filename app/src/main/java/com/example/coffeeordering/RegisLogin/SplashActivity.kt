package com.example.coffeeordering.RegisLogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.example.coffeeordering.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //membuat gambar logo fadein
        imgLogo.visibility = View.VISIBLE
        val anim3 = AnimationUtils.loadAnimation(this, R.anim.fadein)
        imgLogo.startAnimation(anim3)

        //membuat splash selama 4 detik
        Handler().postDelayed({
            //ini untuk start activity-nya
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            //untuk mengakhiri activity ini
            finish()
        }, 7000)
    }
}

