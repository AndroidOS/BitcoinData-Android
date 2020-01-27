package com.example.bitcoindata.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bitcoindata.R
import com.example.bitcoindata.model.Price
import com.example.bitcoindata.utils.graphData
import com.example.bitcoindata.viewmodel.ListViewModel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "GraphActivity"
class GraphActivity : AppCompatActivity() {


    private lateinit var series1: LineGraphSeries<DataPoint>
    private lateinit var bitcoinData: List<Price>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

       fillChart()

    }




    fun fillChart(){
        var x = 0.0
        var y = 0.0

        series1 = LineGraphSeries()


        for (p in graphData){
            x += 1.0
            Log.d(TAG, "${p.amount}")
            series1.appendData(DataPoint(x, p.amount!!.toDouble()), true, 30)
        }


        series1.color = Color.RED


        graph.addSeries(series1)

    }
}
