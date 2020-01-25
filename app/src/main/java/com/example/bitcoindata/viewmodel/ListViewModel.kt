package com.example.bitcoindata.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bitcoindata.model.Price
import com.example.bitcoindata.model.VolleyGet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.io.IOException

private const val TAG = "ListViewModel"

class ListViewModel(application: Application) : BaseViewModel(application) {



    private val fetch = VolleyGet(application)





    val loading = MutableLiveData<Boolean>()
    val bitcoinPrices = MutableLiveData<Price>()

    fun refresh() {
        fetchFromRemote()
    }


    private fun fetchFromRemote() {
        loading.value = true
//        Log.d(TAG, "ListViewModel ${fetch.getPrices()}")
        Log.d(TAG, "ListViewModel ${fetchJson()}")






    }

    fun fetchJson(){

        val url = "https://api.coindesk.com/v1/bpi/historical/close.json"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("${e?.message}")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                fetch.parseJSON(body.toString())

                //editText?.text = "${body.toString()}" \\ or whatever else you wanna set on the edit text
            }
        })

    }



    override fun onCleared() {
        super.onCleared()
    }
}