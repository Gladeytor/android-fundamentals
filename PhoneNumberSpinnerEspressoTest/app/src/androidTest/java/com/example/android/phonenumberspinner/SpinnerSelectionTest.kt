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

package com.example.android.phonenumberspinner

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.`is`

/**
 * PhoneNumberSpinner is an app that shows a spinner, with the id label_spinner,
 * for choosing the label of a phone number (Home, Work, Mobile, and Other).
 * The app displays the choice in a text field, concatenated with the entered phone number.
 * The goal of this test is to open the spinner, click each item,
 * and then verify that the TextView text_phonelabel contains the item.
 * The test demonstrates that the code retrieving the spinner selection
 * is working properly, and the code displaying the text of the spinner item
 * is also working properly.
 */
@RunWith(AndroidJUnit4::class)
class SpinnerSelectionTest {

    @Rule
    var mActivityRule: ActivityTestRule<*> = ActivityTestRule(
            MainActivity::class.java)

    @Test
    fun iterateSpinnerItems() {
        val myArray = mActivityRule.activity.resources
                .getStringArray(R.array.labels_array)

        // Iterate through the spinner array of items.
        val size = myArray.size
        for (i in 0 until size) {
            // Find the spinner and click on it.
            onView(withId(R.id.label_spinner)).perform(click())
            // Find the spinner item and click on it.
            onData(`is`(myArray[i])).perform(click())
            // Find the Submit button and click on it.
            onView(withId(R.id.button_main)).perform(click())
            // Find the text view and check that the spinner item
            // is part of the string.
            onView(withId(R.id.text_phonelabel))
                    .check(matches(withText(containsString(myArray[i]))))
        }
    }
}
