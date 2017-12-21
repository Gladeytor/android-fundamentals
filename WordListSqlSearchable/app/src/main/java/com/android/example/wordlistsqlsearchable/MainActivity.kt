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
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Implements a RecyclerView that displays a list of words from a SQL database.
 * - Clicking the fab button opens a second activity to add a word to the database.
 * - Clicking the Edit button opens an activity to edit the current word in the database.
 * - Clicking the Delete button deletes the current word from the database.
 */
class MainActivity : AppCompatActivity() {

    private var mDB: WordListOpenHelper? = null
    //    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: WordListAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDB = WordListOpenHelper(this)

        // Create recycler view.
        // Create an mAdapter and supply the data to be displayed.
        mAdapter = WordListAdapter(this, /* mDB.getAllEntries(),*/ mDB!!)
        // Connect the mAdapter with the recycler view.
        recyclerview!!.adapter = mAdapter
        // Give the recycler view a default layout manager.
        recyclerview!!.layoutManager = LinearLayoutManager(this)

        // Add a floating action click handler for creating new entries.
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            // Starts empty edit activity.
            val intent = Intent(baseContext, EditWordActivity::class.java)
            startActivityForResult(intent, WORD_EDIT)
        }
    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param   menu    Menu to inflate.
     * @return          Returns true if the menu inflated.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item  Item clicked.
     * @return      Returns true if one of the defined items was clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                // Starts search activity.
                val intent = Intent(baseContext, com.android.example.wordlistsqlsearchable.SearchActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == WORD_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                val word = data.getStringExtra(EditWordActivity.EXTRA_REPLY)

                // Update the database.
                if (!TextUtils.isEmpty(word)) {
                    val id = data.getIntExtra(WordListAdapter.EXTRA_ID, -99)

                    if (id == WORD_ADD) {
                        mDB!!.insert(word)
                    } else if (id >= 0) {
                        mDB!!.update(id, word)
                    }
                    // Update the UI.
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                            applicationContext,
                            R.string.empty_word_not_saved,
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        val WORD_EDIT = 1
        val WORD_ADD = -1
    }
}