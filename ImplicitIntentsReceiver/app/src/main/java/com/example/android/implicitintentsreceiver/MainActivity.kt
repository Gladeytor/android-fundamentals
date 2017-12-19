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
package com.example.android.implicitintentsreceiver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The ImplicitIntentsReceiver app registers itself for implicit intents that come from browsable
 * links (URLs) with the scheme:http and host:developer.android.com (see AndroidManifest.xml).
 *
 * If it receives that intent, the app just prints the incoming URI to a textview.
 * ImplicitIntentsReceiver is intended to be used in conjunction with the ImplicitIntents app, but
 * will receive a matching implicit intent from any app (for example, a link in an email.)
 */
class MainActivity : AppCompatActivity() {

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = intent
        val uri = intent.data
        if (uri != null) {
            text_uri_message.text = "URI: " + uri.toString()
        }
    }
}
