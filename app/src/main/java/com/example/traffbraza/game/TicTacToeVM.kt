package com.example.traffbraza.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.traffbraza.game.utils.GameUtils
import com.example.traffbraza.game.utils.GameUtils.PLAYER_O
import com.example.traffbraza.game.utils.GameUtils.PLAYER_X
import com.example.traffbraza.game.utils.GameUtils.isBoardFull
import com.example.traffbraza.game.utils.GameUtils.isGameWon

private val CLEAR_BOARD = arrayListOf("", "", "", "", "", "", "", "", "")

class TicTacToeVM : ViewModel() {

    var isGameOver by mutableStateOf(false)
        private set

    var winner by mutableStateOf("")
        private set

    var board by mutableStateOf(CLEAR_BOARD)
        private set

    private var currentPlayer = PLAYER_X

    fun play(move: Int) {
        if (isGameOver) return

        if (board[move] == "") {
            if (currentPlayer == PLAYER_X) {
                board = ArrayList(
                    board.toMutableList().also {
                        it[move] = PLAYER_X
                    },
                )
                currentPlayer = PLAYER_O
            } else {
                board = ArrayList(
                    board.toMutableList().also {
                        it[move] = PLAYER_O
                    },
                )
                currentPlayer = PLAYER_X
            }
        }

        // calculate and show game result
        isGameOver = isGameWon(board, PLAYER_X) || isGameWon(board, PLAYER_O) || isBoardFull(board)
        winner = GameUtils.gameResult(board)

        Log.d("TicTacToeVM", "$board")
    }

    fun restart() {
        isGameOver = false
        board = CLEAR_BOARD
    }
}
