plugins {
    embeddedKotlin("multiplatform")
    id("maven-publish")
}

group = "io.scif.api"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        /*
        Kotlin/JS projects can target two different execution environments:

        * Browser for client-side scripting in browsers
        * Node.js for running JavaScript code outside of a browser, for example,
          for server-side scripting.

        To define the target execution environment for a Kotlin/JS project,
        add the js section with browser {} or nodejs {} inside.

        https://kotlinlang.org/docs/js-project-setup.html#execution-environments
         */
        nodejs { }
        /*
        The instruction binaries.executable() explicitly instructs the Kotlin
        compiler to emit executable .js files. This instruction is explicitly
        required if you are working with the Kotlin/JS IR compiler.
         */
        binaries.executable()
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
            api("org.jetbrains.kotlinx:kotlinx-io-core:0.3.0")
          }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(npm("base-64", "1.0.0"))
            }
        }
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
