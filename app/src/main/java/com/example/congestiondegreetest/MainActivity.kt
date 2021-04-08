package com.example.congestiondegreetest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), RecyclerViewHolder.ItemClickListener {

    private val url : String = "https://script.google.com/macros/s/AKfycbwAGmpDO_mcI05IC8d6y39Eyu1ndIMUHMSsx1zAqFJnthVgXz77PuRXfq0qW4d9LtPkOA/exec"
    private val jsonMessage: String = "{\"function\": \"init\"}"

    //　1分*60秒/分*1000ミリ秒/秒
    private val interval : Long = 1 * 60 * 1000

    private val horizontalCount = 2

    private var handler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reload, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_reload -> {
            makeUI()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


    @SuppressLint("SetTextI18n")
    private fun makeUI() {
        val gThread = GetInformationThread(url,jsonMessage)
        gThread.start()
        gThread.join()
        textView_grossPeople.text = "校内合計来場者:${gThread.allCount}(人)"
        RecyclerView_main.adapter = RecyclerAdapter(this, this, gThread.dataList)
        RecyclerView_main.layoutManager = GridLayoutManager(this,horizontalCount)
        Log.i("INFORMATION","更新されました")
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this,"クリックされました",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                makeUI()
                handler!!.postDelayed(this,interval)
            }
        }
        handler!!.post(runnable!!)
    }

    override fun onPause() {
        super.onPause()
        handler!!.removeCallbacks(runnable!!)
    }
}
