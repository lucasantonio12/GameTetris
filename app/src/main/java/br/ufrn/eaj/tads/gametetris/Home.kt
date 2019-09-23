package br.ufrn.eaj.tads.gametetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        newGameButton.setOnClickListener {
            var i = Intent(this,Game::class.java)
            startActivity(i)
        }

        settingsButton.setOnClickListener {
            var i = Intent(this,Settings::class.java)
            startActivity(i)
        }
        continueButton.setOnClickListener {
           
        }

        Glide.with(this).load(R.drawable.telainicial).into(imageViewHome)
    }
}
