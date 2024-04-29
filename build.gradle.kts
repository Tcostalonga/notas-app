buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.navigationSafeargs) apply false
    alias(libs.plugins.ktlint) apply true
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
