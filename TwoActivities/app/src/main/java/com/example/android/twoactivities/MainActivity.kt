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
package com.example.android.twoactivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The TwoActivities app contains two activities and sends messages (intents) between them.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Handle the onClick for the "Send" button. Gets the value of the main EditText,
     * creates an intent, and launches the second activity with that intent.
     *
     * The return intent from the second activity is onActivityResult().
     * @param view The view (Button) that was clicked.
     */
    fun launchSecondActivity(view: View) {
        Log.d(LOG_TAG, "Button clicked!")

        val intent = Intent(this, SecondActivity::class.java)
        val message = editText_main!!.text.toString()

        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    /**
     * Handle the data in the return intent from SecondActivity.
     *
     * @param requestCode Code for the SecondActivity request.
     * @param resultCode Code that comes back from SecondActivity.
     * @param data Intent data sent back from SecondActivity.
     */
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Test for the right intent reply
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == Activity.RESULT_OK) {
                val reply = data.getStringExtra(SecondActivity.EXTRA_REPLY)

                // Make the reply head visible.
                text_header_reply!!.visibility = View.VISIBLE

                // Set the reply and make it visible.
                text_message_reply!!.text = reply
                text_message_reply!!.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        // Class name for Log tag
        private val LOG_TAG = MainActivity::class.java!!.simpleName
        // Unique tag required for the intent extra
        val EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE"
        // Unique tag for the intent reply
        val TEXT_REQUEST = 1
    }
}
