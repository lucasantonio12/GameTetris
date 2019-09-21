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
        val salvar = settings.getBoolean("salvar", false)


        if (salvar){
            salveButton.isChecked = salvar

            if(facil.isChecked){
                val texto = settings.getString("texto","facil")

            }else{
                val texto = settings.getString("texto","dificil")
            }

        }

        sairButton.setOnClickListener {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()


        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var editor = settings.edit()

        if (salveButton.isChecked) {

            /*
            editor.putBoolean("salvar", checkBox.isChecked)
            editor.putString("texto", editText.text.toString())
            editor.commit()
            */

            with(settings.edit()) {
                putBoolean("salvar", salveButton.isChecked)
                if(facil.isChecked){
                    putString("texto","facil")
                }else{
                    putString("texto","dificil")
                }
                commit()
            }


        } else {

            with(settings.edit()) {
                remove("salvar")
                remove("texto")
                commit()
            }
        }
    }
}
