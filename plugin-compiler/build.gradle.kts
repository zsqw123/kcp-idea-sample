import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
    maven("https://www.jetbrains.com/intellij-repository/releases/")
}
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
dependencies {
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin-api")
//    compileOnly("com.jetbrains.intellij.platform:core:213.6777.52")
    compileOnly("org.jetbrains.kotlin:kotlin-compiler")
    kapt("com.google.auto.service:auto-service:1.0.1")
    compileOnly("com.google.auto.service:auto-service-annotations:1.0.1")
}