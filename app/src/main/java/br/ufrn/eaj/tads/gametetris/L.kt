package br.ufrn.eaj.tads.gametetris

class L(x:Int, y:Int):Piece(x,y){
    var checked = false
    init {

        pontoB = Point(x-1,y)
        pontoC = Point(x-2,y)
        pontoD = Point(x,y+1)
    }

    override fun moveDown() {
        pontoA.moveDown()
        pontoB.moveDown()
        pontoC.moveDown()
        pontoD.moveDown()
    }

    override fun moveLeft() {
        pontoA.moveLeft()
        pontoB.moveLeft()
        pontoC.moveLeft()
        pontoD.moveLeft()
    }

    override fun moveRight() {
        pontoA.moveRight()
        pontoB.moveRight()
        pontoC.moveRight()
        pontoD.moveRight()
    }

    override fun rotateRight() {
        checked = true

        pontoB.x += 1
        pontoB.y += 1

        pontoC.x += 2
        pontoC.y += 2

        pontoD.x += 1
        pontoD.y += 1

    }
    override fun rotateCenter() {
        checked = false

        pontoB.x -= 1
        pontoB.y -= 1

        pontoC.x -= 2
        pontoC.y -= 2

        pontoD.x -= 1
        pontoD.y -= 1

    }

}