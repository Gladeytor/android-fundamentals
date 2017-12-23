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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/***
 * Main Activity for the Material Me app, a mock sports news application
 */
class MainActivity : AppCompatActivity() {

    //Member variables
//    private var mRecyclerView: RecyclerView? = null
    private var mSportsData: ArrayList<Sport>? = null
    private var mAdapter: SportsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set the Layout Manager
        recyclerView!!.layoutManager = LinearLayoutManager(this)


        //Initialize the ArrayList that will contain the data
        mSportsData = ArrayList()

        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = SportsAdapter(this, mSportsData!!)
        recyclerView!!.adapter = mAdapter

        //Get the data
        initializeData()

        //Helper class for creating swipe to dismiss and drag and drop functionality
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN
                or ItemTouchHelper.UP, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            /**
             * Method that defines the drag and drop functionality
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the original one with.
             * @return returns true if the item was moved, false otherwise
             */
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {

                //Get the from and to position
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition

                //Swap the items and notify the adapter
                Collections.swap(mSportsData!!, from, to)
                mAdapter!!.notifyItemMoved(from, to)
                return true
            }

            /**
             * Method that defines the swipe to dismiss functionality
             * @param viewHolder The viewholder being swiped
             * @param direction The direction it is swiped in
             */
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //Remove the item from the dataset
                mSportsData!!.removeAt(viewHolder.adapterPosition)

                //Notify the adapter
                mAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })

        //Attach the helper to the RecyclerView
        helper.attachToRecyclerView(recyclerView)
    }

    /**
     * Method for initializing the sports data from resources.
     */
    private fun initializeData() {
        //Get the resources from the XML file
        val sportsList = resources.getStringArray(R.array.sports_titles)
        val sportsInfo = resources.getStringArray(R.array.sports_info)
        val sportsImageResources = resources.obtainTypedArray(R.array.sports_images)
        //Clear the existing data (to avoid duplication)
        mSportsData!!.clear()


        //Create the ArrayList of Sports objects with the titles, images
        // and information about each sport
        for (i in sportsList.indices) {
            mSportsData!!.add(Sport(sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)))
        }

        //Recycle the typed array
        sportsImageResources.recycle()

        //Notify the adapter of the change
        mAdapter!!.notifyDataSetChanged()
    }

    /**
     * onClick method for th FAB that resets the data
     * @param view The button view that was clicked
     */
    fun resetSports(view: View) {
        initializeData()
    }
}
