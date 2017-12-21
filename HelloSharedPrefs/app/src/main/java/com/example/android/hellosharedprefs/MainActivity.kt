/*
 * Copyright (C) 2016 Google Inc.
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
package com.example.android.hellosharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * HelloSharedPrefs is an adaptation of the HelloToast app from chapter 1. It includes:
 * - Buttons for changing the background color.
 * - Maintenance of instance state.
 * - Themes and styles.
 * - Read and write shared preferences for the current count and the color.
 *
 *
 * This is the solution code for HelloSharedPrefs.
 */
class MainActivity : AppCompatActivity() {
    // Current count.
    private var mCount = 0
    // Current background color.
    private var mColor: Int = 0

    // Key for current count
    private val COUNT_KEY = "count"
    // Key for current color
    private val COLOR_KEY = "color"

    // Shared preferences object
    private var mPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views, color, preferences
        mColor = ContextCompat.getColor(this, R.color.default_background)
        mPreferences = getSharedPreferences(mSharedPrefFile, Context.MODE_PRIVATE)

        // Restore preferences
        mCount = mPreferences!!.getInt(COUNT_KEY, 0)
        count_textview!!.text = String.format("%s", mCount)
        mColor = mPreferences!!.getInt(COLOR_KEY, mColor)
        count_textview!!.setBackgroundColor(mColor)
    }

    /**
     * Callback for activity pause.  Shared preferences are saved in this callback.
     */
    override fun onPause() {
        super.onPause()

        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.putInt(COUNT_KEY, mCount)
        preferencesEditor.putInt(COLOR_KEY, mColor)
        preferencesEditor.apply()
    }

    /**
     * Handles the onClick for the background color buttons.  Gets background color of the button
     * that was clicked and sets the textview background to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        count_textview!!.setBackgroundColor(color)
        mColor = color
    }

    /**
     * Handles the onClick for the Count button.  Increments the value of the mCount global and
     * updates the textview.
     *
     * @param view The view (Button) that was clicked.
     */
    fun countUp(view: View) {
        mCount++
        count_textview!!.text = String.format("%s", mCount)
    }

    /**
     * Handles the onClick for the Reset button.  Resets the global count and background
     * variables to the defaults, resets the views to those values, and clears the shared
     * preferences
     *
     * @param view The view (Button) that was clicked.
     */
    fun reset(view: View) {
        // Reset count
        mCount = 0
        count_textview!!.text = String.format("%s", mCount)

        // Reset color
        mColor = ContextCompat.getColor(this, R.color.default_background)
        count_textview!!.setBackgroundColor(mColor)

        // Clear preferences
        val preferencesEditor = mPreferences!!.edit()
        preferencesEditor.clear()
        preferencesEditor.apply()

    }

    companion object {
        // Name of shared preferences file
        private val mSharedPrefFile = "com.example.android.hellosharedprefs"
    }
}
