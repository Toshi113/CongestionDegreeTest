package com.example.congestiondegreetest

import android.content.Context

import java.util.ArrayList

interface GetInformation{
    var allCount : Int
    fun getJson(context: Context) : Boolean
    fun getAllData() : ArrayList<CongestionInformationStructure>
}