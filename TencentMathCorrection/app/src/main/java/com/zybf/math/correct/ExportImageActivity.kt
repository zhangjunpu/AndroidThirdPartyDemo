package com.zybf.math.correct

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.log.L
import com.junpu.toast.toast
import com.junpu.utils.gson.fromJson
import com.junpu.utils.gson.toJson
import com.junpu.utils.toFile
import com.tencent.taisdk.TAIMathCorrectionRet
import com.zybf.math.correct.bean.OralBean
import com.zybf.math.correct.utils.showToastDialog
import kotlinx.android.synthetic.main.activity_export_image.*
import java.io.File

/**
 *
 * @author junpu
 * @date 2020/10/16
 */
class ExportImageActivity : PermissionsActivity() {

    private val storageDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

    private val oralManager by lazy { OralManager() }
    private val marManager by lazy { OralMarkManager() }
    private var oralList: List<OralBean>? = null
    private var srcBitmap: Bitmap? = null
    private var resultBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export_image)

//        val imageName = "/口算/2年级/O2SUF1778（原图）.jpg"
//        val jsonName = "/口算/2年级/O2SUF1778（原图）.json"
        val imageName = "/口算/6年级/1602745424(1).jpg"
        val jsonName = "/口算/6年级/1602745424(1).json"
        checkPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            val srcFile = File(storageDir, imageName)
            if (!srcFile.exists()) {
                toast("image文件不存在")
                return@checkPermissions
            }
            srcBitmap = BitmapFactory.decodeFile(srcFile.path)
            imageHomework.setImageBitmap(srcBitmap)
        }
        btnSign?.setOnClickListener {
            oralManager.getSign()
        }
        btnStart?.setOnClickListener {
            startCorrection(srcBitmap)
        }
        btnLocal?.setOnClickListener {
            val jsonFile = File(storageDir, jsonName)
            if (srcBitmap == null) {
                toast("作业图片为null")
                return@setOnClickListener
            }
            if (!jsonFile.exists()) {
                toast("json文件不存在")
                return@setOnClickListener
            }
            jsonFile.readText().fromJson(TAIMathCorrectionRet::class.java)?.let {
                correctSuccess(srcBitmap!!, it)
            }
        }
        btnProcess?.setOnClickListener {
            val dir = File(storageDir, "/口算")
//            correctLocalImages(dir)
        }
    }

    /**
     * 批量处理图片
     */
    private fun correctLocalImages(dir: File) {
        dir.listFiles()?.forEach {
            if (it.isDirectory) {
                if (it.name != "答案") {
                    correctLocalImages(it)
                }
            } else {
                if (it.extension == "jpg" || it.extension == "png" || it.extension == "jpeg") {
                    L.vv(it.absoluteFile)
                    val bitmap = BitmapFactory.decodeFile(it.absolutePath)
                    startCorrection(bitmap, it)
                }
            }
        }
    }

    /**
     * 开始批改
     */
    private fun startCorrection(bitmap: Bitmap?, file: File? = null) {
        bitmap ?: return
        L.vv("startCorrection : ${file?.name}")
//        showLoadingDialog(true)
        oralManager.correction(this, bitmap,
            success = {
                correctSuccess(bitmap, it, file)
            },
            failure = {
                L.ee("${it?.code}: ${it?.desc}")
                runOnUiThread {
                    showToastDialog(String.format("错误码：%d\n错误信息：%s", it?.code, it?.desc))
                }
            },
            complete = {
//                showLoadingDialog(false)
            }
        )
    }

    /**
     * 处理批改结果
     */
    private fun correctSuccess(bitmap: Bitmap, result: TAIMathCorrectionRet, file: File? = null) {
        oralList = marManager.convert(bitmap, result)
        resultBitmap = marManager.transform(bitmap, oralList)
        runOnUiThread {
            imageHomework.setImageBitmap(resultBitmap)
        }
        file?.let {
            val filePath = "${file.parent}${File.separator}${file.nameWithoutExtension}"
            resultBitmap?.toFile(filePath + "_result.jpg")
            result.toJson()?.let { File("$filePath.json").writeText(it) }
        }
    }

}