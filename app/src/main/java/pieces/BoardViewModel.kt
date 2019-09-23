package pieces

import androidx.lifecycle.ViewModel

class BoardViewModel : ViewModel() {
    val LINHA = 36
    val COLUNA = 26
    var board = Array(LINHA) {
        Array(COLUNA) { 0 }
    }
}