@file:JvmName("TencentHelper")

package com.zybf.math.correct.utils

/**
 * 算式工具类
 * @author junpu
 * @date 2020/10/21
 */

/**
 * 格式化腾讯云算式
 */
val String.formatFormula: String
    get() {
        val formula = if (contains("=")) substring(0, indexOf("=") + 1) else this
        return formula
            .replace(Regex("frac\\(\\d+,\\d+\\)")) {
                it.value.replace("frac(", "\\(\\frac{")
                    .replace(",", "}{")
                    .replace(")", "}\\)")
            }
//            .replace("%", "\\%")
            .replace("*", " × ")
            .replace("/", " ÷ ")
    }

/**
 * 格式化腾讯云算式（压图用）
 */
val String.formatFormulaImage: String
    get() {
        return replace("*", "×")
            .replace("/", "÷")
            .replace("%", "\\%")
            .replace(Regex("\\d+frac\\(\\d+,\\d+\\)")) {
                it.value.replace("frac(", "(")
                    .replace(",", "/")
                    .replace(")", ")")
            }
            .replace(Regex("frac\\(\\d+,\\d+\\)")) {
                it.value.replace("frac(", "")
                    .replace(",", "/")
                    .replace(")", "")
            }
    }