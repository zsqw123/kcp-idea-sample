package com.zsu.kcp.util

import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.types.KotlinType

fun KtNamedFunction.returnType(bctx: BindingContext): KotlinType {
    return typeReference.safeType(bctx)
}