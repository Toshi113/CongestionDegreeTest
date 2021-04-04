package com.example.congestiondegreetest

data class CongestionInformationStructure(val id:String,val maxCount:Int,val count:Int) {
    override fun toString(): String {
        return "id:$id,maxCount:$maxCount,count:$count"
    }
}