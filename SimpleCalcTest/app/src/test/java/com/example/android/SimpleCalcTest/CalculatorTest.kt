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

import android.test.suitebuilder.annotation.SmallTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * JUnit4 unit tests for the calculator logic. These are local unit tests; no device needed
 */
@RunWith(JUnit4::class)
@SmallTest
class CalculatorTest {

    private var mCalculator: Calculator? = null

    /**
     * Set up the environment for testing
     */
    @Before
    fun setUp() {
        mCalculator = Calculator()
    }

    /**
     * Test for simple addition
     */
    @Test
    fun addTwoNumbers() {
        val resultAdd = mCalculator!!.add(1.0, 1.0)
        assertThat(resultAdd, `is`(equalTo(2.0)))
    }

    /**
     * Test for addition with a negative operand
     */
    @Test
    fun addTwoNumbersNegative() {
        val resultAdd = mCalculator!!.add(-1.0, 2.0)
        assertThat(resultAdd, `is`(equalTo(1.0)))
    }

    /**
     * Test for addition where the result is negative
     */
    @Test
    fun addTwoNumbersWorksWithNegativeResult() {
        val resultAdd = mCalculator!!.add(-1.0, -17.0)
        assertThat(resultAdd, `is`(equalTo(-18.0)))
    }

    /**
     * Test for floating-point addition
     */
    @Test
    fun addTwoNumbersFloats() {
        val resultAdd = mCalculator!!.add(1.111, 1.111)
        assertThat(resultAdd, `is`(equalTo(2.222)))
    }

    /**
     * Test for especially large numbers
     */
    @Test
    fun addTwoNumbersBignums() {
        val resultAdd = mCalculator!!.add(123456781.0, 111111111.0)
        assertThat(resultAdd, `is`(equalTo(234567892.0)))
    }

    /**
     * Test for simple subtraction
     */
    @Test
    fun subTwoNumbers() {
        val resultSub = mCalculator!!.sub(1.0, 1.0)
        assertThat(resultSub, `is`(equalTo(0.0)))
    }

    /**
     * Test for simple subtraction with a negative result
     */
    @Test
    fun subWorksWithNegativeResult() {
        val resultSub = mCalculator!!.sub(1.0, 17.0)
        assertThat(resultSub, `is`(equalTo(-16.0)))
    }

    /**
     * Test for simple division
     */
    @Test
    fun divTwoNumbers() {
        val resultDiv = mCalculator!!.div(32.0, 2.0)
        assertThat(resultDiv, `is`(equalTo(16.0)))
    }

    /**
     * Test for divide by zero; must throw IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException::class)
    fun divDivideByZeroThrows() {
        mCalculator!!.div(32.0, 0.0)
    }

    /**
     * Test for simple multiplication
     */
    @Test
    fun mulTwoNumbers() {
        val resultMul = mCalculator!!.mul(32.0, 2.0)
        assertThat(resultMul, `is`(equalTo(64.0)))
    }

}