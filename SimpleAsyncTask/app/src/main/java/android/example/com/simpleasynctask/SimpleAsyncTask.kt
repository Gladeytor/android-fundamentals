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

import android.os.AsyncTask
import android.widget.TextView

import java.util.Random

/**
 * Performs a very simple background task, in this case, just sleeps!
 */
internal class SimpleAsyncTask// Constructor that provides a reference to the TextView from the MainActivity
(// The TextView where we will show results
        private val textView: TextView) : AsyncTask<Void, Void, String>() {

    /**
     * Runs on the background thread.
     *
     * @param voids No parameters in this use case.
     * @return Returns the string including the amount of time that
     * the background thread slept.
     */
    override fun doInBackground(vararg voids: Void): String {

        // Generate a random number between 0 and 10
        val r = Random()
        val n = r.nextInt(11)

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        val s = n * 200

        // Sleep for the random amount of time
        try {
            Thread.sleep(s.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // Return a String result
        return "Awake at last after sleeping for $s milliseconds!"
    }


    /**
     * Does something with the result on the UI thread; in this case
     * updates the TextView.
     */
    override fun onPostExecute(result: String) {
        textView.text = result
    }
}
