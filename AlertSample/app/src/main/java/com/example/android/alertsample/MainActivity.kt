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


package com.example.android.alertsample


import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

/**
 * This app shows a button to trigger a standard alert dialog.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Creates the view.
     * @param savedInstanceState    The saved instance.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Builds a standard alert dialog,
     * using setTitle to set its title, setMessage to set its message,
     * and setPositiveButton and setNegativeButton to set its buttons.
     * Defines toast messages to appear depending on which alert
     * button is clicked.
     * Shows the alert.
     * @param view  The activity's view in which the alert will appear.
     */
    fun onClickShowAlert(view: View) {
        // Build the alert dialog.
        val myAlertBuilder = AlertDialog.Builder(this@MainActivity)
        // Set the dialog title.
        myAlertBuilder.setTitle(R.string.alert_title)
        // Set the dialog message.
        myAlertBuilder.setMessage(R.string.alert_message)
        // Add the buttons.
        myAlertBuilder.setPositiveButton(R.string.ok) { dialog, which ->
            // User clicked OK button.
            Toast.makeText(applicationContext, R.string.pressed_ok,
                    Toast.LENGTH_SHORT).show()
        }
        myAlertBuilder.setNegativeButton(R.string.cancel) { dialog, which ->
            // User clicked the CANCEL button.
            Toast.makeText(applicationContext, R.string.pressed_cancel,
                    Toast.LENGTH_SHORT).show()
        }

        // Create and show the AlertDialog.
        myAlertBuilder.show()

    }
}