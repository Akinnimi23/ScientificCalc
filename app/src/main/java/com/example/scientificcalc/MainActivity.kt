package com.example.scientificcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"

class MainActivity : AppCompatActivity() {

    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.tvw1) }


    // Variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById<EditText>(R.id.result)
        newNumber = findViewById<EditText>(R.id.newNumber)

        //Data input button
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val btn0: Button = findViewById(R.id.btn0)
        val btndot: Button = findViewById(R.id.btndot)

        //Operation button
        val btnmul = findViewById<Button>(R.id.btnmul)
        val btndiv = findViewById<Button>(R.id.btndiv)
        val btnsum = findViewById<Button>(R.id.btnsum)
        val btnsub = findViewById<Button>(R.id.btnsub)
        val btnequal = findViewById<Button>(R.id.btnequal)
        val btndel = findViewById<Button>(R.id.btndel)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        btn0.setOnClickListener(listener)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
        btn3.setOnClickListener(listener)
        btn4.setOnClickListener(listener)
        btn5.setOnClickListener(listener)
        btn6.setOnClickListener(listener)
        btn7.setOnClickListener(listener)
        btn8.setOnClickListener(listener)
        btn9.setOnClickListener(listener)
        btndot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
        }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }

        btnequal.setOnClickListener(opListener)
        btndiv.setOnClickListener(opListener)
        btnmul.setOnClickListener(opListener)
        btnsum.setOnClickListener(opListener)
        btnsub.setOnClickListener(opListener)


        btndel.setOnClickListener { view ->
            val value = 0
            operand1 = null
            if (value == 0){
                result.setText("")
                newNumber.setText("")
            }

        }
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
                 operand1 = value
                } else {
                    if (pendingOperation == "=") {
                    pendingOperation = operation
                    }
                    when (pendingOperation) {
                        "=" -> operand1 = value
                        "/" -> operand1 = if (value == 0.0) {
                            Double.NaN // handle attempt to divide by zero
                        } else {
                            operand1!! / value
                        }
                        "*" -> operand1 = operand1!! * value
                        "-" -> operand1 = operand1!! - value
                        "+" -> operand1 = operand1!! + value
                    }
                }
                result.setText(operand1.toString())
                newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1!=null) {
            outState.putDouble( STATE_OPERAND1, operand1!!)
        } 
        outState.putString(STATE_PENDING_OPERATION, pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1)

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)!!
        displayOperation.text = pendingOperation

    }
}