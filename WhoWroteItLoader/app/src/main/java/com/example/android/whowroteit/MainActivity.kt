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
package com.example.android.whowroteit

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject

/**
 * The WhoWroteItLoader app upgrades the WhoWroteIt app to use an AsyncTaskLoader
 * instead of an AsyncTask.
 */
class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {

    // Variables for the search input field, and results TextViews
    private var mBookInput: EditText? = null
    private var mTitleText: TextView? = null
    private var mAuthorText: TextView? = null


    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize all the view variables
        mBookInput = findViewById<View>(R.id.bookInput) as EditText
        mTitleText = findViewById<View>(R.id.titleText) as TextView
        mAuthorText = findViewById<View>(R.id.authorText) as TextView

        //Check if a Loader is running, if it is, reconnect to it
        if (supportLoaderManager.getLoader<Any>(0) != null) {
            supportLoaderManager.initLoader(0, null, this)
        }
    }

    /**
     * Gets called when the user pushes the "Search Books" button
     *
     * @param view The view (Button) that was clicked.
     */
    fun searchBooks(view: View) {
        // Get the search string from the input field.
        val queryString = mBookInput!!.text.toString()

        // Hide the keyboard when the button is pushed.
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)

        // Check the status of the network connection.
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        // If the network is active and the search field is not empty,
        // add the search term to the arguments Bundle and start the loader.
        if (networkInfo != null && networkInfo.isConnected && queryString.length != 0) {
            mAuthorText!!.text = ""
            mTitleText!!.setText(R.string.loading)
            val queryBundle = Bundle()
            queryBundle.putString("queryString", queryString)
            supportLoaderManager.restartLoader(0, queryBundle, this)
        } else {
            if (queryString.length == 0) {
                mAuthorText!!.text = ""
                mTitleText!!.setText(R.string.no_search_term)
            } else {
                mAuthorText!!.text = ""
                mTitleText!!.setText(R.string.no_network)
            }
        }// Otherwise update the TextView to tell the user there is no connection or no search term.
    }

    /**
     * Loader Callbacks
     */

    /**
     * The LoaderManager calls this method when the loader is created.
     *
     * @param id ID integer to id   entify the instance of the loader.
     * @param args The bundle that contains the search parameter.
     * @return Returns a new BookLoader containing the search term.
     */
    override fun onCreateLoader(id: Int, args: Bundle): Loader<String> {
        return BookLoader(this, args.getString("queryString")!!)
    }

    /**
     * Called when the data has been loaded. Gets the desired information from
     * the JSON and updates the Views.
     *
     * @param loader The loader that has finished.
     * @param data The JSON response from the Books API.
     */
    override fun onLoadFinished(loader: Loader<String>, data: String) {
        try {
            // Convert the response into a JSON object.
            val jsonObject = JSONObject(data)
            // Get the JSONArray of book items.
            val itemsArray = jsonObject.getJSONArray("items")

            // Initialize iterator and results fields.
            var i = 0
            var title: String? = null
            var authors: String? = null

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || authors == null && title == null) {
                // Get the current item information.
                val book = itemsArray.getJSONObject(i)
                val volumeInfo = book.getJSONObject("volumeInfo")

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title")
                    authors = volumeInfo.getString("authors")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Move to the next item.
                i++
            }

            // If both are found, display the result.
            if (title != null && authors != null) {
                mTitleText!!.text = title
                mAuthorText!!.text = authors
                mBookInput!!.setText("")
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText!!.setText(R.string.no_results)
                mAuthorText!!.text = ""
            }

        } catch (e: Exception) {
            // If onPostExecute does not receive a proper JSON string, update the UI to show failed results.
            mTitleText!!.setText(R.string.no_results)
            mAuthorText!!.text = ""
            e.printStackTrace()
        }


    }

    /**
     * In this case there are no variables to clean up when the loader is reset.
     *
     * @param loader The loader that was reset.
     */
    override fun onLoaderReset(loader: Loader<String>) {}
}
