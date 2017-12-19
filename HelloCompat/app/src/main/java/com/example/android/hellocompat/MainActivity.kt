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
package com.example.android.hellocompat

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.util.*

/**
 * HelloCompat demonstrates the use of the ContextCompat class, part of the
 * V4 support library, to help demonstrate why the support libraries are useful.
 */
class MainActivity : AppCompatActivity() {
    // Text view for Hello World.
    private var mHelloTextView: TextView? = null

    // array of color names, these match the color resources in color.xml
    private val mColorArray = arrayOf("red", "pink", "purple", "deep_purple", "indigo", "blue", "light_blue", "cyan", "teal", "green", "light_green", "lime", "yellow", "amber", "orange", "deep_orange", "brown", "grey", "blue_grey", "black")

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the main text view
        mHelloTextView = findViewById<View>(R.id.hello_textview) as TextView

        // Restore saved instance state (the text color)
        if (savedInstanceState != null) {
            mHelloTextView!!.setTextColor(savedInstanceState.getInt("color"))
        }
    }

    /**
     * Handles the onClick for the change color button. Chooses a random color name from
     * the color array and gets the color value for that name from the resources. Sets
     * the Hello World textview to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    fun changeColor(view: View) {
        // get a random color name from the color array (20 colors)
        val random = Random()
        val colorName = mColorArray[random.nextInt(20)]

        // get the color identifier that matches the color name
        val colorResourceName = resources.getIdentifier(colorName,
                "color", applicationContext.packageName)

        // get the color ID from the resources
        // The pre API 23 way
        // int colorRes = getResources().getColor(colorResourceName);
        // The post API way
        // int colorRes = getResources().getColor(colorResourceName, this.getTheme());
        // Compatible way
        val colorRes = ContextCompat.getColor(this, colorResourceName)

        // Set the text color
        mHelloTextView!!.setTextColor(colorRes)
    }

    /**
     * Save the instance state if the activity is restarted (for example, on device rotation).
     * Saves the color of the hello world text.
     *
     * @param outState The state data.
     */
    public override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // save the current text color
        outState!!.putInt("color", mHelloTextView!!.currentTextColor)
    }
}
