package com.example.bitcoindata.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


const val TAG = "VolleyGet"
class VolleyGet(val context: Context) {


    fun parseJSON(data: String): List<Price>{

        var pricesParse = mutableListOf<Price>()

        val jsonObject = JSONObject(data)

        val prices = jsonObject.getJSONObject("bpi")
        val pricesKeys = prices.keys()



        while (pricesKeys.hasNext()) {
            val key: String = pricesKeys.next()

            val price = prices[key] as Double
            val newPrice = Price(key, price)
            pricesParse.add(newPrice)
        }

        return pricesParse
    }




}