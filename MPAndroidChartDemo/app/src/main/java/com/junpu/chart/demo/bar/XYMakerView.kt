package com.junpu.chart.demo.bar

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.junpu.chart.demo.R
import kotlinx.android.synthetic.main.custom_marker_view.view.*

/**
 *
 * @author junpu
 * @date 2020-01-09
 */
class XYMarkerView : MarkerView {

    private var formatter: ValueFormatter? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, formatter: ValueFormatter?) : super(context, R.layout.custom_marker_view) {
        this.formatter = formatter
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val x = formatter?.getFormattedValue(e?.x ?: 0f) ?: e?.x.toString()
        val y = e?.y?.toInt() ?: 0
        textContent.text = String.format("x：%s，y：%d", x, y)
        super.refreshContent(e, highlight)
    }

    override fun getOffset() = MPPointF(-width / 2f, -height.toFloat())
}