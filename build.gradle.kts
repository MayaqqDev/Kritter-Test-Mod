import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("earth.terrarium.cloche") version "0.18.2"
    kotlin("jvm") version "2.2.10"
}

val kritter = "2.0.0-82-local.1"

repositories {
    cloche.librariesMinecraft()

    mavenCentral()

    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.is-immensely.gay/releases")

    cloche {
        main()

        mavenFabric()
        mavenForge()
        mavenNeoforgedMeta()
        mavenNeoforged()
    }
    mavenLocal()
}

cloche {
    metadata {
        modId = "testmod"
        name = "Example Mod"
        license = "ARR"
        description = "Hello!"
    }

    mappings {
        official()
    }

    common {

    }

    val fabric = common("fabric") {
        dependencies {
            modApi("net.fabricmc:fabric-language-kotlin:1.13.7+kotlin.2.2.21")
        }
    }

    val neoforge = common("neoforge") {
    }

    fabric("fabric:1.20.1") {
        dependsOn(fabric)
        loaderVersion = "0.18.6"
        minecraftVersion = "1.20.1"

        metadata {
            entrypoint("main") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.testmod.TestmodFabric")
            }
        }

        dependencies {
            modImplementation("invoke.kitty.kritter:kritter-1.20.1-fabric:$kritter")
        }

        includedClient()
        runs {
            client()
            server()
        }
    }

    fabric("fabric:1.21.1") {
        dependsOn(fabric)
        loaderVersion = "0.18.6"
        minecraftVersion = "1.21.1"

        metadata {
            entrypoint("main") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.testmod.TestmodFabric")
            }
        }

        dependencies {
            modImplementation("invoke.kitty.kritter:kritter-1.21.1-fabric:$kritter")
        }

        includedClient()
        runs {
            client()
            server()
        }
    }

    forge("forge:1.20.1") {
        metadata {
            modLoader = "javafml"
            loaderVersion("1")
            blurLogo = false
        }

        loaderVersion = "47.4.18"
        minecraftVersion = "1.20.1"

        dependencies {
            modImplementation("invoke.kitty.kritter:kritter-1.20.1-forge:$kritter")
            modImplementation("thedarkcolour:kotlinforforge:4.12.0")
        }

        runs {
            client()
            server()
        }
    }

    neoforge("neoforge:1.21.1") {
        metadata {
            modLoader = "javafml"
            loaderVersion("1")
            blurLogo = false
        }
        dependsOn(neoforge)
        loaderVersion = "21.1.224"
        minecraftVersion = "1.21.1"

        dependencies {
            modImplementation("invoke.kitty.kritter:kritter-1.21.1-neoforge:$kritter")
            //modImplementation("thedarkcolour:kotlinforforge:5.11.0")
        }

        runs {
            client()
            server()
        }
    }

}
kotlin {

    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_2
    }
    jvmToolchain(21)
}