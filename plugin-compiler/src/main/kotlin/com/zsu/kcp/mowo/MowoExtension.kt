package com.zsu.kcp.mowo

import com.intellij.util.io.StringRef
import com.zsu.kcp.SampleFunc
import com.zsu.kcp.util.capFirst
import com.zsu.kcp.util.hasAnnotation
import com.zsu.kcp.util.methodNames
import com.zsu.kcp.util.methods
import org.jetbrains.kotlin.backend.common.serialization.findPackage
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.containingClass
import org.jetbrains.kotlin.psi.stubs.elements.KtClassElementType
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassStubImpl
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext
import org.jetbrains.kotlin.resolve.lazy.data.KtClassInfo
import org.jetbrains.kotlin.resolve.lazy.data.KtClassInfoUtil
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor

class MowoExtension : SyntheticResolveExtension {
    override fun generateSyntheticClasses(
        thisDescriptor: PackageFragmentDescriptor,
        name: Name,
        ctx: LazyClassContext,
        declarationProvider: PackageMemberDeclarationProvider,
        result: MutableSet<ClassDescriptor>
    ) {
//        super.generateSyntheticClasses(thisDescriptor, name, ctx, declarationProvider, result)

//        if (name.asString() == "MyFunMowo") println("smdasdmsalkdmas")
//        val syntheticClasses: ArrayList<ClassDescriptor> = arrayListOf()
//        result.forEach { classDesc ->
//            classDesc.methods().forEach { memberDesc ->
//                genNewClassForMethod(thisDescriptor, memberDesc, ctx)?.let {
//                    syntheticClasses.add(it)
//                }
//            }
//        }
//        if (syntheticClasses.isNotEmpty()) result.addAll(syntheticClasses)

//        val genClass = ClassDescriptorImpl(
//            thisDescriptor, Name.identifier("Mowo"),
//            Modality.FINAL, ClassKind.CLASS, emptyList(), SourceElement.NO_SOURCE, false, ctx.storageManager
//        )
//        val constructor = ClassConstructorDescriptorImpl.create(genClass, Annotations.EMPTY, true, SourceElement.NO_SOURCE)
//        constructor.initialize(emptyList(), DescriptorVisibilities.PUBLIC)
//        genClass.initialize(
//            MemberScope.Empty, emptySet(),
//            DescriptorFactory.createPrimaryConstructorForObject(genClass, genClass.source)
//        )
//        result.add(genClass)


//        val testClass = ClassDescriptorImpl(
//            thisDescriptor, // 在哪个 package 下生成
//            Name.identifier("MowoClass"), // 名字
//            Modality.FINAL,
//            ClassKind.CLASS,
//            emptyList(), // super Type
//            SourceElement.NO_SOURCE,
//            false,
//            ctx.storageManager
//        )
//
//        testClass.initialize(
//            MemberScope.Empty,
//            emptySet(),
//            DescriptorFactory.createPrimaryConstructorForObject(testClass, testClass.source)
//        )
//        result.add(testClass)
    }

    override fun getSyntheticNestedClassNames(thisDescriptor: ClassDescriptor): List<Name> {
        return getAllMowoName(thisDescriptor)
    }

    private fun getAllMowoName(classDescriptor: ClassDescriptor): List<Name> {
        return classDescriptor.methodNames().map {
            it.asString().capFirst() + "Mowo"
        }.map {
            Name.identifier(it)
        }.toList()
//        return classDescriptor.methods().filter {
//            it.annotations.hasAnnotation(FqName(SampleFunc::class.java.canonicalName))
//        }.map {
//            it.name.asString().capFirst() + "Mowo"
//        }.map {
//            Name.identifier(it)
//        }.toList()
    }

    override fun generateSyntheticClasses(
        thisDescriptor: ClassDescriptor,
        name: Name,
        ctx: LazyClassContext,
        declarationProvider: ClassMemberDeclarationProvider,
        result: MutableSet<ClassDescriptor>
    ) {
        super.generateSyntheticClasses(thisDescriptor, name, ctx, declarationProvider, result)
//        if (name.asString() != "MyFunMowo") return
//        thisDescriptor.methods().forEach { memberDesc ->
//            genNewClassForMethod(thisDescriptor, memberDesc, ctx)?.let {
//                syntheticClasses.add(it)
//            }
//        }
        declarationProvider.methods().forEach { func ->
            genNewClassForMethod(thisDescriptor, func, ctx, result)
        }
    }


    private fun genNewClassForMethod(
        parentDescriptor: DeclarationDescriptor,
        method: KtNamedFunction,
        ctx: LazyClassContext,
        result: MutableSet<ClassDescriptor>,
    ): ClassDescriptor? {
        if (!method.hasAnnotation(SampleFunc::class.java.simpleName)) return null
        val className = Name.identifier(method.name.capFirst() + "Mowo")
//        val ktFile = method.findPsi()?.containingFile as? KtFile ?: return null
        val methodParentClass = method.containingClass() ?: return null
        val ktClassInfo = KtClassInfoUtil.createClassOrObjectInfo(methodParentClass)
        val parentPsi = parentDescriptor.findPsi() ?: return null
        val scope = ctx.declarationScopeProvider.getResolutionScopeForDeclaration(parentPsi)
        val descriptor = SyntheticClassOrObjectDescriptor(
            ctx, methodParentClass, parentDescriptor, className, SourceElement.NO_SOURCE,
            scope, Modality.FINAL, DescriptorVisibilities.PUBLIC, Annotations.EMPTY, DescriptorVisibilities.PUBLIC,
            ClassKind.CLASS, false
        ).apply { initialize() }
        val funcBean = MowoFunc(methodParentClass, method.name.capFirst())
        return MowoFunc.refreshCacheAndReturn(result, descriptor, funcBean)
    }

    private fun genNewClassForMethod(
        parentDescriptor: DeclarationDescriptor,
        method: SimpleFunctionDescriptor,
        ctx: LazyClassContext,
    ): ClassDescriptor? {
        if (!method.annotations.hasAnnotation(FqName(SampleFunc::class.java.canonicalName))) return null
        val className = Name.identifier(method.name.asString().capFirst() + "Mowo")
//        val ktFile = method.findPsi()?.containingFile as? KtFile ?: return null
        val methodParentClass = method.containingDeclaration.findPsi() as? KtClassOrObject ?: return null
        val ktClassInfo = KtClassInfoUtil.createClassOrObjectInfo(methodParentClass)
//        val mowoClassStub = MowoKtClass.genStubFromMowoMethod(ktFile.greenStub, method) ?: return null
//        val mowoKtClass = MowoKtClass(mowoClassStub)
//        val mowoClassInfo = KtClassInfoUtil.createClassOrObjectInfo(mowoKtClass)
        val scope = ctx.declarationScopeProvider.getResolutionScopeForDeclaration(methodParentClass)
//
//        val constructor = ClassConstructorDescriptorImpl.create(genClass, Annotations.EMPTY, true, method.source)
//        constructor.initialize(emptyList(), DescriptorVisibilities.PUBLIC)
//        genClass.initialize(
//            packageFragmentDescriptor.getMemberScope(), setOf(constructor),
//            DescriptorFactory.createPrimaryConstructorForObject(genClass, method.source)
//        )
//        return genClass

//        val mowoClassDescriptor = LazyClassDescriptor(ctx, parentDescriptor, mowoKtClass.nameAsSafeName, mowoClassInfo, false)
        return SyntheticClassOrObjectDescriptor(
            ctx, methodParentClass, parentDescriptor, className, SourceElement.NO_SOURCE,
            scope, Modality.FINAL, DescriptorVisibilities.PUBLIC, Annotations.EMPTY, DescriptorVisibilities.PUBLIC,
            ClassKind.CLASS, false
        ).apply { initialize() }
//        return mowoClassDescriptor
//        return null
    }
}