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

package com.example.android.notificationscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity for the Notification Scheduler app, which explores the JobScheduler API
 */
class MainActivity : AppCompatActivity() {

    private var mScheduler: JobScheduler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scheduleJobButton = findViewById<View>(R.id.scheduleJobButton) as Button
        val cancelJobButton = findViewById<View>(R.id.cancelJobsButton) as Button

        val label = findViewById<View>(R.id.seekBarLabel) as TextView
        val seekBarProgress = findViewById<View>(R.id.seekBarProgress) as TextView


        //Switch that toggles between periodic tasks and tasks with single deadlines
        periodicSwitch!!.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                label.setText(R.string.periodic_interval)
            } else {
                label.setText(R.string.override_deadline)
            }
        }

        //Updates the TextView with the value from the seekbar
        seekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, userSet: Boolean) {
                if (progress > 0) {
                    val progressLabel = getString(R.string.seekbar_label, progress)
                    seekBarProgress.text = progressLabel
                } else {
                    seekBarProgress.setText(R.string.not_set)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // OnClickListener that sets the job.
        scheduleJobButton.setOnClickListener { scheduleJob() }

        //OnClickListener that cancels all existing jobs.
        cancelJobButton.setOnClickListener { cancelJobs() }
    }


    /**
     * onClick method that schedules the jobs based on the parameters set
     */
    private fun scheduleJob() {
        mScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        val networkOptions = findViewById<View>(R.id.networkOptions) as RadioGroup

        val selectedNetworkID = networkOptions.checkedRadioButtonId

        var selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
        when (selectedNetworkID) {
            R.id.noNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED
        }

        val serviceName = ComponentName(packageName,
                NotificationJobService::class.java.name)
        val builder = JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(idleSwitch!!.isChecked)
                .setRequiresCharging(chargingSwitch!!.isChecked)

        val seekBarInteger = seekBar!!.progress
        val seekBarSet = seekBarInteger > 0

        //Set the job parameters based on the periodic switch.
        if (periodicSwitch!!.isChecked) {
            if (seekBarSet) {
                builder.setPeriodic((seekBarInteger * 1000).toLong())
            } else {
                Toast.makeText(this@MainActivity, R.string.no_interval_toast,
                        Toast.LENGTH_SHORT).show()
            }
        } else {
            if (seekBarSet) {
                builder.setOverrideDeadline((seekBarInteger * 1000).toLong())
            }
        }

        val constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE
                || chargingSwitch!!.isChecked || idleSwitch!!.isChecked
                || seekBarSet)

        if (constraintSet) {
            //Schedule the job and notify the user
            val myJobInfo = builder.build()
            mScheduler!!.schedule(myJobInfo)
            Toast.makeText(this, R.string.job_scheduled, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.no_constraint_toast, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * onClick method for cancelling all existing jobs
     */
    private fun cancelJobs() {
        if (mScheduler != null) {
            mScheduler!!.cancelAll()
            mScheduler = null
            Toast.makeText(this, R.string.jobs_canceled, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        private val JOB_ID = 0
    }
}
