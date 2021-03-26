package com.zybf.math.correct.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.junpu.widget.dialog.LoadingDialog
import com.zybf.math.correct.utils.newLoadingDialog

/**
 * base fragment
 * @author junpu
 * @date 2019-07-28
 */
abstract class BaseFragment : Fragment() {

    private var dialog: LoadingDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout() != 0) return inflater.inflate(layout(), container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog = null
    }

    @LayoutRes
    protected abstract fun layout(): Int

    fun showLoadingDialog(flag: Boolean, @StringRes resId: Int = 0) {
        val msg = if (resId == 0) null else getString(resId)
        return showLoadingDialog(flag, msg)
    }

    fun showLoadingDialog(flag: Boolean, msg: String? = null) {
        if (flag) {
            if (dialog == null) {
                dialog = requireContext().newLoadingDialog(msg)
            }
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }
}
