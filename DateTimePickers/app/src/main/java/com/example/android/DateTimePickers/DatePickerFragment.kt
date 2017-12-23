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


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.widget.DatePicker
import java.util.*

/**
 * A simple [Fragment] subclass for the date picker.
 * Sets the current date for the picker using Calendar.
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    /**
     * Creates the date picker dialog with the current date from Calendar.
     * @param savedInstanceState    Saved instance
     * @return DatePickerDialog     The date picker dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    /**
     * Grabs the date and passes it to processDatePickerResult().
     * @param view  The date picker view
     * @param year  The year chosen
     * @param month The month chosen
     * @param day   The day chosen
     */
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Convert the date elements to strings.
        // Set the activity to the Main Activity.
        val activity = activity as MainActivity?
        // Invoke Main Activity's processDatePickerResult() method.
        activity!!.processDatePickerResult(year, month, day)
    }

}
