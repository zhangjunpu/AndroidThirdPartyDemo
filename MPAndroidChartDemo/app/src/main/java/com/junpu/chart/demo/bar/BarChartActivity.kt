package com.junpu.chart.demo.bar

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.gson.reflect.TypeToken
import com.junpu.chart.demo.R
import com.junpu.chart.demo.bean.ChartBean
import com.junpu.chart.demo.utils.fromJson
import com.junpu.chart.demo.utils.readAssetsFile
import kotlinx.android.synthetic.main.activity_bar_chart.*
import kotlin.math.max
import kotlin.math.min


/**
 * 柱状图
 * @author junpu
 * @date 2020-01-09
 */
class BarChartActivity : AppCompatActivity() {

    private var chartSrc: List<ChartBean>? = null
    private var chartEntry: List<BarEntry>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        chartSrc = readAssetsFile("bar_chart.json")
            ?.fromJson<List<ChartBean>>(object : TypeToken<List<ChartBean>>() {}.type)
        chartEntry = chartSrc?.mapIndexed { index, chartBean ->
            BarEntry(index.toFloat(), chartBean.count.toFloat())
        }

        initView()
        initData(emptyList())
        initData(chartEntry)
    }

    private fun initView() {
        chart?.run {
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            description.isEnabled = false
//            setTouchEnabled(false)
            setMaxVisibleValueCount(60)
            setPinchZoom(false)
            setDrawGridBackground(false)

            val xFormatter = XValueFormatter(chartSrc)
            // X轴
            xAxis.run {
                setDrawGridLines(false) // 是否显示x轴线
                setDrawAxisLine(false) // 是否显示底边线
//                setDrawBorders(true) // 外边框
                position = XAxisPosition.BOTTOM
                textSize = 10f
                granularity = 1f
                valueFormatter = xFormatter
            }

            val maxValue = max(2f, chartSrc?.maxBy { it.count }?.count?.toFloat() ?: 0f)
            // 获取到图形左边的Y轴
            axisLeft.run {
                // 设置限制临界线
                addLimitLine(LimitLine(5f, "临界点").apply {
                    lineColor = Color.RED
                    lineWidth = 1f
                    textColor = Color.RED
                })
//                setDrawGridLines(false) // 是否显示Y轴线
                setDrawAxisLine(false) // 是否显示边线
                setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
//                spaceTop = 15f
//                axisMaximum = 13f // 顶部以0为基准线
                labelCount = min(maxValue.toInt(), 5) // Y轴线数量
                axisMaximum = maxValue // 顶部以0为基准线
                axisMinimum = 0f // 底部以0为基准线
                valueFormatter = YValueFormatter()
            }

            // 获取到图形右边的Y轴，并设置为不显示
            axisRight.isEnabled = false

            // 图例设置
            legend.run {
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM // 上、中、下
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT // 左、中、右
                orientation = Legend.LegendOrientation.HORIZONTAL // 横向或纵向
                setDrawInside(false) // 画在内部还是外部
                form = Legend.LegendForm.SQUARE // 图标形状
                formSize = 9f // 图标大小
                textSize = 11f // 文字大小
                formToTextSpace = 6f // 图表文字间距
                direction = Legend.LegendDirection.LEFT_TO_RIGHT // 方向
                xEntrySpace = 14f
            }

            // 如果点击柱形图，会弹出pop提示框.XYMarkerView为自定义弹出框
            marker = XYMarkerView(this@BarChartActivity, xFormatter).apply {
                chartView = chart
            }
        }
    }

    private fun initData(list: List<BarEntry>?) {
        val width = 0.6f / 7 * (list?.size ?: 0)
        chart?.run {
            if (data != null && data.dataSetCount > 0) {
                (data.getDataSetByIndex(0) as? BarDataSet)?.run {
                    values = list
                }
                data.barWidth = width
                data?.notifyDataChanged()
                notifyDataSetChanged()
            } else {
                val dataSet = BarDataSet(list, "BarChart Test").apply {
                    color = Color.parseColor("#4A90E2") // 柱状图颜色
                }
                val barDataSet = arrayListOf<IBarDataSet>(dataSet)
                data = BarData(barDataSet).apply {
                    setValueTextSize(10f)
                    barWidth = width
                }
            }
        }
    }

}
