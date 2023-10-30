import de.undercouch.gradle.tasks.download.Download
import magik.github
import org.gradle.kotlin.dsl.support.listFilesOrdered

plugins {
    embeddedKotlin("multiplatform")
    `maven-publish`
    id("elect86.magik") version "0.3.3"
    embeddedKotlin("plugin.serialization")
    embeddedKotlin("plugin.assignment")
    id("de.undercouch.download") version "5.5.0"
}

assignment {
    annotation("kotlin.KotlinAssignmentOverloadTarget")
}

group = "io.scif.api"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    github("kotlin-graphics/mary")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
        hostOs == "Linux" && isArm64 -> linuxArm64("native")
        hostOs == "Linux" && !isArm64 -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.6.0")
                implementation("kotlin.graphics:unsigned:3.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.scijava:scijava-common:2.77.0")
            }
        }
        val jvmTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

tasks {
    val downloadResources by registering(Download::class) {
        val scifio = listOf("test-png", "test-avi", "kidney_TFl", /*"test-dicom"*/).map { "https://samples.scif.io/$it.zip" }
        src(scifio + "https://www.rubomedical.com/dicom_files/dicom_viewer_0002.zip")
        dest(layout.buildDirectory.file("tmp/assets"))
    }
    val downloadAndUnzipResources by registering(Copy::class) {
        dependsOn(downloadResources)
        for(file in downloadResources.get().dest.listFilesOrdered())
            from(zipTree(file))
        into(layout.buildDirectory.file("resources"))
    }
}