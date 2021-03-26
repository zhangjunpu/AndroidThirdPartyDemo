package com.zybf.math.correct

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import com.junpu.log.L
import com.junpu.utils.gson.toJson
import com.junpu.utils.toByteArray
import com.tencent.taisdk.*
import java.util.*

/**
 *
 * @author junpu
 * @date 2020/10/16
 */
open class OralManager {

    private val correction by lazy { TAIMathCorrection() }
    private var curTimestamp = 0L

    fun getSign(): String {
        curTimestamp = System.currentTimeMillis() / 1000
        val sign = correction.getStringToSign(curTimestamp)
        val uuid = UUID.randomUUID().toString()
        L.v("curTimestamp: $curTimestamp, signature: \n$sign")
        L.v("uuid: $uuid")
        return sign
    }

    /**
     * 开始批改
     */
    fun correction(
        context: Context,
        bitmap: Bitmap,
        success: (result: TAIMathCorrectionRet) -> Unit,
        failure: (error: TAIError?) -> Unit,
        complete: (() -> Unit)? = null
    ) {
        val param = TAIMathCorrectionParam().apply {
            this.context = context
            this.sessionId = UUID.randomUUID().toString()
            this.appId = BuildConfig.APP_ID
            this.secretId = BuildConfig.SECRET_ID
            this.imageData = bitmap.toByteArray(50)
            this.secretKey = BuildConfig.SECRET_KEY // 内部签名
            // 外部签名
//            this.signature = "807db04ea8427bb454b15579fdb82008a22914c692a683e109f4e2664b9a857b"
//            this.timestamp = 1604390385
            this.laTex = 1
        }

        correction.mathCorrection(param, object : TAIMathCorrectionCallback {
            override fun onSuccess(result: TAIMathCorrectionRet?) {
                L.vv(result?.toJson())

                if (result == null) {
                    failure(null)
                } else {
                    success(result)
                }
                complete?.invoke()
            }

            override fun onError(error: TAIError?) {
                failure(error)
                complete?.invoke()
            }
        })
    }

}