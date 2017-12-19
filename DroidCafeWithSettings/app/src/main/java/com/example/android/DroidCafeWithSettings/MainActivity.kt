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

package com.example.android.DroidCafeWithSettings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This app shows food images the user can tap, which displays a toast message
 * showing which food item was chosen and launches a second activity.
 * The second activity lets the user choose a delivery method using radio buttons,
 * and displays a Toast message showing the choice. This app is a demo of
 * the options menu, clickable images, and radio buttons.
 *
 * The app has been enhanced to include the Settings Activity template
 * with a custom setting (Account: Market) and the other settings from the template.
 */

class MainActivity : AppCompatActivity() {

    /**
     * Creates the content view, the toolbar, and
     * the floating action button.
     * This method is provided in the Basic Activity template.
     *
     * @param savedInstanceState Saved instance.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { displayMap() }

        // Sets default values only once, first time this is called.
        // The third argument is a boolean that indicates whether the default values
        // should be set more than once. When false, the system sets the default values
        // only if this method has never been called in the past.
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_notification, false)
        PreferenceManager.setDefaultValues(this, R.xml.pref_data_sync, false)

        // Read the Account "market" setting and display a toast message.
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val marketPref = sharedPref.getString("sync_frequency", "-1")
        val switchPref = sharedPref.getBoolean("example_switch", false)
        val message = getString(R.string.market_message) + marketPref +
                getString(R.string.recommend_message) + switchPref
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Inflates the menu, and adds items to the action bar if it is present.
     *
     * @param menu Menu to inflate.
     * @return Returns true if the menu inflated.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handles app bar item clicks.
     *
     * @param item Item clicked.
     * @return True if one of the defined items was clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle app bar item clicks here. The app bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_order -> {
                displayToast(getString(R.string.action_order_message))
                return true
            }
            R.id.action_status -> {
                displayToast(getString(R.string.action_status_message))
                return true
            }
            R.id.action_favorites -> {
                displayToast(getString(R.string.action_favorites_message))
                return true
            }
            R.id.action_contact -> {
                displayToast(getString(R.string.action_contact_message))
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }// Do nothing
        return super.onOptionsItemSelected(item)
    }

    /**
     * Shows a message that the donut image was clicked.
     */
    fun showDonutOrder(view: View) {
        showFoodOrder(getString(R.string.donut_order_message))
    }

    /**
     * Shows a message that the ice cream sandwich image was clicked.
     */
    fun showIceCreamOrder(view: View) {
        showFoodOrder(getString(R.string.ice_cream_order_message))
    }

    /**
     * Shows a message that the froyo image was clicked.
     */
    fun showFroyoOrder(view: View) {
        showFoodOrder(getString(R.string.froyo_order_message))
    }

    /**
     * Displays a Toast message for the food order and starts the OrderActivity activity.
     *
     * @param message Message to display.
     */
    fun showFoodOrder(message: String) {
        displayToast(message)
        val intent = Intent(this, OrderActivity::class.java)
        startActivity(intent)
    }

    /**
     * Displays a Toast message.
     *
     * @param message Message to display.
     */
    fun displayToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun displayMap() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        // Using the coordinates for Google headquarters.
        val data = getString(R.string.google_mtv_coord_zoom12)
        intent.data = Uri.parse(data)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
