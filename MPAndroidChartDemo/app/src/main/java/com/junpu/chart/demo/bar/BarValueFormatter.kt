package com.junpu.chart.demo.bar

import com.github.mikephil.charting.formatter.ValueFormatter
import com.junpu.chart.demo.bean.ChartBean
import com.junpu.chart.demo.utils.formatNotNull

/**
 *
 * @author junpu
 * @date 2020-01-09
 */
class XValueFormatter(private var data: List<ChartBean>?) : ValueFormatter() {
    override fun getFormattedValue(value: Float) = data?.get(value.toInt())?.date?.formatNotNull("MM-dd").toString()
}

class YValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float) = value.toInt().toString()
}
