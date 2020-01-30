package com.example.bitcoindata.view

import android.content.Intent
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
import com.example.bitcoindata.utils.graphData
import com.example.bitcoindata.viewmodel.ListViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import androidx.appcompat.app.AlertDialog

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
            val intent = Intent(this, GraphActivity::class.java).apply {

            }
            startActivity(intent)

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
            R.id.action_settings -> {
                showDialogue()
                return true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun observeViewModel() {
        (viewModel as ListViewModel).bitcoinPrices.observe(this, Observer { prices ->
            prices?.let {


                var text = ""
                for (price in prices) {
                    graphData.add(price)
                    text += " " + price.date + " -> " + price.amount + "\n"
                }

                tv_dataView.text = text
            }
        })



    }

    private fun showDialogue() {
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("About BitCoinData")

        // Display a message on alert dialog
        builder.setMessage("BitcoinData was developed by Manuel carvalho")

        builder.setNeutralButton("Done") { _, _ ->

        }


        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
