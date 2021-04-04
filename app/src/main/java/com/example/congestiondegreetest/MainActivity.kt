package com.example.congestiondegreetest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewHolder.ItemClickListener {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var jsonReader : GetInformation = GetInformationGAS(0,textView_grossPeople,RecyclerView_main,this)
        jsonReader.getJson(this)
        Log.i("INFORMATION","run")
    }

    override fun onItemClick(view: View, position: Int) {
    }
}
