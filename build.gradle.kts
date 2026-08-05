plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.navigationSafeargs) apply false
}
