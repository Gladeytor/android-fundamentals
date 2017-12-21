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

package com.android.example.wordlistsql

import android.view.View

/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
open class MyButtonOnClickListener(internal var id: Int, internal var word: String?) : View.OnClickListener {

    override fun onClick(v: View) {
        // Implemented in WordListAdapter
    }

    companion object {
        private val TAG = View.OnClickListener::class.java.simpleName
    }
}
