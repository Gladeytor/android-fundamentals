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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Activity that searches the database for a string entered by the user.
 * - Displays any strings matching the search term in a text view.
 * - If the search term is empty, prints all the words in the database.
 * - If the word is not found, displays "No result."
 */
class SearchActivity : AppCompatActivity() {

    private var mDB: WordListOpenHelper? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mDB = WordListOpenHelper(this)
    }

    // Click handler for Search button.
    fun showResult(view: View) {
        val word = search_word!!.text.toString()
        search_result!!.text = "Result for $word:\n\n"

        // Search for the word in the database.
        val cursor = mDB!!.search(word)
        // You must move the cursor to the first item.
        cursor!!.moveToFirst()
        // Only process a non-null cursor with rows.
        if (cursor.count > 0) {
            var index: Int
            var result: String
            // Iterate over the cursor, while there are entries.
            do {
                // Don't guess at the column index. Get the index for the named column.
                index = cursor.getColumnIndex(WordListOpenHelper.KEY_WORD)
                // Get the value from the column for the current cursor.
                result = cursor.getString(index)
                // Add result to what's already in the text view.
                search_result!!.append(result + "\n")
            } while (cursor.moveToNext())
            cursor.close()
        } else {
            search_result!!.append(getString(R.string.no_result))
        }
    }

    companion object {

        private val TAG = EditWordActivity::class.java.simpleName
    }
}
