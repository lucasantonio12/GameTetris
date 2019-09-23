package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toDrawable
import pieces.*
import java.util.*
import kotlin.random.Random


class Game : AppCompatActivity() {

    val LINE = 36
    val COLUMN = 26
    val DOWNLIMIT = 0
    val UPPERLIMIT = 0
    var running = true
    var speed:Long = 200
    var pt = initPiece()
    var checked = false
    val PREFS = "prefs_file"
    var score = 0
    var checkSpeed = 200

    var board = Array(LINE) {
        Array(COLUMN){0}
    }

    var boardView = Array(LINE){
        arrayOfNulls<ImageView>(COLUMN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridboard.rowCount = LINE
        gridboard.columnCount = COLUMN

        val inflater = LayoutInflater.from(this)
        scoreText.text = score.toString()

        for (i in 0 until LINE) {
            for (j in 0 until COLUMN) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }
        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)

        if(settings.contains("dificil")){
            speed = 100
            checkSpeed = 100
        }else{
            speed = 200
            checkSpeed = 200
        }

        left.setOnClickListener {
            if(colisionLeft()) {
                pt.moveLeft()
            }
        }

        right.setOnClickListener {
            if(colisionRight()) {
                pt.moveRight()
            }
        }

        rotation.setOnClickListener {
            if(colisionLeft() && colisionRight()) {
                if (checked) {
                    checked = false
                    pt.rotateCenter()
                } else {
                    checked = true
                    pt.rotateRight()
                }
            }
        }

        downButton.setOnClickListener {
             speed = 80
        }

        gameRun()
    }

    fun initPiece():Piece{
        var randomPiece = Random.nextInt(1,7)
        when(randomPiece){
            1->{
                return I(1,12)
            }
            2->{
                return L(1,12)
            }
            3->{
                return N(1,12)
            }
            4->{
                return T(1,12)
            }
            5->{
                return Z(1,12)
            }
            6->{
                return C(1,12)
            }
            7->{
                return O(1,12)
            }
            else ->return O(1,12)
        }
    }

    fun colisionLeft(): Boolean {
        return((pt.pixelA.y + 1 < COLUMN) && (pt.pixelB.y + 1 < COLUMN) &&
                (pt.pixelC.y + 1 < COLUMN) && (pt.pixelD.y + 1 < COLUMN))
    }

    fun colisionRight(): Boolean{
        return((pt.pixelA.y - 1 >= DOWNLIMIT) && (pt.pixelB.y - 1 >= DOWNLIMIT)  &&
                (pt.pixelC.y - 1 >= DOWNLIMIT)  && (pt.pixelD.y - 1 >= DOWNLIMIT))
    }

    fun colisionDonw():Boolean{
        return((pt.pixelA.x + 1 <= LINE) && (pt.pixelB.x + 1 <= LINE) &&
                (pt.pixelC.x + 1 <= LINE) && (pt.pixelD.x + 1 <= LINE))
    }

    fun colisionPiece():Boolean{
        return((board[pt.pixelA.x][pt.pixelA.y] != 1) && (board[pt.pixelB.x][pt.pixelB.y] != 1) &&
                (board[pt.pixelC.x][pt.pixelC.y] != 1) && (board[pt.pixelD.x][pt.pixelD.y] != 1))
    }

    fun gameOver():Boolean{
        return((pt.pixelA.x - 1 > UPPERLIMIT) && (pt.pixelB.x - 1 > UPPERLIMIT) &&
                (pt.pixelC.x - 1 > UPPERLIMIT) && (pt.pixelD.x - 1 > UPPERLIMIT))
    }

    fun printPiece(){
        boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.white)
    }

    fun savePiece(){
        board[pt.pixelA.x][pt.pixelA.y] = 1
        board[pt.pixelB.x][pt.pixelB.y] = 1
        board[pt.pixelC.x][pt.pixelC.y] = 1
        board[pt.pixelD.x][pt.pixelD.y] = 1
    }

    fun scoreLine(){
        for (i in 0 until LINE) {
            var cont = 0
            for (j in 0 until COLUMN) {
                if(board[i][j] == 0) {
                    break
                }
                else{
                    cont++
                    if((cont === COLUMN)){
                        destroyLine(i)
                    }
                }
            }
        }
    }

    fun destroyLine(line:Int){
        board[line] = Array(COLUMN){0}
        for(i in line downTo  1){
            board[i] = board[i - 1]
        }
        score += 20
        scoreText.text = score.toString()
    }



    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINE) {
                        for (j in 0 until COLUMN) {
                            when(board[i][j]){
                                0->{
                                    boardView[i][j]!!.setImageResource(R.drawable.black)
                                }
                                1->{
                                    boardView[i][j]!!.setImageResource(R.drawable.white)
                                }
                            }
                        }
                    }

                    pt.moveDown()

                    if (colisionDonw() && colisionPiece()) {
                        printPiece()
                    }else if(gameOver()){
                        when(checkSpeed) {
                            200->{
                                speed = 200
                            }
                            100->{
                               speed = 100
                            }
                        }

                        pt.moveUp()
                        printPiece()
                        savePiece()
                        checked = false
                        pt = initPiece()
                    }else{
                        var i = Intent(this,GameOver::class.java)
                        i.putExtra("Score",score)
                        startActivity(i)
                        finish()
                    }
                    scoreLine()
                }
            }
        }.start()
    }
}
