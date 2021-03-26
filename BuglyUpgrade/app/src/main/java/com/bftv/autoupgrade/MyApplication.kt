package com.bftv.autoupgrade

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.multidex.MultiDex
import com.abooc.util.Debug
import com.bftv.upgrade.bugly.UpgradeManager

/**
 * MyApplication
 * @author Junpu
 * @time 2018/4/8 11:34
 */
class MyApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        UpgradeManager.installTinker()
    }

    override fun onCreate() {
        super.onCreate()
        Debug.DEBUG_ENABLE = true
        initBugly()
    }

    /**
     * 初始化bugly
     */
    private fun initBugly() {
        val channel = ToolUtils.getApplicationMetaData(this, "APP_CHANNEL")
        println("app channel = $channel")
        UpgradeManager.initUpgrade()
                .initTinker()
                .isDebug(BuildConfig.DEBUG)
                .initChannel(channel)
                .init(this)
    }

    private fun setStrictMode() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
    }
}