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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.wordlist_item.view.*

/**
 * Implements a simple Adapter for a RecyclerView.
 * Demonstrates how to add a click handler for each item in the ViewHolder.
 */
class WordListAdapter(internal var mContext: Context, internal var mDB: WordListOpenHelper) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * Custom view holder with a text view and two buttons.
     */
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView = itemView.word!!
        var delete_button = itemView.delete_button
        var edit_button = itemView.edit_button


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = mInflater.inflate(R.layout.wordlist_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = mDB.query(position)
        holder.wordItemView.text = current.word
        // Keep a reference to the view holder for the click listener
        val h = holder // needs to be final for use in callback
        // Attach a click listener to the DELETE button.
        holder.delete_button.setOnClickListener(object : MyButtonOnClickListener(
                current.id, null) {


            override fun onClick(v: View) {
                // You have to get the position like this, you can't hold a reference
                Log.d(TAG + "onClick", "VHPos " + h.adapterPosition + " ID " + id)
                val deleted = mDB.delete(id)
                if (deleted >= 0)
                    notifyItemRemoved(h.adapterPosition)
            }
        })

        holder.edit_button.setOnClickListener(object : MyButtonOnClickListener(
                current.id, current.word!!) {

            override fun onClick(v: View) {
                val intent = Intent(mContext, EditWordActivity::class.java)

                intent.putExtra(EXTRA_ID, id)
                intent.putExtra(EXTRA_POSITION, h.adapterPosition)
                intent.putExtra(EXTRA_WORD, word)

                // Start an empty edit activity.
                (mContext as Activity).startActivityForResult(intent, MainActivity.WORD_EDIT)
            }
        })
    }

    override fun getItemCount(): Int {
        return mDB.count().toInt()
    }

    companion object {

        private val TAG = WordListAdapter::class.java.simpleName

        val EXTRA_ID = "ID"
        val EXTRA_WORD = "WORD"
        val EXTRA_POSITION = "POSITION"
    }
}


