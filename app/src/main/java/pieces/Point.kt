package pieces

class Point(var x:Int, var y:Int){
    fun moveDown(){
        x++
    }

    fun moveLeft(){
        y++
    }

    fun moveRight(){
        y--
    }

    fun moveUp(){
        x--
    }


}