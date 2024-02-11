rootProject.name = "PriceListGenerator"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion
    }
}

include("excel-file-processing-service")
include("excel-dsl")
