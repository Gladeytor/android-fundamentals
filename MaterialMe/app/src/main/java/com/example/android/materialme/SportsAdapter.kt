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
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

/***
 * The adapter class for the RecyclerView, contains the sports data
 */
internal class SportsAdapter
/**
 * Constructor that passes in the sports data and the context
 * @param sportsData ArrayList containing the sports data
 * @param context Context of the application
 */
(private val mContext: Context, private val mSportsData: ArrayList<Sport>) : RecyclerView.Adapter<SportsAdapter.SportsViewHolder>() {

    //Member variables
    private val mGradientDrawable: GradientDrawable = GradientDrawable()

    init {

        //Prepare gray placeholder
        mGradientDrawable.setColor(Color.GRAY)

        //Make the placeholder same size as the images
        val drawable = ContextCompat.getDrawable(mContext, R.drawable.img_badminton)
        if (drawable != null) {
            mGradientDrawable.setSize(drawable.intrinsicWidth, drawable.intrinsicHeight)
        }
    }


    /**
     * Required method for creating the viewholder objects.
     * @param parent The ViewGroup into which the new View is added after it is
     * bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly create SportsViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        return SportsViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false), mGradientDrawable)
    }

    /**
     * Required method that binds the data to the viewholder.
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {

        //Get the current sport
        val currentSport = mSportsData[position]

        //Bind the data to the views
        holder.bindTo(currentSport)

    }


    /**
     * Required method for determining the size of the data set.
     * @return Size of the data set.
     */
    override fun getItemCount(): Int {
        return mSportsData.size
    }


    /**
     * SportsViewHolder class that represents each row of data in the RecyclerView
     */
    internal class SportsViewHolder
    /**
     * Constructor for the SportsViewHolder, used in onCreateViewHolder().
     * @param itemView The rootview of the list_item.xml layout file
     */
    (private val mContext: Context, itemView: View, private val mGradientDrawable: GradientDrawable) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        //Member Variables for the holder data
        private val mTitleText: TextView = itemView.findViewById<View>(R.id.title) as TextView
        private val mInfoText: TextView = itemView.findViewById<View>(R.id.subTitle) as TextView
        private val mSportsImage: ImageView = itemView.findViewById<View>(R.id.sportsImage) as ImageView
        private var mCurrentSport: Sport? = null

        init {

            //Initialize the views

            //Set the OnClickListener to the whole view
            itemView.setOnClickListener(this)
        }

        fun bindTo(currentSport: Sport) {
            //Populate the textviews with data
            mTitleText.text = currentSport.title
            mInfoText.text = currentSport.info

            //Get the current sport
            mCurrentSport = currentSport


            //Load the images into the ImageView using the Glide library
            Glide.with(mContext).load(currentSport.imageResource).placeholder(mGradientDrawable).into(mSportsImage)
        }

        override fun onClick(view: View) {

            //Set up the detail intent
            val detailIntent = Sport.starter(mContext, mCurrentSport!!.title,
                    mCurrentSport!!.imageResource)


            //Start the detail activity
            mContext.startActivity(detailIntent)
        }
    }
}
