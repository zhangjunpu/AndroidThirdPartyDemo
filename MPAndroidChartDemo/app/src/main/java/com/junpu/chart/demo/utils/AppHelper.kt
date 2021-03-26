@file:JvmName("AppHelper")

package com.junpu.chart.demo.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.junpu.chart.demo.App
import com.junpu.chart.demo.LogManager
import com.junpu.chart.demo.R
import com.junpu.log.L
import com.junpu.log.logStackTrace

/**
 * App扩展类
 * @author junpu
 * @date 2019-06-29
 */

fun log(any: Any?) {
    if (LogManager.instance.isLogEnable) {
        println(any)
    }
}

fun loge(t: Throwable?, errorMsg: String? = null) {
    t?.let {
        errorMsg?.let { msg -> L.e(msg) }
        it.logStackTrace()
    }
}

val baseApplication
    get() = App.instance

fun getStringResource(stringId: Int): String = baseApplication.resources.getString(stringId)

fun getColorResource(colorId: Int): Int = baseApplication.getColorResource(colorId)

fun Context.getAppName(): CharSequence? {
    val appInfo = packageManager?.getApplicationInfo(packageName, 0)
    val labelRes = appInfo?.labelRes ?: 0
    val label = if (labelRes > 0) getString(labelRes) else appInfo?.nonLocalizedLabel
    return label ?: getString(R.string.app_name)
}

fun Context.getApplicationMetaData(key: String): String? {
    val appInfo = packageManager?.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
    return appInfo?.metaData?.getString(key)
}

fun Activity.getActivityMetaData(key: String): String? {
    val info = packageManager?.getActivityInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}

fun Context.getServiceMetaData(cls: Class<*>, key: String): String? {
    val componentName = ComponentName(this, cls)
    val info = packageManager?.getServiceInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}

fun Activity.getReceiverMetaData(cls: Class<*>, key: String): String? {
    val componentName = ComponentName(this, cls)
    val info = packageManager?.getReceiverInfo(componentName, PackageManager.GET_META_DATA)
    return info?.metaData?.getString(key)
}