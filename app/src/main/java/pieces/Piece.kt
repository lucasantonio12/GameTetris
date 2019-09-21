package pieces

import pieces.Point

abstract class Piece (var  x:Int, var y:Int){
    var pixelA: Point = Point(x, y)
     lateinit var pixelB: Point
     lateinit var pixelC: Point
     lateinit var pixelD: Point

    abstract fun moveDown()
    abstract fun moveLeft()
    abstract fun moveRight()
    abstract fun rotateRight()
    abstract fun rotateCenter()
    abstract  fun moveUp()

}