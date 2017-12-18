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
package android.example.com.simpleasynctask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The SimpleAsyncTask app contains a button that launches an AsyncTask
 * which sleeps in the asynchronous thread for a random amount of time.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Initializes the activity.
     * @param savedInstanceState The current state data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restore TextView if there is a savedInstanceState
        if (savedInstanceState != null) {
            textView1.text = savedInstanceState.getString(TEXT_STATE)
        }
    }

    /**
     * Handles the onClick for the "Start Task" button. Launches the AsyncTask
     * which performs work off of the UI thread.
     * @param view The view (Button) that was clicked.
     */
    fun startTask(view: View) {
        // Put a message in the text view
        textView1!!.setText(R.string.napping)

        // Start the AsyncTask.
        // The AsyncTask has a callback that will update the text view.
        SimpleAsyncTask(textView1!!).execute()
    }


    /**
     * Saves the contents of the TextView to restore on configuration change.
     * @param outState The bundle in which the state of the activity is saved when
     * it is spontaneously destroyed.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state of the TextView
        outState.putString(TEXT_STATE, textView1!!.text.toString())
    }

    companion object {

        //Key for saving the state of the TextView
        private val TEXT_STATE = "currentText"
    }
}
