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


package com.example.android.DateTimePickers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

/**
 * This app shows the time and date pickers when you click the appropriate button.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Creates the view based on the layout for the main activity.
     * @param savedInstanceState    Saved instance
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, getString(R.string.date_picker))
    }

    fun showTimePickerDialog(view: View) {
        val newFragment = TimePickerFragment()
        newFragment.show(supportFragmentManager, getString(R.string.time_picker))
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int) {
        // The month integer returned by the date picker starts counting at 0
        // for January, so you need to add 1 to show months starting at 1.
        val month_string = Integer.toString(month + 1)
        val day_string = Integer.toString(day)
        val year_string = Integer.toString(year)
        // Assign the concatenated strings to dateMessage.
        val dateMessage = "$month_string/$day_string/$year_string"
        Toast.makeText(this, getString(R.string.date) + dateMessage, Toast.LENGTH_SHORT).show()
    }

    fun processTimePickerResult(hourOfDay: Int, minute: Int) {
        // Convert time elements into strings.
        val hour_string = Integer.toString(hourOfDay)
        val minute_string = Integer.toString(minute)
        // Assign the concatenated strings to timeMessage.
        val timeMessage = hour_string + ":" + minute_string
        Toast.makeText(this, getString(R.string.time) + timeMessage, Toast.LENGTH_SHORT).show()
    }

}
