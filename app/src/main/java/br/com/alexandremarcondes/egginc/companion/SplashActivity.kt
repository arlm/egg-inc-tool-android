package br.com.alexandremarcondes.egginc.companion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alexandremarcondes.egginc.companion.screens.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start home activity
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))

        // close splash activity
        finish()
    }
}