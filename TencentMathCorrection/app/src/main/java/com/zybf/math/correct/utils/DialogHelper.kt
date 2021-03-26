@file:JvmName("DialogHelper")

package com.zybf.math.correct.utils

import android.content.Context
import android.content.DialogInterface
import com.junpu.widget.dialog.ListDialog
import com.junpu.widget.dialog.LoadingDialog
import com.junpu.widget.dialog.ToastDialog

/**
 * 快速生成对话框
 * @author zhangjunpu
 * @date 2017/2/10
 */


/**
 * 显示Loading对话框
 */
fun Context?.newLoadingDialog(msg: String? = null) = this?.run {
    LoadingDialog.Builder(this)
        .setMessage(msg)
        .create()
}

/**
 * 显示提示对话框
 */
fun Context?.newToastDialog(
    message: String,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null
) = this?.run {
    ToastDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton { dialog, which -> positiveListener?.invoke(dialog, which) }
        .create()
}

/**
 * 显示提示对话框
 */
fun Context?.newToastDialog(
    msgResId: Int,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null
) = this?.run {
    ToastDialog.Builder(this)
        .setMessage(msgResId)
        .setPositiveButton { dialog, which -> positiveListener?.invoke(dialog, which) }
        .create()
}

/**
 * 显示Loading对话框
 */
fun Context?.showLoadingDialog(msg: String? = null) = newLoadingDialog(msg)?.apply {
    show()
}

/**
 * 显示提示对话框
 */
fun Context?.showToastDialog(
    message: String,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null
) = newToastDialog(message, positiveListener)?.apply {
    show()
}

/**
 * 显示提示对话框
 */
fun Context?.showToastDialog(
    msgResId: Int,
    positiveListener: ((DialogInterface, Int) -> Unit)? = null
) = newToastDialog(msgResId, positiveListener)?.apply {
    show()
}

/**
 * 显示列表对话框
 */
fun Context.newListDialog(
    list: Array<String>,
    listener: ((dialog: ListDialog, position: Int) -> Unit)? = null
) = ListDialog.Builder(this)
    .setList(list)
    .setOnItemClickListener(listener)
    .show()

/**
 * 显示列表对话框
 */
fun Context.showListDialog(
    list: Array<String>,
    listener: ((dialog: ListDialog, position: Int) -> Unit)? = null
) = newListDialog(list, listener).apply {
    show()
}
