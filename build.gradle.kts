plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
            ktlint("1.5.0").editorConfigOverride(
                mapOf(
                    "indent_size" to "4",
                    "max_line_length" to "140",
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    "ktlint_standard_property-naming" to "disabled",
                    "ktlint_standard_function-naming" to "disabled"
                )
            )
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint("1.5.0")
        }
    }
}