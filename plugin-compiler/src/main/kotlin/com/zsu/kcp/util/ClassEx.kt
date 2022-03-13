package com.zsu.kcp.util

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor
import org.jetbrains.kotlin.descriptors.impl.getRefinedUnsubstitutedMemberScopeIfPossible
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner

fun ClassDescriptor.methods(): Sequence<SimpleFunctionDescriptor> = sequence {
    if (this@methods !is LazyClassDescriptor) return@sequence
    yieldAll(this@methods.declaredCallableMembers
        .filter { it.kind.isReal }
        .filterIsInstance<SimpleFunctionDescriptor>()
    )
}

fun ClassDescriptor.methodNames(): Sequence<Name> = unsubstitutedMemberScope.getFunctionNames().asSequence()

fun ClassMemberDeclarationProvider.methods(nameFilter: (Name) -> Boolean = { true }): Sequence<KtNamedFunction> = sequence {
    val declarations = getDeclarations(DescriptorKindFilter.FUNCTIONS, nameFilter)
    for (declar in declarations) {
        if (declar.isValid && declar is KtNamedFunction && !declar.isLocal)
            yield(declar)
    }
}