@file:JvmName("AppHelper")

package com.zybf.math.correct.common.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.zybf.math.correct.common.BaseApp
import com.zybf.math.correct.common.R

/**
 * App扩展类
 * @author junpu
 * @date 2019-06-29
 */

val baseApplication
    get() = BaseApp.instance

fun getStringResource(@StringRes stringId: Int): String =
    baseApplication.resources.getString(stringId)

fun getColorResource(@ColorRes colorId: Int): Int = baseApplication.getColorResource(colorId)

fun Context.getColorResource(@ColorRes id: Int) = ContextCompat.getColor(this, id)

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