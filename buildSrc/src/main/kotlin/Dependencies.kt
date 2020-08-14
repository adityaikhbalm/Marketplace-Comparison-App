const val kotlinAndroid: String = "android"
const val kotlinAndroidExtension: String = "android.extensions"
const val kotlinKapt: String = "kapt"
const val ktLintVersion: String = "0.37.2"

object Config {
    object Version {
        private const val versionMajor = 1
        private const val versionMinor = 0
        private const val versionPatch = 0

        const val versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
        const val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

        const val compileSdkVersion = 30
        const val targetSdkVersion = 30
        const val minSdkVersion = 21
    }

    const val isMultiDexEnabled: Boolean = true

    object Android {
        const val applicationId: String = "com.adityaikhbalm.pricehunter"
        const val testInstrumentationRunner: String = "androidx.test.runner.AndroidJUnitRunner"
    }
}

interface Libraries {
    val components: List<String>
}

object Dependencies {
    object AndroidX : Libraries {
        object Version {
            const val coreKtx: String = "1.3.1"
            const val lifeCycle: String = "2.3.0-alpha03"
            const val activity: String = "1.2.0-alpha05"
            const val navigation: String = "2.3.0"
            const val multidex: String = "2.0.1"
        }

        const val coreKtx: String = "androidx.core:core-ktx:${Version.coreKtx}"
        const val activity: String = "androidx.activity:activity-ktx:${Version.activity}"
        const val lifeCycleRuntime: String =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifeCycle}"
        const val lifeCycleCommon: String =
            "androidx.lifecycle:lifecycle-common-java8:${Version.lifeCycle}"
        const val viewModel: String =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifeCycle}"
        const val viewModelState: String =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.lifeCycle}"
        const val navigationFragmentKtx: String =
            "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val navigationUiKtx: String =
            "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
        const val navigationDFM: String =
            "androidx.navigation:navigation-dynamic-features-fragment:${Version.navigation}"
        const val multiDex: String = "androidx.multidex:multidex:${Version.multidex}"

        override val components: List<String>
            get() = listOf(
                coreKtx, activity, lifeCycleRuntime, lifeCycleCommon, viewModel,
                viewModelState, navigationFragmentKtx, navigationUiKtx, multiDex
            )
    }

    object View : Libraries {
        object Version {
            const val materialComponent: String = "1.2.0-alpha04"
            const val shimmerLayout: String = "0.5.0"
            const val appCompat: String = "1.2.0-rc01"
            const val constraintLayout: String = "2.0.0-beta6"
            const val fragment: String = "1.2.5"
            const val recyclerView: String = "1.1.0"
            const val coil: String = "0.11.0"
            const val swipeRefreshLayout: String = "1.1.0-rc01"
            const val neumorphism: String = "0.1.11"
            const val waveLoading: String = "0.3.5"
        }

        const val appCompat: String = "androidx.appcompat:appcompat:${Version.appCompat}"
        const val fragment: String = "androidx.fragment:fragment-ktx:${Version.fragment}"
        const val materialComponent: String =
            "com.google.android.material:material:${Version.materialComponent}"
        const val shimmerLayout: String =
            "com.facebook.shimmer:shimmer:${Version.shimmerLayout}"
        const val constraintLayout: String =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val recyclerView: String =
            "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        const val coil: String = "io.coil-kt:coil:${Version.coil}"
        const val swipeRefreshLayout: String =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"
        const val neumorphism: String = "com.github.fornewid:neumorphism:${Version.neumorphism}"
        const val waveLoading: String = "me.itangqi.waveloadingview:library:${Version.waveLoading}"

        override val components: List<String>
            get() = listOf(appCompat, fragment, materialComponent, constraintLayout)
    }

    object FlowBinding {
        private const val flowBindingVersion: String = "0.12.0"
        const val android: String =
            "io.github.reactivecircus.flowbinding:flowbinding-android:$flowBindingVersion"
        const val swipeRefresh: String =
            "io.github.reactivecircus.flowbinding:flowbinding-swiperefreshlayout:$flowBindingVersion"
        const val lifecycle: String =
            "io.github.reactivecircus.flowbinding:flowbinding-lifecycle:$flowBindingVersion"
    }

    object Network : Libraries {
        object Version {
            const val okHttp: String = "4.8.0"
            const val retrofit: String = "2.9.0"
            const val moshi: String = "1.9.3"
        }

        object AnnotationProcessor {
            const val moshi: String = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
        }

        private const val okhttp: String = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
        private const val loggingInterceptor: String =
            "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        private const val retrofit: String = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        private const val retrofitMoshi: String =
            "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        const val moshi: String = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"

        override val components: List<String>
            get() = listOf(okhttp, loggingInterceptor, retrofit, retrofitMoshi, moshi)
    }

    object DI {
        object Version {
            const val javaxInject: String = "1"
            const val daggerHiltAndroid: String = "2.28.3-alpha"
        }

        object AnnotationProcessor {
            const val daggerHiltAndroid: String =
                "com.google.dagger:hilt-android-compiler:${Version.daggerHiltAndroid}"
        }

        const val javaxInject: String = "javax.inject:javax.inject:${Version.javaxInject}"
        const val daggerHiltAndroid: String =
            "com.google.dagger:hilt-android:${Version.daggerHiltAndroid}"
    }

    object Coroutines : Libraries {
        object Version {
            const val coroutines: String = "1.3.8"
        }

        const val core: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        const val android: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

        override val components: List<String> get() =  listOf(core, android)
    }

    object Test {
        object Version {
            const val jUnit: String = "5.6.2"
            const val runner: String = "1.1.0"
            const val rules: String = "1.3.1"
            const val testExt: String = "1.1.1"
            const val espresso: String = "3.2.0"
            const val fragment: String = "1.2.5"
            const val truth: String = "1.0.1"
            const val mockWebServer: String = "4.8.0"
            const val coroutineTest: String = "1.3.8"
        }

        private const val unitApi: String = "org.junit.jupiter:junit-jupiter-api:${Version.jUnit}"
        private const val unitEngine: String = "org.junit.jupiter:junit-jupiter-engine:${Version.jUnit}"
        private const val unitParam: String = "org.junit.jupiter:junit-jupiter-params:${Version.jUnit}"



        const val runner: String = "androidx.test:runner:${Version.runner}"
        const val fragmentTesting: String = "androidx.fragment:fragment-testing:${Version.fragment}"
        const val testExt: String = "androidx.test.ext:junit:${Version.testExt}"
        const val espresso: String = "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val rules: String = "androidx.test:rules:${Version.rules}"
        const val truth: String = "com.google.truth:truth:${Version.truth}"
        const val mockWebServer: String =
            "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
        const val coroutinesTest: String =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutineTest}"

        val unitTest: List<String> = listOf(unitApi, unitEngine, unitParam)
//        val instrumentTest: List<String> = listOf(core, android)
    }

    object Annotation {
        object Version {
            const val annotation = "1.1.0"
        }
        const val annotation = "androidx.annotation:annotation:${Version.annotation}}"
    }
}

object ProjectLib {
    const val app: String = ":app"
    const val core: String = ":core"
    const val presentation: String = ":presentation"
    const val domain: String = ":libraries:domain"
    const val data: String = ":libraries:data"
    const val remote: String = ":libraries:remote"
    const val recipe: String = ":features:recipes:recipe"
    const val recipeDetail: String = ":features:recipes:recipeDetail"
    const val stepDetail: String = ":features:recipes:stepDetail"
    const val recipeModel: String = ":features:recipes:model"
    const val views: String = ":common:views"
    const val videoPlayer: String = ":features:videoPlayer"
    const val commonTest: String = ":common:common-test"
    const val network: String = ":network"
}