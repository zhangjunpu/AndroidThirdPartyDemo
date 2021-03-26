package com.zybf.math.correct.base

import androidx.annotation.StringRes
import com.junpu.gopermissions.PermissionsActivity
import com.junpu.widget.dialog.LoadingDialog
import com.zybf.math.correct.utils.newLoadingDialog

/**
 * base activity
 * @author junpu
 * @date 2019-07-28
 */
open class BaseActivity : PermissionsActivity() {

    private var dialog: LoadingDialog? = null

    override fun onDestroy() {
        super.onDestroy()
        dialog = null
    }

    fun showLoadingDialog(flag: Boolean, @StringRes resId: Int) {
        val msg = if (resId == 0) null else getString(resId)
        return showLoadingDialog(flag, msg)
    }

    fun showLoadingDialog(flag: Boolean, msg: String? = null) {
        if (flag) {
            if (dialog == null) {
                dialog = newLoadingDialog(msg)
            }
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }
}
