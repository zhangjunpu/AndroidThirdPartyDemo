@file:JvmName("ViewHelper")

package com.junpu.chart.demo.utils

import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * View扩展类
 * @author junpu
 * @date 2019-06-29
 */

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setInvisibility(invisible: Boolean) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE
fun View.isGone() = visibility == View.GONE
fun View.isInvisible() = visibility == View.INVISIBLE

fun View.setLayoutSize(width: Int, height: Int) {
    layoutParams.width = width
    layoutParams.height = height
}

fun View.setLayoutSize(size: Int) {
    layoutParams.width = size
    layoutParams.height = size
}

/**
 * 获取View在屏幕中的位置坐标
 */
val View.location
    get() = Rect().also {
        getGlobalVisibleRect(it)
    }

fun View.constraintLayoutParams() = layoutParams as? ConstraintLayout.LayoutParams

/**
 * 是否点击在了一个View内
 */
fun View.isTouchPointInView(x: Float, y: Float): Boolean {
    val location = IntArray(2).also {
        getLocationOnScreen(it)
    }
    val left = location[0]
    val top = location[1]
    val right = left + measuredWidth
    val bottom = top + measuredHeight
    return y >= top && y <= bottom && x >= left && x <= right
}

/**
 * 添加一行
 */
fun TextView.appendLine(text: CharSequence?) = apply {
    append(text)
    append("\n")
}

fun TextView.nextLine() = apply {
    append("\n")
}

fun TextView.clearText() = apply {
    text = null
}
