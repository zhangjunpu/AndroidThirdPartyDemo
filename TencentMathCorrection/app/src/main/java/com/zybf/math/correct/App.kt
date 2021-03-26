package com.zybf.math.correct

import android.app.Application
import com.junpu.log.L
import com.junpu.toast.toastContext
import com.junpu.utils.app

/**
 *
 * @author junpu
 * @date 2020/9/4
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        toastContext = this
        L.logEnable = true
    }
}