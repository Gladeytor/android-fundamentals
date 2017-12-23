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

package com.example.android.materialme

import android.content.Context
import android.content.Intent
import android.support.annotation.DrawableRes

/**
 * Data model for each row of the RecyclerView.
 */
internal class Sport
/**
 * Constructor for the Sport class data model
 * @param title The name if the sport.
 * @param info Information about the sport.
 * @param imageResource The resource for the sport image
 */
(//Member variables representing the title, image and information about the sport
        /**
         * Gets the title of the sport
         * @return The title of the sport.
         */
        val title: String,
        /**
         * Gets the info about the sport
         * @return The info about the sport.
         */
        val info: String,
        /**
         * Gets the resource for the image
         * @return The resource for the image
         */
        val imageResource: Int) {
    companion object {

        val TITLE_KEY = "Title"
        val IMAGE_KEY = "Image Resource"

        /**
         * Method for creating  the Detail Intent
         * @param context Application context, used for launching the Intent
         * @param title The title of the current Sport
         * @param imageResId The image resource associated with the current sport
         * @return The Intent containing the extras about the sport, launches Detail activity
         */
        fun starter(context: Context, title: String, @DrawableRes imageResId: Int): Intent {
            val detailIntent = Intent(context, DetailActivity::class.java)
            detailIntent.putExtra(TITLE_KEY, title)
            detailIntent.putExtra(IMAGE_KEY, imageResId)
            return detailIntent
        }
    }
}
