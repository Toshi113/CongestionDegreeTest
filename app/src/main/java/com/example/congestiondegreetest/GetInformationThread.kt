package com.example.congestiondegreetest

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class GetInformationThread(private val urlToPost:String, private val jsonMessage:String) : Thread(){

    var allCount : Int = 0
    var dataList = arrayListOf<CongestionInformationStructure>()

    override fun run() {
        val mediaTypeJson =
            MediaType.parse("application/json; charset=utf-8")
        val requestBody = RequestBody.create(
            mediaTypeJson,
            jsonMessage
        )
        val request = Request.Builder()
            .url(urlToPost).post(requestBody).build()
        val client = OkHttpClient.Builder().build()
        val response = client.newCall(request).execute()
        val jsonObject = JSONObject(response.body()!!.string())
        allCount = jsonObject.getString("allCount").toInt()
        var data = jsonObject.getJSONArray("data")
        for (i in 0 until data.length()) {
            val jsonData = data.getJSONObject(i)
            dataList.add(
                CongestionInformationStructure(
                    jsonData.getString("id"),
                    jsonData.getInt("maxCount"),
                    jsonData.getInt("count")
                )
            )
        }
    }
}