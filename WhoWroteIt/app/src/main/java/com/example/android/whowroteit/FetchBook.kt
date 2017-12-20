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

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.EditText
import android.widget.TextView

import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * AsyncTask implementation that opens a network connection and
 * query's the Book Service API.
 */
class FetchBook// Constructor providing a reference to the views in MainActivity
(private val mTitleText: TextView, private val mAuthorText: TextView, // Variables for the search input field, and results TextViews
 private val mBookInput: EditText) : AsyncTask<String, Void, String>() {


    /**
     * Makes the Books API call off of the UI thread.
     *
     * @param params String array containing the search data.
     * @return Returns the JSON string from the Books API or
     * null if the connection failed.
     */
    override fun doInBackground(vararg params: String): String? {

        // Get the search string
        val queryString = params[0]


        // Set up variables for the try block that need to be closed in the finally block.
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var bookJSONString: String? = null

        // Attempt to query the Books API.
        try {
            // Base URI for the Books API.
            val BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"

            val QUERY_PARAM = "q" // Parameter for the search string.
            val MAX_RESULTS = "maxResults" // Parameter that limits search results.
            val PRINT_TYPE = "printType" // Parameter to filter by print type.

            // Build up your query URI, limiting results to 10 items and printed books.
            val builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build()

            val requestURL = URL(builtURI.toString())

            // Open the network connection.
            urlConnection = requestURL.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            // Get the InputStream.
            val inputStream = urlConnection.inputStream

            // Read the response string into a StringBuilder.
            val builder = StringBuilder()

            reader = BufferedReader(InputStreamReader(inputStream))

            var line: String
            while ((reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                line = reader.readLine()
                builder.append(line + "\n")
            }

            if (builder.isEmpty()) {
                // Stream was empty.  No point in parsing.
                // return null;
                return null
            }
            bookJSONString = builder.toString()

            // Catch errors.
        } catch (e: IOException) {
            Log.e(LOG_TAG, e.toString())

            // Close the connections.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect()
            }
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    Log.e(LOG_TAG, e.toString())
                }

            }
        }

        // Return the raw response.
        return bookJSONString
    }

    /**
     * Handles the results on the UI thread. Gets the information from
     * the JSON and updates the Views.
     *
     * @param s Result from the doInBackground method containing the raw JSON response,
     * or null if it failed.
     */
    override fun onPostExecute(s: String) {
        super.onPostExecute(s)
        try {
            // Convert the response into a JSON object.
            val jsonObject = JSONObject(s)
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
                    Log.e(LOG_TAG, e.toString())
                }

                // Move to the next item.
                i++
            }

            // If both are found, display the result.
            if (title != null && authors != null) {
                mTitleText.text = title
                mAuthorText.text = authors
                mBookInput.setText("")
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText(R.string.no_results)
                mAuthorText.text = ""
            }

        } catch (e: Exception) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText(R.string.no_results)
            mAuthorText.text = ""
            Log.e(LOG_TAG, e.toString())
        }

    }

    companion object {

        // Class name for Log tag
        private val LOG_TAG = FetchBook::class.java.simpleName
    }
}
