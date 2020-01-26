package com.example.bitcoindata.view

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bitcoindata.R
import com.example.bitcoindata.model.VolleyGet
import com.example.bitcoindata.viewmodel.ListViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[ListViewModel::class.java]
        viewModel.refresh()

        observeViewModel()

        fab.setOnClickListener { view ->
            val vol = VolleyGet(this)
            vol.getPrices()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun observeViewModel() {
        (viewModel as ListViewModel).bitcoinPrices.observe(this, Observer { prices ->
            prices?.let {
//                txt_temperature.text = "Temperature: " + weather.main?.temp_max.toString()
//                txt_conditions.text = weather.weather?.get(0)?.description.toString()

                var text = ""
                for (price in prices) {
                    text += " " + price.date + " -> " + price.amount + "\n"
                }

                tv_dataView.text = text
            }
        })



    }
}
