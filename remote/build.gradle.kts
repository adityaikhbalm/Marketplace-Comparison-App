import Dependencies.Network

plugins {
    kotlinLibrary
    kotlin(kotlinKapt)
}

dependencies {
    implementAll(Network.components)
    kapt(Network.AnnotationProcessor.moshi)
}
