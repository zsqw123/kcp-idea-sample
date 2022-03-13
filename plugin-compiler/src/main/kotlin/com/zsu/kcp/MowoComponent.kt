package com.zsu.kcp

import com.google.auto.service.AutoService
import com.intellij.mock.MockProject
import com.zsu.kcp.mowo.MowoExtension
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension

@AutoService(ComponentRegistrar::class)
class MowoComponent : ComponentRegistrar {
    override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
        SyntheticResolveExtension.registerExtension(project, MowoExtension())
    }
}