/*
 * Copyright 2016, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.SimpleCalcTest

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

/**
 * SimpleCalcTest is the final version of SimpleCalc.  It adds:
 * - Input validation (no input, bad number format, div by zero)
 * - Local unit testing for various cases
 */
class MainActivity : Activity() {

    private var mCalculator: Calculator? = null

    private var mOperandOneEditText: EditText? = null
    private var mOperandTwoEditText: EditText? = null

    private var mResultTextView: TextView? = null

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the calculator class and all the views
        mCalculator = Calculator()
        mResultTextView = findViewById<View>(R.id.operation_result_text_view) as TextView
        mOperandOneEditText = findViewById<View>(R.id.operand_one_edit_text) as EditText
        mOperandTwoEditText = findViewById<View>(R.id.operand_two_edit_text) as EditText
    }

    /**
     * OnClick method that is called when the add [Button] is pressed.
     */
    fun onAdd(view: View) {
        compute(Calculator.Operator.ADD)
    }

    /**
     * OnClick method that is called when the substract [Button] is pressed.
     */
    fun onSub(view: View) {
        compute(Calculator.Operator.SUB)
    }

    /**
     * OnClick method that is called when the divide [Button] is pressed.
     */
    fun onDiv(view: View) {
        try {
            compute(Calculator.Operator.DIV)
        } catch (iae: IllegalArgumentException) {
            Log.e(TAG, "IllegalArgumentException", iae)
            mResultTextView!!.text = getString(R.string.computationError)
        }

    }

    /**
     * OnClick method that is called when the multiply [Button] is pressed.
     */
    fun onMul(view: View) {
        compute(Calculator.Operator.MUL)
    }

    private fun compute(operator: Calculator.Operator) {
        val operandOne: Double
        val operandTwo: Double
        try {
            operandOne = getOperand(mOperandOneEditText)!!
            operandTwo = getOperand(mOperandTwoEditText)!!
        } catch (nfe: NumberFormatException) {
            Log.e(TAG, "NumberFormatException", nfe)
            mResultTextView!!.text = getString(R.string.computationError)
            return
        }

        val result: String
        when (operator) {
            Calculator.Operator.ADD -> result = mCalculator!!.add(operandOne, operandTwo).toString()
            Calculator.Operator.SUB -> result = mCalculator!!.sub(operandOne, operandTwo).toString()
            Calculator.Operator.DIV -> result = mCalculator!!.div(operandOne, operandTwo).toString()
            Calculator.Operator.MUL -> result = mCalculator!!.mul(operandOne, operandTwo).toString()
            else -> result = getString(R.string.computationError)
        }
        mResultTextView!!.text = result
    }

    companion object {

        private val TAG = "CalculatorActivity"

        /**
         * @return the operand value which was entered in an [EditText] as a double
         */
        private fun getOperand(operandEditText: EditText?): Double? {
            val operandText = getOperandText(operandEditText)
            return java.lang.Double.valueOf(operandText)
        }

        /**
         * @return the operand text which was entered in an [EditText].
         */
        private fun getOperandText(operandEditText: EditText?): String {
            val operandText = operandEditText!!.text.toString()
            if (TextUtils.isEmpty(operandText)) {
                throw NumberFormatException("operand cannot be empty!")
            }
            return operandText
        }
    }
}