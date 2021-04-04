package com.example.congestiondegreetest

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.ArrayList

class GetTeamNameSample {
    companion object {

        fun getTeamDataFromId(id: String,context: Context): ArrayList<String> {
            /**
             * @return index順に団体名、場所、紹介文、アイコン画像のファイル名
             */
            val assetManager = context.resources.assets //アセット呼び出し
            val inputStream = assetManager.open("idTeamName.json") //Jsonファイル
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val str: String = bufferedReader.readText() //データ
            try {
                val jsonObject = JSONObject(str)
                val jsonArray = jsonObject.getJSONArray("Teams")
                for (i in 0 until jsonArray.length()) {
                    val jsonData = jsonArray.getJSONObject(i)
                    if(jsonData.getString("id") == id){
                        return arrayListOf(jsonData.getString("teamName"),jsonData.getString("place"),jsonData.getString("introduceMessage"),jsonData.getString("icoName"))
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return arrayListOf(context.getString(R.string.dummy),context.getString(R.string.dummy),context.getString(R.string.dummy),context.getString(R.string.dummy))
        }
    }
}
