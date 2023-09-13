package com.haritdanabobataudasuda.pyatnashki

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.haritdanabobataudasuda.pyatnashki.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment: Fragment() {
    private lateinit var timeHandler: Handler
    private lateinit var timeRunnable: Runnable
    private var _binding: FragmentGameBinding? = null
    private val binding
        get() = _binding!!
    private var elapsedTimeInSeconds: Long = 0
    private lateinit var puzzle: Puzzle
    private lateinit var buttons: Array<Array<Button>>
    private val vm : SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        puzzle = Puzzle()
        _binding = FragmentGameBinding.inflate(inflater)
        buttons = Array(puzzle.getNumRows()) { row ->
            kotlin.Array(puzzle.getNumCols()) { col ->
                val button = android.widget.Button(requireContext())
                button.textSize = 24F
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels
                val screenHeight = displayMetrics.heightPixels
                val buttonSize = (0.2 * java.lang.Math.min(
                    screenWidth,
                    screenHeight
                )).toInt() // Например, 20% от минимальной стороны экрана
                button.layoutParams = android.view.ViewGroup.LayoutParams(buttonSize, buttonSize)
                button.setBackgroundResource(com.haritdanabobataudasuda.pyatnashki.R.drawable.game_button_background)
                button.setTextAppearance(
                    requireContext(),
                    com.haritdanabobataudasuda.pyatnashki.R.style.CustomButtonStyle
                )
                button.setOnClickListener { onTileClick(row, col) }
                _binding?.gridLayout?.addView(button)
                button
            }
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        timeHandler = Handler(Looper.getMainLooper())
        timeHandler.post(object : Runnable{
            override fun run() {
                elapsedTimeInSeconds++
                updateTimer()
                timeHandler.postDelayed(this, 1000)
            }
        })

        binding.restartButton.setOnClickListener {
            puzzle.shuffle()
            elapsedTimeInSeconds = 0
            updateTimer()
            updateUI()
        }
    }

    private fun updateTimer(){
        val hours = elapsedTimeInSeconds / 3600
        val minutes = (elapsedTimeInSeconds % 3600) / 60
        val seconds = elapsedTimeInSeconds % 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        binding.time.text = formattedTime
    }

    private fun onTileClick(row: Int, col: Int) {
        if (puzzle.moveTile(row, col)) {
            updateUI()
            if (puzzle.isSolved()) {
                Log.d("m", "game over")
                vm.setTime(binding.time.text.toString())
                findNavController().navigate(R.id.action_gameFragment_to_finishFragment)
            }
        }
    }

    private fun updateUI() {
        for (row in 0 until puzzle.getNumRows()) {
            for (col in 0 until puzzle.getNumCols()) {
                val value = puzzle.getTileValue(row, col)
                buttons[row][col].text = if (value > 0) value.toString() else ""
            }
        }
    }
}