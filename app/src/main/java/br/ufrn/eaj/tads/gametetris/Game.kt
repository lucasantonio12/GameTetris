package br.ufrn.eaj.tads.gametetris

import android.content.Context
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
    val LIMIT = 0
    var running = true
    var speed:Long = 350
    var pt = initPiece()
    var checked = false
    val PREFS = "prefs_file"
    var score = 0
    //val board = Array(LINHA, { IntArray(COLUNA) })

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
        /*
        if(){
            speed = 350
        }else{
            speed = 230
        }
        */
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
             speed = 100
        }

        gameRun()
    }

    fun initPiece():Piece{
        var randomPiece = Random.nextInt(1,6)
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
            else ->return O(1,12)
        }
    }

    fun colisionLeft(): Boolean {
        return((pt.pixelA.y + 1 < COLUMN) && (pt.pixelB.y + 1 < COLUMN) &&
                (pt.pixelC.y + 1 < COLUMN) && (pt.pixelD.y + 1 < COLUMN))
    }

    fun colisionRight(): Boolean{
        return((pt.pixelA.y - 1 >= LIMIT) && (pt.pixelB.y - 1 >= LIMIT)  &&
                (pt.pixelC.y - 1 >= LIMIT)  && (pt.pixelD.y - 1 >= LIMIT))
    }

    fun colisionDonw():Boolean{
        return((pt.pixelA.x + 1 <= LINE) && (pt.pixelB.x + 1 <= LINE) &&
                (pt.pixelC.x + 1 <= LINE) && (pt.pixelD.x + 1 <= LINE))
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



    fun colisionPiece():Boolean{
        return((board[pt.pixelA.x][pt.pixelA.y] != 1) && (board[pt.pixelB.x][pt.pixelB.y] != 1) &&
                (board[pt.pixelC.x][pt.pixelC.y] != 1) && (board[pt.pixelD.x][pt.pixelD.y] != 1))
    }

    fun scoreLine(){
        for (i in 0 until LINE) {
            var cont = 0
            for (j in 0 until COLUMN) {
                if(board[i][j] == 1) {
                    cont++
                }
                else if(cont === 26) {
                    destroyLine(i)
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
                    //move peça atual
                    pt.moveDown()
                    //print peça
                    if(colisionDonw() && colisionPiece()){
                        printPiece()
                    }else{
                        speed = 350
                        pt.moveUp()
                        printPiece()
                        savePiece()
                        checked = false
                        pt = initPiece()
                    }
                    scoreLine()
                }
            }
        }.start()
    }
}
