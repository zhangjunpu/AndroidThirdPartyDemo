package com.zybf.math.correct

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.utils.app
import com.junpu.utils.dip
import com.junpu.utils.getColorResource
import com.junpu.utils.sp
import com.tencent.taisdk.TAIMathCorrectionRet
import java.security.MessageDigest

/**
 * Glide Correction Mark Transformation
 * @author junpu
 * @date 2019-10-10
 */
class OralMarkTransformation(
    private var markLocation: TAIMathCorrectionRet,
    private var srcWidth: Int,
    private var srcHeight: Int
) : BitmapTransformation() {

    companion object {
        private const val VERSION = 1
        private const val ID = "com.zybf.math.correct.CorrectionMarkTransformation.$VERSION"
    }

    private val rightSymbol by lazy {
        ContextCompat.getDrawable(app, R.drawable.ic_symbol_right)
    } // 对勾符号
    private val wrongSymbol by lazy {
        ContextCompat.getDrawable(app, R.drawable.ic_symbol_wrong)
    } // 错叉符号

    private val strokePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getPaintColor()
            style = Paint.Style.STROKE
            strokeWidth = dip(1).toFloat()
        }
    }
    private val textPaint: TextPaint by lazy {
        TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getColorResource(R.color.accent_red)
            textSize = sp(10).toFloat()
        }
    }

    private val scale = 0.7f
    private val Drawable.width: Int
        get() = (intrinsicWidth * scale).toInt()

    private val Drawable.height: Int
        get() = (intrinsicHeight * scale).toInt()

    private fun getPaintColor(isRight: Boolean = true) =
        if (isRight) getColorResource(R.color.accent_green) else getColorResource(R.color.accent_red)

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        if (markLocation.items.isNullOrEmpty()) return toTransform
        L.vv("srcWidth/srcHeight: $srcWidth/$srcHeight")

        val inWidth = toTransform.width
        val inHeight = toTransform.height
        L.vv("inWidth/inHeight: $inWidth/$inHeight")

        // 缩放比例
        val scaleX = inWidth / srcWidth.toFloat()
        val scaleY = inHeight / srcHeight.toFloat()
        L.vv("scaleX: ${scaleX}, scaleY: $scaleY")

        // draw final bitmap
        val resultBitmap = pool.get(inWidth, inHeight, getSafeConfig(toTransform))
        try {
            Canvas(resultBitmap).run {
                drawBitmap(toTransform, 0f, 0f, null)
                markLocation.items.forEach {
                    val rectF = RectF(
                        it.rect.left * scaleX,
                        it.rect.top * scaleY,
                        it.rect.right * scaleX,
                        it.rect.bottom * scaleY
                    )
//                    drawRect(rectF, strokePaint.apply { color = getPaintColor(it.result) })
                    if (it.result) {
                        // 画√
                        rightSymbol?.bounds = rectF.buildSymbolRect(rightSymbol, inWidth, inHeight)
                        rightSymbol?.draw(this)
                    } else {
                        // 画x
                        wrongSymbol?.bounds = rectF.buildSymbolRect(wrongSymbol, inWidth, inHeight)
                        wrongSymbol?.draw(this)
                        // 画文字
                        val point =
                            wrongSymbol?.bounds?.buildTextPoint(it.answer, inWidth, inHeight)
                        drawText(it.answer, point?.x ?: 0f, point?.y ?: 0f, textPaint)
                    }
                }
            }
        } catch (e: Exception) {
            e.logStackTrace()
        }
        return resultBitmap
    }

    /**
     * 根据题目框，构建对勾、错叉位置
     */
    private fun RectF.buildSymbolRect(drawable: Drawable?, maxWidth: Int, maxHeight: Int): Rect {
        val width = drawable?.width ?: 0
        val height = drawable?.height ?: 0

        var left = right.toInt()
        var top = (top + (height() - height) / 2).toInt()
        var right = left + width
        var bottom = top + height
        // 超出边界位置微调
        if (right > maxWidth) {
            val offset = right - maxWidth
            right = maxWidth
            left -= offset
        }
        if (bottom > maxHeight) {
            val offset = bottom - maxHeight
            bottom = maxHeight
            top -= offset
        }
        return Rect(left, top, right, bottom)
    }

    /**
     * 构建文字位置
     */
    private fun Rect.buildTextPoint(text: String?, maxWidth: Int, maxHeight: Int): PointF {
        val width = textPaint.measureText(text)
        var textX = right.toFloat()
        var textY = bottom + textPaint.textSize
        if ((textX + width) > maxWidth) {
            val offset = (textX + width) - maxWidth
            textX -= offset
        }
        if (textY > maxHeight) {
            textY = maxHeight.toFloat()
        }
        return PointF(textX, textY)
    }

    private fun getSafeConfig(bitmap: Bitmap) = bitmap.config ?: Bitmap.Config.ARGB_8888

    override fun equals(other: Any?) = other is OralMarkTransformation

    override fun hashCode() = ID.hashCode()

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID.toByteArray(Key.CHARSET))
    }
}