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

package com.android.example.wordlistsqlsearchable

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit_word.*

/**
 * Activity for entering a new word or editing an existing word.
 */
class EditWordActivity : AppCompatActivity() {

    private var mId = MainActivity.WORD_ADD

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)

        // Get data sent from calling activity.
        val extras = intent.extras

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            val id = extras.getInt(WordListAdapter.EXTRA_ID, NO_ID)
            val word = extras.getString(WordListAdapter.EXTRA_WORD, NO_WORD)
            if (id != NO_ID && word !== NO_WORD) {
                mId = id
                edit_word!!.setText(word)
            }
        } // Otherwise, start with empty fields.
    }

    /* *
     * Click handler for the Save button.
     *  Creates a new intent for the reply, adds the reply message to it as an extra,
     *  sets the intent result, and closes the activity.
     */
    fun returnReply(view: View) {
        val word = (findViewById<View>(R.id.edit_word) as EditText).text.toString()

        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, word)
        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId)
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    companion object {

        private val TAG = EditWordActivity::class.java.simpleName

        private val NO_ID = -99
        private val NO_WORD = ""

        // Unique tag for the intent reply.
        val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

