@file:JvmName("BaseHelper")

package com.junpu.chart.demo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.*
import android.util.Size
import android.util.SizeF
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * 基础扩展类
 * @author junpu
 * @time 2017/11/7 23:09
 */

fun Context.launch(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) {
    val intent = Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        bundle?.let { putExtras(bundle) }
    }
    startActivity(intent)
}

fun Fragment.launch(cls: Class<*>, bundle: Bundle? = null, vararg flags: Int) {
    context?.launch(cls, bundle, *flags)
}

fun Activity.launchForResult(cls: Class<*>, requestCode: Int, args: Bundle? = null, vararg flags: Int) {
    val intent = Intent(this, cls).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.launchForResult(cls: Class<*>, requestCode: Int, args: Bundle? = null, vararg flags: Int) {
    val intent = Intent(context, cls).apply {
        flags.forEach { addFlags(it) }
        args?.let { putExtras(args) }
    }
    startActivityForResult(intent, requestCode)
}

fun Fragment.isPortrait(): Boolean = activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

fun Context.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
        LayoutInflater.from(this).inflate(resource, root, attachToRoot)

fun Fragment.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
        LayoutInflater.from(context).inflate(resource, root, attachToRoot)

fun View.inflate(resource: Int, root: ViewGroup?, attachToRoot: Boolean): View =
        LayoutInflater.from(context).inflate(resource, root, attachToRoot)

fun Context.getColorResource(id: Int) = ContextCompat.getColor(this, id)

fun Context.dp2px(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
fun Context.dp2px(dp: Int): Int = dp2px(dp.toFloat())

fun Fragment.dp2px(dp: Float): Int = context?.dp2px(dp) ?: 0
fun Fragment.dp2px(dp: Int): Int = context?.dp2px(dp) ?: 0

fun View.dp2px(dp: Float): Int = context?.dp2px(dp) ?: 0
fun View.dp2px(dp: Int): Int = context?.dp2px(dp) ?: 0

fun dp2px(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density + 0.5).toInt()
fun dp2px(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density + 0.5).toInt()

/**
 * 获取Intent实例，此Intent清掉目标之上的Activity，并走目标的onNewIntent()方法
 */
fun Context.getNewIntent(cls: Class<*>) = Intent(this, cls).apply {
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 清掉目标Activity和其之上的Activity，并重新创建目标Activity
    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) // 不会清掉目标Activity，而是走onNewIntent()方法
}

fun postDelay(r: Runnable, delayMillis: Long) = Handler().postDelayed(r, delayMillis)
fun postDelay(delayMillis: Long = 100, r: () -> Unit) = Handler().postDelayed(r, delayMillis)

/**
 * 重试
 */
fun postRetry(retryTimes: Int, delayMillis: Long = 100, retry: (retryTimes: Int) -> Unit) {
    var retryIndex = retryTimes
    retryIndex--
    if (retryIndex > 0) postDelay(delayMillis) { retry(retryIndex) }
}

fun bundleOf(vararg pairs: Pair<String, Any?>) = Bundle(pairs.size).apply {
    for ((key, value) in pairs) {
        append(key, value)
    }
}

fun Bundle.appendAll(bundle: Bundle?) = apply {
    bundle?.let { putAll(it) }
}

@SuppressLint("ObsoleteSdkInt")
fun Bundle.append(key: String, value: Any?) = apply {
    when (value) {
        null -> putString(key, null) // Any nullable type will suffice.

        // Scalars
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Double -> putDouble(key, value)
        is Float -> putFloat(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Short -> putShort(key, value)

        // References
        is Bundle -> putBundle(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Parcelable -> putParcelable(key, value)

        // Scalar arrays
        is BooleanArray -> putBooleanArray(key, value)
        is ByteArray -> putByteArray(key, value)
        is CharArray -> putCharArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is ShortArray -> putShortArray(key, value)

        // Reference arrays
        is Array<*> -> {
            val componentType = value::class.java.componentType!!
            @Suppress("UNCHECKED_CAST") // Checked by reflection.
            when {
                Parcelable::class.java.isAssignableFrom(componentType) -> {
                    putParcelableArray(key, value as Array<Parcelable>)
                }
                String::class.java.isAssignableFrom(componentType) -> {
                    putStringArray(key, value as Array<String>)
                }
                CharSequence::class.java.isAssignableFrom(componentType) -> {
                    putCharSequenceArray(key, value as Array<CharSequence>)
                }
                Serializable::class.java.isAssignableFrom(componentType) -> {
                    putSerializable(key, value)
                }
                else -> {
                    val valueType = componentType.canonicalName
                    throw IllegalArgumentException("Illegal value array type $valueType for key \"$key\"")
                }
            }
        }

        // Last resort. Also we must check this after Array<*> as all arrays are serializable.
        is Serializable -> putSerializable(key, value)

        else -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && value is Binder) {
                putBinder(key, value)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && value is Size) {
                putSize(key, value)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && value is SizeF) {
                putSizeF(key, value)
            } else {
                val valueType = value.javaClass.canonicalName
                throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
            }
        }
    }
}
