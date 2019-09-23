package br.ufrn.eaj.tads.gametetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_main.*

class GameOver : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)


        Glide.with(this).load(R.drawable.gameover).into(imageView)

        var params = intent.extras
        var score= params?.getInt("Score")

        scoreView.text = score.toString()

        yesButton.setOnClickListener {
            var i = Intent(this,Game::class.java)
            startActivity(i)
            finish()
        }

        noButton.setOnClickListener {
            var i = Intent(this,Home::class.java)
            startActivity(i)
            finish()
        }
    }
}
