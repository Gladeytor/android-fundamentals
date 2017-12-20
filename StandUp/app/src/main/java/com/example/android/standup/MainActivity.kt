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
package com.example.android.standup

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mNotificationManager: NotificationManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //Set up the Notification Broadcast Intent
        val notifyIntent = Intent(this, AlarmReceiver::class.java)

        //Check if the Alarm is already set, and check the toggle accordingly
        val alarmUp = PendingIntent.getBroadcast(this, 0, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null

        alarmToggle.isChecked = alarmUp

        //Set up the PendingIntent for the AlarmManager
        val notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)



        alarmToggle.setOnCheckedChangeListener { compoundButton, isChecked ->
            val toastMessage: String
            if (isChecked) {

                val triggerTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES

                val repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES

                //If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime, repeatInterval, notifyPendingIntent)

                //Set the toast message for the "on" case
                toastMessage = getString(R.string.alarm_on_toast)
            } else {
                //Cancel the alarm and notification if the alarm is turned off
                alarmManager.cancel(notifyPendingIntent)
                mNotificationManager!!.cancelAll()

                //Set the toast message for the "off" case
                toastMessage = getString(R.string.alarm_off_toast)
            }

            //Show a toast to say the alarm is turned on or off
            Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_SHORT)
                    .show()
        }


    }

    companion object {

        private val NOTIFICATION_ID = 0
    }
}
