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

package com.example.android.recyclerview

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Implements a basic RecyclerView that displays a list of generated words.
 * - Clicking an item marks it as clicked.
 * - Clicking the fab button adds a new word to the list.
 */
class MainActivity : AppCompatActivity() {

    private var wordListAdapter: WordListAdapter? = null
    private val wordList = LinkedList<String>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Put initial data into the word list.
        for (i in 0..19) {
            wordList.addLast("Word " + i)
        }

        // Create an wordListAdapter and supply the data to be displayed.
        wordListAdapter = WordListAdapter(this, wordList)
        // Connect the wordListAdapter with the recycler view.
        recyclerview!!.adapter = wordListAdapter
        // Give the recycler view a default layout manager.
        recyclerview!!.layoutManager = LinearLayoutManager(this)

        // Add a floating action click handler for creating new entries.
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val wordListSize = wordList.size
            // Add a new word to the wordList.
            wordList.addLast("+ Word " + wordListSize)
            // Notify the wordListAdapter, that the data has changed.
            recyclerview!!.adapter.notifyItemInserted(wordListSize)
            // Scroll to the bottom.
            recyclerview!!.smoothScrollToPosition(wordListSize)
        }
    }
}