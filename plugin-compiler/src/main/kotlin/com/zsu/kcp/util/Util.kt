package com.zsu.kcp.util

import org.jetbrains.kotlin.psi.KtNamedFunction

fun String?.capFirst(): String {
    this ?: return ""
    if (isEmpty()) return this
    if (get(0) in 'a'..'z') return replaceFirstChar { 'A' + (it - 'a') }
    return this
}

/** 模糊匹配名字，不进行匹配完整包名 */
fun KtNamedFunction.hasAnnotation(simpleName: String): Boolean {
    for (annotationEntry in annotationEntries)
        if (simpleName == annotationEntry.typeReference?.text) return true
    for (annotation in annotations)
        if (simpleName == annotation.text) return true
    return false
}