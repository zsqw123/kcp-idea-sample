package com.zsu.kcp.mowo

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import com.zsu.kcp.util.capFirst
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor
import org.jetbrains.kotlin.descriptors.containingPackage
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassStubImpl

class MowoKtClass(mowoClassStub: KotlinClassStub) : KtClass(mowoClassStub) {
    companion object {
        fun genStubFromMowoMethod(
            parent: StubElement<out PsiElement>?,
            methodDesc: SimpleFunctionDescriptor,
        ): KotlinClassStub? {
            val mowoPackageQName = methodDesc.containingPackage()?.asString() ?: return null
            val mowoClassName = methodDesc.name.asString().capFirst() + "Mowo"
            val mowoClassQName = mowoPackageQName + mowoClassName
            val stub = KotlinClassStubImpl(
                type = KtStubElementTypes.CLASS,
                parent = parent,
                qualifiedName = StringRef.fromString(mowoClassQName),
                name = StringRef.fromString(mowoClassName), emptyArray(),
                isInterface = false, isEnumEntry = false, isLocal = false, isTopLevel = true
            )
            return stub
        }
    }
}