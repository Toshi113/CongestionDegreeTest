package com.example.congestiondegreetest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val teamNameTextView: TextView = view.findViewById(R.id.textView_teamName)
    val placeTextView: TextView = view.findViewById(R.id.textView_place)
    val percentageTextView: TextView = view.findViewById(R.id.textView_percentage)
    val countTextView: TextView = view.findViewById(R.id.textView_count)
    val maxCountTextView: TextView = view.findViewById(R.id.textView_maxCount)
}