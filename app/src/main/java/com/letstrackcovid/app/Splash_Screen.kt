package com.letstrackcovid.app

import android.content.Intent
import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

@Suppress("DEPRECATION")
class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash__screen)

        val img = findViewById(R.id.splash_img) as ImageView
        val animation : Animation = AnimationUtils.loadAnimation(applicationContext,R.anim.splash_screen_animation)
        img.startAnimation(animation)

        window.setFlags(

                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({

            val intent = Intent(this,Sign_in_page::class.java)
            startActivity(intent)
            finish()

        },3000)


    }
}