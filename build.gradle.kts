buildscript {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    }
}

subprojects {
    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }
}