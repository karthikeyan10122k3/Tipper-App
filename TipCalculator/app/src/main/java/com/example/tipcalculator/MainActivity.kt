package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.tipcalculator.databinding.ActivityMainBinding


private const val INITIAL_TIP_PERCENTAGE_AMT = 15
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.seekTipAmt.progress = INITIAL_TIP_PERCENTAGE_AMT
        binding.tipPercentageLable.text = "$INITIAL_TIP_PERCENTAGE_AMT%"
        tipResponse(INITIAL_TIP_PERCENTAGE_AMT)
        splitSectionToggle()

        binding.seekTipAmt.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tipPercentageLable.text = "$progress %"
                tipResponse(progress)
                computeTipAndTotal()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.baseAmt.addTextChangedListener {
            computeTipAndTotal()
        }

        binding.splitBtn.setOnClickListener {
            splitSectionToggle()
        }
        binding.splitCount.addTextChangedListener {
            computeSplitAmount()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun splitSectionToggle() {
        if (binding.splitAmtLable.visibility == View.VISIBLE) {
            binding.splitAmtLable.visibility = View.INVISIBLE
            binding.splitAmt.visibility = View.INVISIBLE
            binding.splitCount.visibility = View.INVISIBLE
            binding.splitCountLable.visibility = View.INVISIBLE
        } else {
            binding.splitAmtLable.visibility = View.VISIBLE
            binding.splitAmt.visibility = View.VISIBLE
            binding.splitCount.visibility = View.VISIBLE
            binding.splitCountLable.visibility = View.VISIBLE
        }

    }

    private fun computeSplitAmount() {
        if(binding.splitCount.text.isEmpty()){
            binding.splitAmt.text = "0.0"
            return
        }
        val totalAmount = binding.totalAmt.text.toString()
        val splitCount = binding.splitCount.text.toString()
        val splitAmount = totalAmount.toFloat() / splitCount.toFloat()
        binding.splitAmt.text = "%.2f".format(splitAmount)
    }

    private fun tipResponse(progress: Int) {

        val tipResponseText = when(progress){
            in 0..8 -> "Poor"
            in 9..16 -> "Not Bad"
            in 17..24 -> "Good"
            in 25..35 -> "Excellent"
            else -> "ExtraOrdinary"
        }
        binding.response.text = tipResponseText
        val color = ArgbEvaluator().evaluate(
            progress.toFloat() / binding.seekTipAmt.max,
            ContextCompat.getColor(this, R.color.worst_color_tip),
        ContextCompat.getColor(this, R.color.best_color_tip)
        ) as Int
        binding.response.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if( binding.baseAmt.text.isEmpty()){
            binding.tipAmt.text = "0.0"
            binding.totalAmt.text = "0.0"
            return
        }
        val baseAmount = binding.baseAmt.text.toString().toDouble()
        val tipPercent = binding.seekTipAmt.progress
        val tipAmount = baseAmount * tipPercent /100
        val totalAmount = baseAmount + tipAmount

        binding.tipAmt.text = "%.2f".format(tipAmount)
        binding.totalAmt.text = "%.2f".format(totalAmount)
    }
}






