package com.zsu.kcp.mowo

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.psi.KtClass
import java.util.concurrent.ConcurrentHashMap

data class MowoFunc(
    val parentClass: KtClass,
    val memthodName: String,
) {
    companion object {
        private val cacheMap = ConcurrentHashMap<MowoFunc, ClassDescriptor>()
        fun refreshCacheAndReturn(
            res: MutableSet<ClassDescriptor>, newDescriptor: ClassDescriptor, mowoFunc: MowoFunc
        ): ClassDescriptor {
            val oldDescriptor = cacheMap[mowoFunc]
            if (oldDescriptor != null) {
                cacheMap.remove(mowoFunc)
            }
            cacheMap[mowoFunc] = newDescriptor
            res.add(newDescriptor)
            return newDescriptor
        }
    }
}