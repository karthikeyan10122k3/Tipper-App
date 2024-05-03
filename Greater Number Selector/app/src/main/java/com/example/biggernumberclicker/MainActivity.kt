package com.example.biggernumberclicker

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var leftNumButton: Button
    private lateinit var rightNumButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        leftNumButton = findViewById(R.id.leftNum)
        rightNumButton = findViewById(R.id.rightNum)
        mainLayout = findViewById((R.id.main))

        assignNumbers()

        leftNumButton.setOnClickListener {
            checkGreaterNumber(true)

            assignNumbers()
        }
        rightNumButton.setOnClickListener {
            checkGreaterNumber(false)

            assignNumbers()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun assignNumbers() {
        val random = Random
        leftNumButton.text = random.nextInt(10).toString()
        rightNumButton.text = random.nextInt(10).toString()
        if(leftNumButton.text.equals(rightNumButton.text)){
            rightNumButton.text = random.nextInt(10).toString()
        }
    }

    private fun checkGreaterNumber(isLeftButton: Boolean) {

        val leftNumValue = leftNumButton.text.toString().toInt()
        val rightNumValue = rightNumButton.text.toString().toInt()

        val operation  =if( isLeftButton)  leftNumValue > rightNumValue else leftNumValue < rightNumValue

        if(operation){
            mainLayout.setBackgroundColor(Color.GREEN)
            Toast.makeText(this,"Correct Answer",Toast.LENGTH_SHORT).show()

        }else{
            mainLayout.setBackgroundColor(Color.RED)
            Toast.makeText(this,"Wrong Answer",Toast.LENGTH_SHORT).show()
        }
    }
}