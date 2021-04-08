package com.example.congestiondegreetest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val context: Context, private val itemClickListener: RecyclerViewHolder.ItemClickListener, private val itemList:List<CongestionInformationStructure>) : RecyclerView.Adapter<RecyclerViewHolder>()  {

    private var mRecyclerView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.let {
            val list = GetTeamNameSample.getTeamDataFromId(itemList[position].id,context)
            it.teamNameTextView.text = list[0]
            it.placeTextView.text = list[1]
            it.countTextView.text = itemList[position].count.toString()
            it.maxCountTextView.text = itemList[position].maxCount.toString() + "(人)"
            it.percentageTextView.text = (itemList[position].count * 100 / itemList[position].maxCount).toInt().toString() + "%"
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.list_item, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return RecyclerViewHolder(mView)
    }
}