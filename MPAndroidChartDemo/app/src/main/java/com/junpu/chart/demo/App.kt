package com.junpu.chart.demo

import android.app.Application

/**
 *
 * @author junpu
 * @date 2020-01-08
 */
class App : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogManager.instance.isLogEnable = BuildConfig.DEBUG
    }
}