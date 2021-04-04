package com.example.congestiondegreetest

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

@Deprecated("It is sample.So you can't use this class in practice.")
class GetInformationSample(override var allCount: Int) : GetInformation {

    private var jsonPath:String = ""
    private var data : JSONArray? = null
    private var dataList = arrayListOf<CongestionInformationStructure>()


    override fun getJson(context:Context) : Boolean{
        /**
         * @param context ActivityやFragmentで使うときのcontextを入れてください
         */
        jsonPath = "app/src/main/assets/sampleData.json"
        try {
            val assetManager = context.resources.assets //アセット呼び出し
            val inputStream = assetManager.open("sampleData.json") //Jsonファイル
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val str: String = bufferedReader.readText() //データ
            val jsonObject = JSONObject(str)
            if(jsonObject!!.getString("status") == "failed") {
                //Jsonのstatusがfailedならそれはオカシイ
                return false
            }
            allCount = jsonObject!!.getInt("allCount")
            data = jsonObject!!.getJSONArray("data")
        }catch (e:Exception) {
            Log.e("Error","ファイル読み込み失敗")
            return false
        }
        return true
    }

    override fun getAllData() : ArrayList<CongestionInformationStructure>{
        /**
         * getJson()実行する前に動かしたら知らん
         */

        for (i in 0 until data!!.length()) {
            val jsonData = data!!.getJSONObject(i)
            dataList.add(CongestionInformationStructure(jsonData.getString("id"),jsonData.getInt("maxCount"),jsonData.getInt("count")))
        }
        return dataList
    }
}