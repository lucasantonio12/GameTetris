package br.ufrn.eaj.tads.gametetris

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    val PREFS = "prefs_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val save = settings.getBoolean("save", false)


        if (save){
            saveButton.isChecked = save
            if(facil.isChecked){
                val text = settings.getString("text","easy")
            }else if(dificil.isChecked){
                val text= settings.getString("text","hard")
            }else{
                val text = settings.getString("text","medium")
            }

        }

        sairButton.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()


        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = settings.edit()

        if (saveButton.isChecked) {

            /*
            editor.putBoolean("salvar", checkBox.isChecked)
            editor.putString("texto", editText.text.toString())
            editor.commit()
            */
            with(edit) {
                putBoolean("save", saveButton.isChecked)
                if(facil.isChecked){
                    putString("text","easy")
                }else if(dificil.isChecked){
                    putString("text","hard")
                }else{
                    putString("text","medium")
                }
                commit()
            }


        } else {
            with(settings.edit()) {
                remove("save")
                remove("text")
                commit()
            }
        }
    }
}
