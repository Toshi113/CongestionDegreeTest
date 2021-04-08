package com.example.congestiondegreetest

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.util.*

@Deprecated("use class:GetInformation() in  main thread instead.")
class GetInformationGAS(override var allCount: Int, var textView: TextView, var recyclerView: RecyclerView,var context: Context) : GetInformation {

    private val urlGAS: String =
        "https://script.google.com/macros/s/AKfycbwAGmpDO_mcI05IC8d6y39Eyu1ndIMUHMSsx1zAqFJnthVgXz77PuRXfq0qW4d9LtPkOA/exec"
    private val jsonMessage: String = "{\"function\": \"init\"}"


    private var data: JSONArray? = null
    private var dataList = arrayListOf<CongestionInformationStructure>()

    override fun getJson(context: Context): Boolean {
        /**
         * @param context ActivityやFragmentで使うときのcontextを入れてください
         */

        try {
            HttpAsyncTask().execute()
        } catch (e: Exception) {
            Log.e("INFORMATION", e.toString())
            return false
        }
        return true
    }

    override fun getAllData(): ArrayList<CongestionInformationStructure> {
        /**
         * getJson()実行する前に動かしたら知らん
         */
        for (i in 0 until data!!.length()) {
            val jsonData = data!!.getJSONObject(i)
            dataList.add(
                CongestionInformationStructure(
                    jsonData.getString("id"),
                    jsonData.getInt("maxCount"),
                    jsonData.getInt("count")
                )
            )
        }
        return dataList
    }

    inner class HttpAsyncTask() :
        AsyncTask<Void, Void, String>(), RecyclerViewHolder.ItemClickListener {

        override fun doInBackground(vararg p0: Void?): String? {
            Log.i("INFORMATION", jsonMessage)
            Log.i("INFORMATION", urlGAS)
            // ここからPOST処理
            val mediaTypeJson =
                MediaType.parse("application/json; charset=utf-8")
            val requestBody = RequestBody.create(
                mediaTypeJson,
                jsonMessage
            )
            val request = Request.Builder()
                .url(urlGAS).post(requestBody).build()
            val client = OkHttpClient.Builder().build()
            val response = client.newCall(request).execute()
            return response.body()!!.string().toString()
        }

        @SuppressLint("SetTextI18n")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i("INFORMATION2", "result:$result")
            val jsonObject = JSONObject(result)
            allCount = jsonObject.getString("allCount").toInt()
            data = jsonObject.getJSONArray("data")

            textView.text = "校内合計来場者:${allCount}(人)"
            recyclerView.adapter = RecyclerAdapter(context, this, getAllData())
            recyclerView.layoutManager = GridLayoutManager(context,2)
        }

        override fun onItemClick(view: View, position: Int) {
            Toast.makeText(
                context,
                "クリックされました",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}