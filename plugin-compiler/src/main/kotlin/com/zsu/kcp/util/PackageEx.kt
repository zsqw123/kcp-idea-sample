package com.zsu.kcp.util

import com.intellij.psi.PsiClass
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider

/** PsiClass maybe not safe enough, beacuse BodyResolveMode.PARTIAL is not quite safe! */
fun PackageMemberDeclarationProvider.getClasses(nameFilter: (String) -> Boolean = { true }): Sequence<PsiClass> =
    sequence {
        getPackageFiles().forEach { ktFile ->
            ktFile.classes.forEach { psiClass ->
                val psiName = psiClass.qualifiedName
                if (psiName != null && nameFilter(psiName)) yield(psiClass)
            }
        }
    }