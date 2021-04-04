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

    private val url : String = "https://script.google.com/macros/s/AKfycbwAGmpDO_mcI05IC8d6y39Eyu1ndIMUHMSsx1zAqFJnthVgXz77PuRXfq0qW4d9LtPkOA/exec"
    private val jsonMessage: String = "{\"function\": \"init\"}"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gThread = GetInformationThread(url,jsonMessage)
        gThread.start()
        gThread.join()
        textView_grossPeople.text = "校内合計来場者:${gThread.allCount}(人)"
        RecyclerView_main.adapter = RecyclerAdapter(this, this, gThread.dataList)
        RecyclerView_main.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        Log.i("INFORMATION","run")
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this,"クリックされました",Toast.LENGTH_SHORT).show()
    }
}
