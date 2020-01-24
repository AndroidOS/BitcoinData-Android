package com.example.bitcoindata.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.bitcoindata.model.Price
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "ListViewModel"

class ListViewModel(application: Application) : BaseViewModel(application) {



    private val disposable = CompositeDisposable()



    val loading = MutableLiveData<Boolean>()
    val bitcoinPrices = MutableLiveData<Price>()

    fun refresh() {
        fetchFromRemote()
    }


    private fun fetchFromRemote() {
        loading.value = true




    }


    override fun onCleared() {
        super.onCleared()
    }
}