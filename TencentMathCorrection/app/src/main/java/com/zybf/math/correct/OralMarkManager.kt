package com.zybf.math.correct

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import androidx.core.content.ContextCompat
import com.junpu.log.L
import com.junpu.log.logStackTrace
import com.junpu.utils.app
import com.junpu.utils.dip
import com.junpu.utils.getColorResource
import com.junpu.utils.sp
import com.tencent.taisdk.TAIMathCorrectionRet
import com.zybf.math.correct.bean.OralBean
import com.zybf.math.correct.utils.formatFormula
import com.zybf.math.correct.utils.formatFormulaImage

/**
 * 口算标记管理
 * @author junpu
 * @date 2020/10/16
 */
class OralMarkManager {

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

    private val scale = 1f
    private val Drawable.width: Int
        get() = (intrinsicWidth * scale).toInt()

    private val Drawable.height: Int
        get() = (intrinsicHeight * scale).toInt()

    private fun getPaintColor(isRight: Boolean = true) =
        if (isRight) getColorResource(R.color.accent_green) else getColorResource(R.color.accent_red)

    /**
     * 转换数据
     */
    fun convert(bitmap: Bitmap, result: TAIMathCorrectionRet): List<OralBean> {
        val width = bitmap.width
        val height = bitmap.height
        return result.items.map {
            val symbolRect: Rect
            var textPoint = PointF()
            var image: Bitmap? = null
            if (it.result) {
                symbolRect = it.rect.buildSymbolRect(rightSymbol, width, height)
            } else {
                symbolRect = it.rect.buildSymbolRect(wrongSymbol, width, height)
                textPoint = symbolRect.buildTextPoint(it.answer.formatFormulaImage, width, height)
                image = getMistakeBitmap(bitmap, it.rect)
            }
            OralBean(
                it.result,
                it.formula,
                it.answer,
                it.rect,
                symbolRect,
                textPoint,
                null,
                image,
                it.formula.formatFormula,
                it.answer.formatFormulaImage
            )
        }
    }

    /**
     * 标记批改结果
     */
    fun transform(bitmap: Bitmap, list: List<OralBean>?): Bitmap {
        if (list.isNullOrEmpty()) return bitmap

        val width = bitmap.width
        val height = bitmap.height
        L.vv("width/height: $width/$height")

        // draw mark
        val markBitmap =
            Bitmap.createBitmap(width, height, bitmap.config ?: Bitmap.Config.ARGB_8888)
        try {
            Canvas(markBitmap).run {
                drawColor(0, PorterDuff.Mode.CLEAR)
                list.forEach {
                    // 画边框
//                    drawRect(it.rect, strokePaint.apply { color = getPaintColor(it.result) })
                    if (it.result) {
                        // 画√
                        rightSymbol?.bounds = it.symbolRect
                        rightSymbol?.draw(this)
                    } else {
                        // 画x
                        wrongSymbol?.bounds = it.symbolRect
                        wrongSymbol?.draw(this)
                        // 画文字
                        it.text?.let { text ->
                            drawText(text, it.textPoint.x, it.textPoint.y, textPaint)
                        }
                    }
                }
                setBitmap(null)
            }
        } catch (e: Exception) {
            e.logStackTrace()
        }
        // draw final bitmap
        val resultBitmap =
            Bitmap.createBitmap(width, height, bitmap.config ?: Bitmap.Config.ARGB_8888)
        try {
            Canvas(resultBitmap).run {
                drawBitmap(bitmap, 0f, 0f, null)
                drawBitmap(markBitmap, 0f, 0f, null)
            }
        } catch (e: Exception) {
            e.logStackTrace()
        }
        markBitmap.recycle()
        return resultBitmap
    }

    /**
     * 根据题目框，构建对勾、错叉位置
     */
    private fun Rect.buildSymbolRect(drawable: Drawable?, maxWidth: Int, maxHeight: Int): Rect {
        val width = drawable?.width ?: 0
        val height = drawable?.height ?: 0
        L.vv("${width}/${height}")

        var left = right
        var top = top + (height() - height) / 2
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

    /**
     * 截取问题图片
     */
    fun getMistakeBitmap(bitmap: Bitmap, rect: Rect): Bitmap {
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height())
    }

}