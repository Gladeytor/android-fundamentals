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

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class WordListOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private var mWritableDB: SQLiteDatabase? = null
    private var mReadableDB: SQLiteDatabase? = null

    init {
        Log.d(TAG, "Construct WordListOpenHelper")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(WORD_LIST_TABLE_CREATE)
        fillDatabaseWithData(db)
        // We cannot initialize mWritableDB and mReadableDB here, because this creates an infinite
        // loop of on Create being repeatedly called.
    }

    /**
     * Adds the initial data set to the database.
     * According to the docs, onCreate for the open helper does not run on the UI thread.
     *
     * @param db Database to fill with data since the member variables are not initialized yet.
     */
    fun fillDatabaseWithData(db: SQLiteDatabase) {

        val words = arrayOf("Android", "Adapter", "ListView", "AsyncTask", "Android Studio", "SQLiteDatabase", "SQLOpenHelper", "Data model", "ViewHolder", "Android Performance", "OnClickListener")

        // Create a container for the data.
        val values = ContentValues()

        for (i in words.indices) {
            // Put column/value pairs into the container. put() overwrites existing values.
            values.put(KEY_WORD, words[i])
            db.insert(WORD_LIST_TABLE, null, values)
        }
    }

    /**
     * Queries the database for an entry at a given position.
     *
     * @param position The Nth row in the table.
     * @return a WordItem with the requested database entry.
     */
    fun query(position: Int): WordItem {
        val query = "SELECT  * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + position + ",1"

        var cursor: Cursor? = null
        val entry = WordItem()

        try {
            if (mReadableDB == null) {
                mReadableDB = readableDatabase
            }
            cursor = mReadableDB!!.rawQuery(query, null)
            cursor!!.moveToFirst()
            entry.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            entry.word = cursor.getString(cursor.getColumnIndex(KEY_WORD))
        } catch (e: Exception) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.message)
        } finally {
            // Must close cursor and db now that we are done with it.
            cursor!!.close()
            return entry
        }
    }

    /**
     * Gets the number of rows in the word list table.
     *
     * @return The number of entries in WORD_LIST_TABLE.
     */
    fun count(): Long {
        if (mReadableDB == null) {
            mReadableDB = readableDatabase
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE)
    }

    /**
     * Adds a single word row/entry to the database.
     *
     * @param  word New word.
     * @return The id of the inserted word.
     */
    fun insert(word: String): Long {
        var newId: Long = 0
        val values = ContentValues()
        values.put(KEY_WORD, word)
        try {
            if (mWritableDB == null) {
                mWritableDB = writableDatabase
            }
            newId = mWritableDB!!.insert(WORD_LIST_TABLE, null, values)
        } catch (e: Exception) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.message)
        }

        return newId
    }

    /**
     * Updates the word with the supplied id to the supplied value.
     *
     * @param id Id of the word to update.
     * @param word The new value of the word.
     * @return The number of rows affected or -1 of nothing was updated.
     */
    fun update(id: Int, word: String): Int {
        var mNumberOfRowsUpdated = -1
        try {
            if (mWritableDB == null) {
                mWritableDB = writableDatabase
            }
            val values = ContentValues()
            values.put(KEY_WORD, word)

            mNumberOfRowsUpdated = mWritableDB!!.update(WORD_LIST_TABLE, //table to change
                    values, // new values to insert
                    KEY_ID + " = ?", // selection criteria for row (in this case, the _id column)
                    arrayOf(id.toString())) //selection args; the actual value of the id

        } catch (e: Exception) {
            Log.d(TAG, "UPDATE EXCEPTION! " + e.message)
        }

        return mNumberOfRowsUpdated
    }

    /**
     * Deletes one entry identified by its id.
     *
     * @param id ID of the entry to delete.
     * @return The number of rows deleted. Since we are deleting by id, this should be 0 or 1.
     */
    fun delete(id: Int): Int {
        var deleted = 0
        try {
            if (mWritableDB == null) {
                mWritableDB = writableDatabase
            }
            deleted = mWritableDB!!.delete(WORD_LIST_TABLE, //table name
                    KEY_ID + " = ? ", arrayOf(id.toString()))
        } catch (e: Exception) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.message)
        }

        return deleted
    }

    /**
     * Called when a database needs to be upgraded. The most basic version of this method drops
     * the tables, and then recreates them. All data is lost, which is why for a production app,
     * you want to back up your data first. If this method fails, changes are rolled back.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(WordListOpenHelper::class.java.name,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data")
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE)
        onCreate(db)
    }

    companion object {

        private val TAG = WordListOpenHelper::class.java.simpleName

        // Declaring all these as constants makes code a lot more readable, and looking like SQL.

        // Versions has to be 1 first time or app will crash.
        private val DATABASE_VERSION = 1
        private val WORD_LIST_TABLE = "word_entries"
        private val DATABASE_NAME = "wordlist"

        // Column names...
        val KEY_ID = "_id"
        val KEY_WORD = "word"

        // ... and a string array of columns.
        private val COLUMNS = arrayOf(KEY_ID, KEY_WORD)

        // Build the SQL query that creates the table.
        private val WORD_LIST_TABLE_CREATE = "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                KEY_ID + " INTEGER PRIMARY KEY, " + // will auto-increment if no value passed

                KEY_WORD + " TEXT );"
    }
}
