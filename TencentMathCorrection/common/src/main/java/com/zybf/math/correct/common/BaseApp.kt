package com.zybf.math.correct.common

import android.app.Application

/**
 *
 * @author junpu
 * @date 2020/9/4
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BaseApp
    }

}