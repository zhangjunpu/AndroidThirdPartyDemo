package com.zybf.math.correct.bean

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.Rect

/**
 *
 * @author junpu
 * @date 2020/10/29
 */
data class OralBean(
    var result: Boolean = false, // 是否正确
    var formula: String? = null, // 腾讯云识别的算式
    var answer: String? = null, // 腾讯云返回的答案
    var rect: Rect = Rect(), // 框
    var symbolRect: Rect = Rect(), // 符号位置
    var textPoint: PointF = PointF(), // 文字坐标
    var imageId: String? = null,
    var image: Bitmap? = null, // 题目图片
    var formulaFormat: String? = null, // 转换的算式（富文本）
    var text: String? = null, // answer转换的文本（压图用）
    var knowledge: String? = null, // 知识点
    var isDone: Boolean = false
)