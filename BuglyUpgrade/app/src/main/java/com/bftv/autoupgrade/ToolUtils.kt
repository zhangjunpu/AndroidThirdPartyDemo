package com.bftv.autoupgrade

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager


/**
 *
 * @author Junpu
 * @time 2018/8/22 16:51
 */
object ToolUtils {

    fun getApplicationMetaData(context: Context, key: String): String? {
        val appInfo = context.packageManager?.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        return appInfo?.metaData?.getString(key)
    }

    fun getActivityMetaData(context: Activity, key: String): String? {
        val info = context.packageManager?.getActivityInfo(context.componentName, PackageManager.GET_META_DATA)
        return info?.metaData?.getString(key)
    }

    fun getServiceMetaData(context: Context, cls: Class<*>, key: String): String? {
        val componentName = ComponentName(context, cls)
        val info = context.packageManager?.getServiceInfo(componentName, PackageManager.GET_META_DATA)
        return info?.metaData?.getString(key)
    }

    fun getReceiverMetaData(context: Activity, cls: Class<*>, key: String): String? {
        val componentName = ComponentName(context, cls)
        val info = context.packageManager?.getReceiverInfo(componentName, PackageManager.GET_META_DATA)
        return info?.metaData?.getString(key)
    }
}