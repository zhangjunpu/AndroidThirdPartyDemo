@file:JvmName("TimeHelper")

package com.junpu.chart.demo.utils

import android.text.format.DateFormat
import java.util.*

/**
 * 日期时间工具类
 *
 * <br>
 * y：年
 * M：月
 * d：日
 * h：时（12小时制，0-12）
 * H：时（24小时制，0-23）
 * m：分
 * s：秒
 * S：毫秒
 * E：星期几
 * a：上下午标识
 * </br>
 *
 * @author junpu
 * @date 2019-07-03
 */

/**
 * Date格式化
 */
fun Date.format(format: CharSequence): CharSequence = DateFormat.format(format, this)

val Date.formatDateTime
    get() = format("yyyy-MM-dd HH:mm")

val Date.formatDate
    get() = format("yyyy-MM-dd")

val Date.formatDateChinese
    get() = format("yyyy年MM月dd日")

val Date.formatTime
    get() = format("HH:mm")

val Date.formatWeek
    get() = format("E")

/**
 * 时间戳格式化
 */
fun Long.format(format: CharSequence): CharSequence = Date(this).format(format)

val Long.formatDateTime
    get() = Date(this).formatDateTime

val Long.formatDate
    get() = Date(this).formatDate

val Long.formatDateChinese
    get() = Date(this).formatDateChinese

val Long.formatTime
    get() = Date(this).formatTime

val Long.formatWeek
    get() = Date(this).formatWeek

/**
 * 非空格式化
 */
fun Long?.formatNotNull(format: CharSequence): CharSequence = if (this == null || this == 0L) "" else format(format)

val Long?.formatDateTimeNotNull
    get() = if (this == null || this == 0L) "" else formatDateTime

val Long?.formatDateNotNull
    get() = if (this == null || this == 0L) "" else formatDate

val Long?.formatDateChineseNotNull
    get() = if (this == null || this == 0L) "" else formatDateChinese

val Long?.formatTimeNotNull
    get() = if (this == null || this == 0L) "" else formatTime

val Long?.formatWeekNotNull
    get() = if (this == null || this == 0L) "" else formatWeek

/**
 * 格式化时间
 */
fun formatDuration(millisecond: Long): String {
    val totalSeconds = millisecond / 1000
    val days = totalSeconds / 60 / 60 / 24
    val hours = totalSeconds / 60 / 60 % 24
    val minutes = totalSeconds / 60 % 60
    val seconds = totalSeconds % 60
    return when {
        days > 0 -> String.format("%dT, %02d:%02d:%02d", days, hours, minutes, seconds)
        hours > 0 -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
        else -> String.format("%02d:%02d", minutes, seconds)
    }
}

/**
 * 格式化时间
 */
fun formatVoiceDuration(millisecond: Long): String {
    val seconds = millisecond / 1000
    return String.format("%d\"", seconds)
}
