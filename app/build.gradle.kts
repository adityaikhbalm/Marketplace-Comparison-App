import Dependencies.AndroidX
import Dependencies.DI
import Dependencies.Network
import Dependencies.View

plugins {
    androidApplication
    kotlin(kotlinAndroid)
    kotlin(kotlinAndroidExtension)
    kotlin(kotlinKapt)
    daggerHilt
}

android {
    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Config.Version.minSdkVersion)
        compileSdkVersion(Config.Version.compileSdkVersion)
        targetSdkVersion(Config.Version.targetSdkVersion)
        versionCode = Config.Version.versionCode
        versionName = Config.Version.versionName
        multiDexEnabled = Config.isMultiDexEnabled
        testInstrumentationRunner = Config.Android.testInstrumentationRunner
    }

    buildTypes {
        named(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    configurations.all {
        resolutionStrategy {
            exclude("org.jetbrains.kotlinx", "kotlinx-coroutines-debug")
        }
    }
}

dependencies {
    implementation(fileTree(configFileTree))
    implementation(project(ProjectLib.network))

    implementAll(View.components)
    implementation(Network.moshi)
    implementation(DI.daggerHiltAndroid)

    AndroidX.run {
        implementation(activity)
        implementation(coreKtx)
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
        implementation(navigationDFM)
        implementation(multiDex)
    }

    kapt(DI.AnnotationProcessor.daggerHiltAndroid)
}
