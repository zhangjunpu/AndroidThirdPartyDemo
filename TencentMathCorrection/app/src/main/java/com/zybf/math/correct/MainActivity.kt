package com.zybf.math.correct

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.log.L
import com.junpu.utils.gson.fromJson
import com.tencent.taisdk.TAIMathCorrectionRet
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : PermissionsActivity() {

    private val correctionManager by lazy { OralManager() }

    private var bitmap: Bitmap? = null
    private val storageDir by lazy { Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) }
    private var bitmapFile: File? = null

    private val localImageIds = arrayOf(45, 279, 288)
    private val localImages by lazy {
        localImageIds.map {
            SparseArray<String>().apply {
                put(0, "/质检/math/math ($it).jpg")
                put(1, "math ($it).json")
            }
        }
    }
    private var curLocalPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAlbum?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_ALBUM)
        }
        btnStart?.setOnClickListener { startCorrection() }
        btnLocal?.setOnClickListener {
            val file = File(storageDir, localImages[curLocalPosition][1])
            file.readText().fromJson(TAIMathCorrectionRet::class.java)?.let {
                val bWidth = bitmap?.width ?: 0
                val bHeight = bitmap?.height ?: 0
                Glide.with(this)
                    .load(bitmap)
                    .apply(
                        RequestOptions.bitmapTransform(
                            OralMarkTransformation(
                                it,
                                bWidth,
                                bHeight
                            )
                        )
                    )
                    .into(imageHomework)
            }
        }
        btnLocalNext?.setOnClickListener {
            if (++curLocalPosition >= localImages.size) curLocalPosition = 0
            val file = File(storageDir, localImages[curLocalPosition][0])
            bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageHomework.setImageBitmap(bitmap)
        }

        checkPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ALBUM && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                val projection = arrayOf(
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.ORIENTATION
                )
                contentResolver.query(uri, projection, null, null, null)?.use {
                    it.moveToFirst()
                    val path = it.getString(it.getColumnIndex(projection[0]))
                    val orientation = it.getString(it.getColumnIndex(projection[1]))
                    L.vv("path: $path, orientation: $orientation")
                    bitmapFile = File(path)
                    if (!path.isNullOrBlank()) {
                        bitmap = BitmapFactory.decodeFile(path)
                        val angle = if (orientation.isNullOrBlank()) 0 else orientation.toInt()
                        if (angle != 0) {
                            bitmap = Bitmap.createBitmap(
                                bitmap!!,
                                0,
                                0,
                                bitmap!!.width,
                                bitmap!!.height,
                                Matrix().apply { setRotate(angle.toFloat()) },
                                true
                            )
                        }
                        imageHomework.setImageBitmap(bitmap)
                    }
                }
            }

        }
    }

    /**
     * 开始批改
     */
    private fun startCorrection() {
        val bitmap = bitmap ?: return
        correctionManager.correction(this, bitmap,
            success = {
                correctionSuccess(it)
            },
            failure = {
                L.ee("${it?.code}: ${it?.desc}")
                runOnUiThread {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("提示")
                        .setMessage(String.format("错误码：%d\n错误信息：%s", it?.code, it?.desc))
                        .setPositiveButton("确定") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
            }
        )
    }

    /**
     * 处理批改结果
     */
    private fun correctionSuccess(result: TAIMathCorrectionRet?) {
        runOnUiThread {
            loading.visibility = View.INVISIBLE
            result?.items?.forEach {
                val view = View(this@MainActivity).apply {
                    setOnClickListener { _ ->
                        if (it.result) {
                            textInfo.text = String.format("正确：%s", it.formula)
                        } else {
                            textInfo.text =
                                String.format("错误：%s, 答案：%s", it.formula, it.answer)
                        }
                    }
                    setBackgroundResource(if (it.result) R.drawable.shape_green_border else R.drawable.shape_red_border)
                    layoutParams = converRect(it.rect).run {
                        MarginLayoutParams(width(), height()).apply {
                            leftMargin = left
                            topMargin = top
                        }
                    }
                }
                layoutMark.addView(view)
            }
            textInfo.text = String.format("识别到%d个算式", result?.items?.size ?: 0)
        }
    }

    private fun converRect(rect: Rect): Rect {
        L.vv("rect: $rect")
        val drawRect = Rect()
        imageHomework.getHitRect(drawRect)
        L.vv("imageHomework.getHitRect: $drawRect")
        val scale: Float
        var offsetX = drawRect.left.toFloat()
        var offsetY = drawRect.top.toFloat()
        val bitmapWidth = bitmap?.width?.toFloat() ?: 0f
        val bitmapHeight = bitmap?.height?.toFloat() ?: 0f
        L.vv("bitmap size: $bitmapWidth/$bitmapHeight")
        if (bitmapHeight / bitmapWidth > drawRect.height() / drawRect.width()) {
            scale = bitmapHeight / drawRect.height()
            offsetX += (drawRect.width() - bitmapWidth / scale) * 0.5f
        } else {
            scale = bitmapWidth / drawRect.width()
            offsetY += (drawRect.height() - bitmapHeight / scale) * 0.5f
        }
        L.vv("offsetXY: $offsetX/$offsetY")
        val l = (offsetX + rect.left / scale).toInt()
        val t = (offsetY + rect.top / scale).toInt()
        val r = l + (rect.width() / scale).toInt()
        val b = t + (rect.height() / scale).toInt()
        L.vv("return: $l, $t, $r, $b")
        return Rect(l, t, r, b)
    }

    companion object {
        private const val REQUEST_ALBUM = 0x100
    }

}