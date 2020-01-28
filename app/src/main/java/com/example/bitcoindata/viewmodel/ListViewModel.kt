package com.example.bitcoindata.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bitcoindata.model.Price
import com.example.bitcoindata.model.PriceDatabase
import com.example.bitcoindata.model.VolleyGet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

private const val TAG = "ListViewModel"

class ListViewModel(application: Application) : BaseViewModel(application) {



    private val fetch = VolleyGet(application)
    private val disposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val bitcoinPrices = MutableLiveData<List<Price>>()

    fun refresh() {
        fetchFromRemote()
    }

    fun getPrices(): List<Price>? {
        var prices = bitcoinPrices.value
//        for (p in bitcoinPrices.value!!){
//            Log.d(TAG,"${p.amount}")
//            prices.add(p)
//        }

        prices = bitcoinPrices.value
        return prices

    }


    private fun fetchFromRemote() {
        loading.value = true
        fetchJson()
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
                val list = fetch.parseJSON(body.toString())


               GlobalScope.launch(Dispatchers.Main) {
                    //bitcoinPrices.value = list
                   storePricesLocally(list)
                }

               Log.d(TAG, " ${bitcoinPrices.value}")
            }
        })

    }

    private fun storePricesLocally1(list: List<Price>) {
        launch {
            val dao = PriceDatabase(getApplication()).priceDao()
            dao.deleteAllPrices()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            pricesRetrieved(list)
        }
    }

    private fun storePricesLocally(list: List<Price>) {
        launch {
            val dao = PriceDatabase(getApplication()).priceDao()

           
            for (p in list){
                val result = dao.getPrice1(p.date!!)
                if (result.date != null){
                    Log.d(TAG, result.date)
                }
            }


        }
    }

    private fun pricesRetrieved(priceList: List<Price>) {
        bitcoinPrices.value = priceList
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}