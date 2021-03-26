package com.bftv.upgrade.bugly

import android.content.Context
import com.abooc.util.Debug
import com.tencent.bugly.Bugly
import com.tencent.bugly.BuglyStrategy
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import com.tencent.bugly.beta.ui.UILifecycleListener
import com.tencent.tinker.entry.ApplicationLike
import java.io.File

/**
 * Upgrade管理器
 * @author Junpu
 * @time 2018/4/4 17:35
 */
object UpgradeManager {

    private const val BUGLY_APP_KEY = "54d9f4d032"

    private var channel: String? = null
    private var isDebug: Boolean = false

    /**
     * 初始化tinker、MultiDex
     */
    fun installTinker(applicationLike: ApplicationLike) {
        Beta.installTinker(applicationLike)
    }

    /**
     * 初始化tinker、MultiDex
     */
    fun installTinker() {
        Beta.installTinker()
    }

    /**
     * Bugly统一初始化
     * 初始化（此方法需要最后调用）
     */
    fun init(context: Context) {
        Bugly.init(context, BUGLY_APP_KEY, isDebug, channel?.let {
            BuglyStrategy().apply {
                appChannel = channel
            }
        })
    }

    /**
     * 是否为debug模式
     */
    fun isDebug(isDebug: Boolean = false): UpgradeManager {
        this.isDebug = isDebug
        return this
    }

    /**
     * 添加渠道号
     */
    fun initChannel(channel: String? = null): UpgradeManager {
        this.channel = channel
        return this
    }

    /**
     * 初始化全量更新
     */
    fun initUpgrade(): UpgradeManager {
        Beta.autoInit = true // true表示app启动自动初始化升级模块；
        Beta.autoCheckUpgrade = false // true表示初始化时自动检查升级，false需要手动调用checkUpgrade
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade_iris
        Beta.strUpgradeDialogVersionLabel = "更新版本"
        Beta.strUpgradeDialogFileSizeLabel = "大小"
        Beta.strUpgradeDialogUpdateTimeLabel = "时间"
        return this
    }

    /**
     * 初始化tinker
     */
    fun initTinker(): UpgradeManager {
        Beta.enableHotfix = true // 设置是否开启热更新能力，默认为true
        Beta.canAutoDownloadPatch = true // 设置是否自动下载补丁
        Beta.canNotifyUserRestart = true // 设置是否提示用户重启
        Beta.canAutoPatch = true // 设置是否自动合成补丁

        /**
         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
         */
        Beta.betaPatchListener = object : BetaPatchListener {
            override fun onPatchReceived(patchFileUrl: String) {
                Debug.out("AthenaApplication.onPatchReceived -> patchFileUrl: $patchFileUrl")
            }

            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                val progress =
                        "${Beta.strNotificationDownloading} ${if (totalLength == 0L) 0 else savedLength * 100 / totalLength}%"
                Debug.out("AthenaApplication.onDownloadReceived -> progress: $progress")
            }

            override fun onDownloadSuccess(patchFilePath: String) {
                Debug.out("AthenaApplication.onDownloadSuccess -> patchFilePath: $patchFilePath")
            }

            override fun onDownloadFailure(msg: String) {
                Debug.out("AthenaApplication.onDownloadFailure: $msg")
            }

            override fun onApplySuccess(msg: String) {
                Debug.out("AthenaApplication.onApplySuccess: $msg")
            }

            override fun onApplyFailure(msg: String) {
                Debug.out("AthenaApplication.onApplyFailure: $msg")
            }

            override fun onPatchRollback() {
                Debug.out("AthenaApplication.onPatchRollback:")
            }
        }
        return this
    }

    /**
     * 手动检查更新（用户点击操作）
     */
    fun checkUpgrade() {
        Beta.checkUpgrade()
    }

    /**
     * 自动检查更新（非用户点击操作）
     */
    fun checkUpgradeAuto() {
        Beta.checkUpgrade(false, false)
    }

    /**
     * 获取升级信息
     */
    fun getUpgradeInfo(): UpgradeInfo? = Beta.getUpgradeInfo()

    /**
     * 设置资源保存目录，默认Download文件夹
     */
    fun setDownloadDir(path: String?) {
        path ?: return
        Beta.storageDir = File(path)
    }

    /**
     * 设置通知栏大图标，状态栏小图标
     */
    fun setNotificationIconId(largeIconResId: Int, smallIconResId: Int) {
        Beta.largeIconId = largeIconResId
        Beta.smallIconId = smallIconResId
    }

    /**
     * 设置自定义升级对话框UI布局
     * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
     * 标题：beta_title，如：android:tag="beta_title"
     * 升级信息：beta_upgrade_info  如： android:tag="beta_upgrade_info"
     * 更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
     * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
     * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
     * 详见layout/dialog_upgrade.xml
     */
    fun setUpgradeDialogLayoutId(layoutId: Int) {
        Beta.upgradeDialogLayoutId = layoutId
    }

    /**
     * 设置自定义tip弹窗UI布局
     * 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
     * 标题：beta_title，如：android:tag="beta_title"
     * 提示信息：beta_tip_message 如： android:tag="beta_tip_message"
     * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
     * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
     * 详见layout/tips_dialog.xml
     */
    fun setUpgradeTipsDialogLayoutId(layoutId: Int) {
        Beta.tipsDialogLayoutId = layoutId
    }

    /**
     * 设置是否显示弹窗中的apk信息
     * 如果你使用我们默认弹窗是会显示apk信息的，如果你不想显示可以将这个接口设置为false。
     */
    fun showApkInfo(flag: Boolean) {
        Beta.canShowApkInfo = flag
    }

    /**
     * 关闭热更新能力
     * 升级SDK默认是开启热更新能力的，如果你不需要使用热更新，可以将这个接口设置为false。
     */
    fun enableHotfix(flag: Boolean) {
        Beta.enableHotfix = flag
    }

    /**
     * 如果想监听升级对话框的生命周期事件，可以通过设置OnUILifecycleListener接口
     */
    fun setUpgradeDialogLifecycleListener(listener: UILifecycleListener<UpgradeInfo>) {
        Beta.upgradeDialogLifecycleListener = listener
    }
}