package com.junpu.chart.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junpu.chart.demo.bar.BarChartActivity
import com.junpu.chart.demo.utils.launch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 柱状图
        barChart.setOnClickListener {
            launch(BarChartActivity::class.java)
        }

        // 折线图
        lineChart.setOnClickListener {

        }
    }
}
