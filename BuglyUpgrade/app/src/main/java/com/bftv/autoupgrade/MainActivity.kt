package com.bftv.autoupgrade

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.bftv.upgrade.bugly.UpgradeManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UpgradeManager.checkUpgradeAuto()

        btnUpgrade.setOnClickListener {
            UpgradeManager.checkUpgrade()
        }
        btnInfo.setOnClickListener {
            loadUpgradeInfo(textInfo)
        }
    }


    private fun loadUpgradeInfo(textView: TextView?) {
        /***** 获取升级信息  */
        val upgradeInfo = UpgradeManager.getUpgradeInfo()

        if (upgradeInfo == null) {
            textView?.text = "无升级信息"
            return
        }

        val info = StringBuilder()
        info.append("id: ").append(upgradeInfo.id).append("\n")
        info.append("标题: ").append(upgradeInfo.title).append("\n")
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n")
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n")
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n")
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n")
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n")
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n")
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n")
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n")
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n")
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n")
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType)

        textView?.text = info
    }
}
