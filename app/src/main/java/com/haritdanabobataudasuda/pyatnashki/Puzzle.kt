package com.haritdanabobataudasuda.pyatnashki

import java.util.Random

class Puzzle {
    private val numRows = 4
    private val numCols = 4
    private val board = Array(numRows) { IntArray(numCols) }
    private var emptyRow = numRows - 1
    private var emptyCol = numCols - 1

    init {
        reset()
        shuffle()
    }

    fun reset() {
        var value = 1
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                board[row][col] = value
                value = (value + 1) % (numRows * numCols)
            }
        }
        emptyRow = numRows - 1
        emptyCol = numCols - 1
        board[emptyRow][emptyCol] = 0
    }

    fun getNumRows(): Int {
        return numRows
    }


    fun shuffle() {
        val random = Random()
        for (i in 0 until 1000) {
            val row = random.nextInt(numRows)
            val col = random.nextInt(numCols)
            val otherRow = random.nextInt(numRows)
            val otherCol = random.nextInt(numCols)
            swapTiles(row, col, otherRow, otherCol)
        }
    }

    private fun swapTiles(row1: Int, col1: Int, row2: Int, col2: Int) {
        val temp = board[row1][col1]
        board[row1][col1] = board[row2][col2]
        board[row2][col2] = temp
        if (board[row1][col1] == 0) {
            emptyRow = row2
            emptyCol = col2
        } else if (board[row2][col2] == 0) {
            emptyRow = row1
            emptyCol = col1
        }
    }

    fun getNumCols(): Int {
        return numCols
    }

    fun getTileValue(row: Int, col: Int): Int {
        return board[row][col]
    }

    fun moveTile(row: Int, col: Int): Boolean {
        if (isValidMove(row, col)) {
            board[emptyRow][emptyCol] = board[row][col]
            board[row][col] = 0
            emptyRow = row
            emptyCol = col
            return true
        }
        return false
    }

    private fun isValidMove(row: Int, col: Int): Boolean {
        return (row >= 0 && row < numRows && col >= 0 && col < numCols
                && (row == emptyRow && Math.abs(col - emptyCol) == 1
                || col == emptyCol && Math.abs(row - emptyRow) == 1))
    }

    fun isSolved(): Boolean {
        var value = 1
        for (row in 0 until numRows) {
            for (col in 0 until numCols) {
                if (board[row][col] != value % (numRows * numCols)) {
                    return false
                }
                value++
            }
        }
        return true
    }
}