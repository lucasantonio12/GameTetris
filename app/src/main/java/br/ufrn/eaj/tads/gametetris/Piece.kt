package br.ufrn.eaj.tads.gametetris

abstract class Piece (var  x:Int, var y:Int){
    var pontoA:Point = Point(x,y)
     lateinit var pontoB:Point
     lateinit var pontoC:Point
     lateinit var pontoD:Point

    abstract fun moveDown()
    abstract fun moveLeft()
    abstract fun moveRight()
    abstract fun rotateRight()
    abstract fun rotateCenter()

}