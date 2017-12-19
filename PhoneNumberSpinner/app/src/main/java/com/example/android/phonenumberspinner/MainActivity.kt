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

package com.example.android.phonenumberspinner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This app is a copy of KeyboardSamples that
 * adds a spinner to the layout to appear right next to the phone number field.
 * The spinner lets the user choose the type of phone number:
 * Home, Work, Mobile, and Other.
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    // Define spinnerLabel for the label (the spinner item that the user chooses).
    private var spinnerLabel = ""

    /**
     * Set the content view, create the spinner, and create the array adapter for the spinner.
     * @param savedInstanceState    Saved instance.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (label_spinner != null) {
            label_spinner.onItemSelectedListener = this
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner.
        if (label_spinner != null) {
            label_spinner.adapter = adapter
        }

    }

    /**
     * Retrieves the text and spinner item and shows them in text_phonelabel.
     * @param view  The view containing editText_main.
     */
    fun showText(view: View) {
        if (editText_main != null) {
            // Assign to showString both the entered string and spinnerLabel.
            val showString = editText_main.text.toString() + " - " + spinnerLabel

            // Show the showString in the phoneNumberResult.
            if (text_phonelabel != null) text_phonelabel.text = showString
        }
    }

    /**
     * Retrieves the selected item in the spinner using getItemAtPosition,
     * and assigns it to spinnerLabel.
     * @param adapterView   The adapter for the spinner, where the selection occurred.
     * @param view          The view within the adapterView that was clicked.
     * @param pos             The position of the view in the adapter.
     * @param id             The row id of the item that is selected (not used here).
     */
    override fun onItemSelected(adapterView: AdapterView<*>, view: View, pos: Int, id: Long) {
        spinnerLabel = adapterView.getItemAtPosition(pos).toString()
        showText(view)
    }

    /**
     * Logs the fact that nothing was selected in the spinner.
     * @param adapterView The adapter for the spinner, where the selection should have occurred.
     */
    override fun onNothingSelected(adapterView: AdapterView<*>) {
        Log.d(TAG, getString(R.string.nothing_selected))
    }

    companion object {
        // Define TAG for logging.
        private val TAG = MainActivity::class.java.simpleName
    }
}
