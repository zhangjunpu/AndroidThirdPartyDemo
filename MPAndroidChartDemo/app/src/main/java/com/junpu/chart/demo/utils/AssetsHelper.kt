@file:JvmName("AssetsHelper")

package com.junpu.chart.demo.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException

/**
 * Assets工具类
 * @author junpu
 * @date 2019-07-23
 */

/**
 * 读取Assets文件夹内的文件
 */
fun Context.readAssetsFile(fileName: String): String? {
    val text: String?
    val br: BufferedReader?
    try {
        br = resources?.assets?.open(fileName)?.reader()?.buffered()
        text = br?.readText()
        br?.close()
    } catch (e: IOException) {
        loge(e)
        return null
    }
    return text
}