package com.junpu.chart.demo

import com.junpu.log.L

/**
 * Log管理
 * @author junpu
 * @date 2019-06-29
 */
class LogManager private constructor() {

    companion object {
        @JvmStatic
        val instance: LogManager by lazy { LogManager() }
    }

    /**
     * 设置log开关
     */
    var isLogEnable = true
        set(enable) {
            field = enable
            L.logEnable = enable
        }

}
