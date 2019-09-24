package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProviders
import pieces.*
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
    var idPieceColor = 0

    var board = Array(LINE) {
        Array(COLUMN){0}
    }

    var boardView = Array(LINE){
        arrayOfNulls<ImageView>(COLUMN)
    }


    val bvm: BoardViewModel by lazy {
        ViewModelProviders.of(this)[BoardViewModel::class.java]
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
        var velocidade = settings.getString("text","")
        if(velocidade.equals("hard")){
            speed = 100
            checkSpeed = 100
        }else if(velocidade.equals("medium")){
            speed = 200
            checkSpeed = 200
        }else{
            speed = 300
            checkSpeed = 300
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
        var randomPiece = Random.nextInt(1,8)
        when(randomPiece){
            1->{
                idPieceColor = 1
                return I(1,12)
            }
            2->{
                idPieceColor = 2
                return L(1,12)
            }
            3->{
                idPieceColor = 3
                return N(1,12)
            }
            4->{
                idPieceColor = 4
                return T(1,12)
            }
            5->{
                idPieceColor = 5
                return Z(1,12)
            }
            6->{
                idPieceColor = 6
                return C(1,12)
            }
            7->{
                idPieceColor = 7
                return O(1,12)
            }
            else ->{
                idPieceColor = 7
                return O(1,12)
            }
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
        return((bvm.board[pt.pixelA.x][pt.pixelA.y] != 1) && (bvm.board[pt.pixelB.x][pt.pixelB.y] != 1) &&
                (bvm.board[pt.pixelC.x][pt.pixelC.y] != 1) && (bvm.board[pt.pixelD.x][pt.pixelD.y] != 1))
    }

    fun gameOver():Boolean{
        return((pt.pixelA.x - 1 > UPPERLIMIT) && (pt.pixelB.x - 1 > UPPERLIMIT) &&
                (pt.pixelC.x - 1 > UPPERLIMIT) && (pt.pixelD.x - 1 > UPPERLIMIT))
    }

    fun printPiece(){
        colorPiece(idPieceColor)
    }

    fun savePiece(){
        bvm.board[pt.pixelA.x][pt.pixelA.y] = 1
        bvm.board[pt.pixelB.x][pt.pixelB.y] = 1
        bvm.board[pt.pixelC.x][pt.pixelC.y] = 1
        bvm.board[pt.pixelD.x][pt.pixelD.y] = 1
    }

    fun gameMode(){
        when(checkSpeed) {
            200->{
                speed = 200
            }
            100->{
                speed = 100
            }
            300->{
                speed = 300
            }
        }
    }

    fun colorPiece(idPieceColor:Int){
        when(idPieceColor){
            1->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.red)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.red)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.red)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.red)
            }
            2->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.blue)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.blue)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.blue)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.blue)
            }
            3->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.orange)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.orange)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.orange)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.orange)
            }
            4->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.ocean)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.ocean)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.ocean)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.ocean)
            }
            5->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.purple)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.purple)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.purple)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.purple)
            }
            6->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.yellow)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.yellow)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.yellow)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.yellow)
            }
            7->{
                boardView[pt.pixelA.x][pt.pixelA.y]!!.setImageResource(R.drawable.pink)
                boardView[pt.pixelB.x][pt.pixelB.y]!!.setImageResource(R.drawable.pink)
                boardView[pt.pixelC.x][pt.pixelC.y]!!.setImageResource(R.drawable.pink)
                boardView[pt.pixelD.x][pt.pixelD.y]!!.setImageResource(R.drawable.pink)
            }
        }
    }

    fun colorBoard(i:Int,j:Int,idPieceColor: Int){
        when(idPieceColor){
            1->{
                boardView[i][j]!!.setImageResource(R.drawable.red)
            }
            2->{
                boardView[i][j]!!.setImageResource(R.drawable.blue)
            }
            3->{
                boardView[i][j]!!.setImageResource(R.drawable.orange)
            }
            4->{
                boardView[i][j]!!.setImageResource(R.drawable.ocean)
            }
            5->{
                boardView[i][j]!!.setImageResource(R.drawable.purple)
            }
            6->{
                boardView[i][j]!!.setImageResource(R.drawable.yellow)
            }
            7->{
                boardView[i][j]!!.setImageResource(R.drawable.pink)
            }
        }
    }

    fun scoreLine(){
        for (i in 0 until LINE) {
            var cont = 0
            for (j in 0 until COLUMN) {
                if(bvm.board[i][j] == 0) {
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
        bvm.board[line] = Array(COLUMN){0}
        for(i in line downTo  1){
            bvm.board[i] = bvm.board[i - 1]
        }
        score += 20
        scoreText.text = score.toString()
    }

    override fun onPause() {
        super.onPause()
        running = false
    }

    override fun onRestart() {
        super.onRestart()
        running = true
        gameRun()
    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                   // boardColor()
                    for (i in 0 until LINE) {
                        for (j in 0 until COLUMN) {
                            when(bvm.board[i][j]){

                                0->{
                                    boardView[i][j]!!.setImageResource(R.drawable.greenthree)
                                }
                                1->{
                                    colorBoard(i,j,idPieceColor)
                                    //boardView[i][j]!!.setImageResource(R.drawable.green)
                                }
                            }
                        }
                    }
                    pt.moveDown()
                    if (colisionDonw() && colisionPiece()) {
                        printPiece()
                    }else if(gameOver()){
                        gameMode()
                        pt.moveUp()
                        printPiece()
                        savePiece()
                        checked = false
                        pt = initPiece()
                    }else{
                        running = false
                        Thread.interrupted()
                        var i = Intent(this,GameOver::class.java)
                        i.putExtra("Score",score)
                        startActivity(i)

                    }
                    scoreLine()
                }
            }
        }.start()
    }
}
