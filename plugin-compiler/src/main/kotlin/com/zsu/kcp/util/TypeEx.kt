package com.zsu.kcp.util

import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.bindingContextUtil.getAbbreviatedTypeOrType
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeUtils
import org.jetbrains.kotlin.types.typeUtil.builtIns

object TypeEx {
    val builtIn: KotlinBuiltIns = TypeUtils.DONT_CARE.builtIns
}

fun KtTypeReference?.safeType(bctx: BindingContext): KotlinType {
    val unitType = TypeEx.builtIn.unitType
    this ?: return unitType
    val resolvedType = getAbbreviatedTypeOrType(bctx) ?: return unitType
    return resolvedType.unwrap()
}