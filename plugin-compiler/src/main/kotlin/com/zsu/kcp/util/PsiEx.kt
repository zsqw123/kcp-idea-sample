package com.zsu.kcp.util

import com.intellij.psi.PsiModifierListOwner

fun PsiModifierListOwner.containsAnnotaion(annotationQN: String): Boolean {
    for (a in annotations) if (a.qualifiedName == annotationQN) return true
    return false
}

//fun List<>