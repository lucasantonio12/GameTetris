package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_main.*

class GameOver : AppCompatActivity() {
    val PREFS = "prefs_file"
    var newRecord = false
    var newScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)


        Glide.with(this).load(R.drawable.gameover).into(imageView)

        var params = intent.extras
        var score= params?.getInt("Score")
        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        newScore = settings.getInt("record",0)
        scoreView.text = score.toString()

        if(newScore < score?.toInt()!!){
            newRecord = true
            numRecord.text = score.toString()
        }else{
            newRecord = false
            numRecord.text = newScore.toString()
        }

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

    override fun onStop() {
        super.onStop()

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val edit = settings.edit()

        if (newRecord == true) {
            with(edit) {
                putBoolean("saveNewRecord", newRecord)
                putInt("record",newScore)
                commit()
            }

        } else {
            with(edit) {
                remove("saveNewRecord")
                remove("record")
                commit()
            }
        }
    }

}
