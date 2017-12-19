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
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * SecondActivity defines the second activity in the app. It is launched from an intent
 * with a message, and sends an intent back with a second message.
 */
class SecondActivity : AppCompatActivity() {

    // EditText for the reply.
    private var mReply: EditText? = null

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize view variables.
        mReply = findViewById<View>(R.id.editText_second) as EditText

        // Get the intent that launched this activity, and the message in
        // the intent extra.
        val intent = intent
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        // Put that message into the text_message TextView
        val textView = findViewById<View>(R.id.text_message) as TextView
        if (textView != null) {
            textView.text = message
        }
    }

    /**
     * Handle the onClick for the "Reply" button. Gets the message from the second EditText,
     * creates an intent, and returns that message back to the main activity.
     *
     * @param view The view (Button) that was clicked.
     */
    fun returnReply(view: View) {
        // Get the reply message from the edit text.
        val reply = mReply!!.text.toString()

        // Create a new intent for the reply, add the reply message to it as an extra,
        // set the intent result, and close the activity.
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    companion object {
        // Unique tag for the intent reply.
        val EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY"
    }
}
