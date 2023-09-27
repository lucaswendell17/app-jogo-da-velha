package com.lucaswendel.jogodavelha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    var isPlayer1: Boolean = true
    var gameEnd: Boolean = false

    private lateinit var boxes: Array<ImageView>

    var emptyBoxes = 9

    private lateinit var cardResult: CardView
    private lateinit var textResult: TextView

    private lateinit var top: ImageView
    private lateinit var topStart: ImageView
    private lateinit var topEnd: ImageView

    private lateinit var center: ImageView
    private lateinit var centerStart: ImageView
    private lateinit var centerEnd: ImageView

    private lateinit var bottom: ImageView
    private lateinit var bottomStart: ImageView
    private lateinit var bottomEnd: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        top = findViewById(R.id.top)
        topStart = findViewById(R.id.top_start)
        topEnd = findViewById(R.id.top_end)

        center = findViewById(R.id.center)
        centerStart = findViewById(R.id.center_start)
        centerEnd = findViewById(R.id.center_end)

        bottom = findViewById(R.id.bottom)
        bottomStart = findViewById(R.id.bottom_start)
        bottomEnd = findViewById(R.id.bottom_end)

        boxes = arrayOf(top, topStart, topEnd, center, centerStart, centerEnd,
            bottom, bottomStart, bottomEnd)

        cardResult = findViewById(R.id.card_result)
        textResult = findViewById(R.id.text_result)

        val reset: Button = findViewById(R.id.button_reset)

        reset.setOnClickListener {
            for (box in boxes) {
                reset(box)
            }
        }

        for (box in boxes){
            configureBox(box)
        }

    }

    private fun reset(box: ImageView){
        box.setImageDrawable(null)
        box.tag = null
        isPlayer1 = true
        gameEnd = false
        emptyBoxes = 9
        cardResult.isVisible = false
    }

    private fun configureBox(box: ImageView) {
        box.setOnClickListener {
            if (box.tag == null && !gameEnd) {
                val currentPlayer = if (isPlayer1) 1 else 2

                if (currentPlayer == 1) {
                    box.setImageResource(R.drawable.baseline_circle_24)
                    box.tag = 1
                } else {
                    box.setImageResource(R.drawable.baseline_close_24)
                    box.tag = 2
                }

                isPlayer1 = !isPlayer1
                emptyBoxes--

                if(playerWin(currentPlayer)){
                    showWinMessage(currentPlayer)
                    gameEnd = true
                } else if (emptyBoxes == 0) {
                    cardResult.isVisible = true
                    textResult.text = "Jogo Empatado!\nInicie um novo jogo!"
                    gameEnd = true
                }
            }
        }
    }

    private fun playerWin(value: Int): Boolean {
        if ( (top.tag == value && center.tag == value && bottom.tag == value) ||
             (topStart.tag == value && centerStart.tag == value && bottomStart.tag == value) ||
             (topEnd.tag == value && centerEnd.tag == value && bottomEnd.tag == value) ||

             (topStart.tag == value && top.tag == value && topEnd.tag == value) ||
             (centerStart.tag == value && center.tag == value && centerEnd.tag == value) ||
             (bottomStart.tag == value && bottom.tag == value && bottomEnd.tag == value) ||

             (topStart.tag == value && center.tag == value && bottomEnd.tag == value) ||
             (topEnd.tag == value && center.tag == value && bottomStart.tag == value) ){
            return true
        }
        return false
    }

    private fun showWinMessage(player: Int) {
        val message = if (player == 1) "Player 1 Venceu!" else "Player 2 Venceu!"
        cardResult.isVisible = true
        textResult.text = message
    }
}