package com.example.bitcoindata.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


const val TAG = "VolleyGet"
class VolleyGet(val context: Context) {

    fun getPrices() {
        //Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.coindesk.com/v1/bpi/historical/close.json"
        var data: String?

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                parseJSON(response.toString())
//                val bitcoinPrices = "Response is: ${response.substring(0, 500)}"
//                //Log.d(TAG, "$bitcoinPrices")
            },
            Response.ErrorListener { "That didn't work! " })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun parseJSON(data: String){

        val jsonObject = JSONObject(data)

        val prices = jsonObject.getJSONObject("bpi")
        val pricesKeys = prices.keys()


        Log.d(TAG, "${prices}")

        while (pricesKeys.hasNext()) {
            val key: String = pricesKeys.next()

            val price = prices[key] as Double
            Log.d(TAG, "${price}")
        }




    }
}