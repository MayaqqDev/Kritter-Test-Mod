import earth.terrarium.cloche.ClocheExtension
import earth.terrarium.cloche.api.target.FabricTarget
import earth.terrarium.cloche.api.target.ForgeLikeTarget
import earth.terrarium.cloche.api.target.MinecraftTarget
import net.peanuuutz.tomlkt.TomlArray
import net.peanuuutz.tomlkt.TomlLiteral
import net.peanuuutz.tomlkt.TomlTable
import net.peanuuutz.tomlkt.buildTomlArray
import net.peanuuutz.tomlkt.buildTomlTable
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("earth.terrarium.cloche") version "0.18.2"
    kotlin("jvm") version "2.2.10"
}

val kritter = "2.0.0-110"

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
        dependencies {
            implementation("invoke.kitty:nullevt:2.2.8")
        }
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
            modLoader = "kritter"
            loaderVersion {
                start = "1"
                end = "200"
            }
            blurLogo = false

            modProperty("kritter", mapOf(
                "entrypoints" to mapOf(
                    "init" to "dev.mayaqq.testmod.Testmod::init"
                )
            ))
        }

        loaderVersion = "47.4.18"
        minecraftVersion = "1.20.1"

        dependencies {
            modImplementation(skipIncludeTransformation("invoke.kitty.kritter:kritter-1.20.1-forge:$kritter"))
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
            loaderVersion {
                start = "0.0.1"
                end = "9999.9.9"
            }
            blurLogo = false
        }
        dependsOn(neoforge)
        loaderVersion = "21.1.224"
        minecraftVersion = "1.21.1"

        dependencies {
            modImplementation(skipIncludeTransformation("invoke.kitty.kritter:kritter-1.21.1-neoforge:$kritter"))
            implementation("thedarkcolour:kotlinforforge:5.11.0")

        }

        runs {
            client()
            server()
        }

        entrypoint("init", "dev.mayaqq.testmod.TestmodNeoforgeKt::init")
    }

    entrypoint("init", "dev.mayaqq.testmod.Testmod::init")
    entrypoint("registrar", "dev.mayaqq.testmod.TestRegistrar")
    entrypoint("config", "dev.mayaqq.testmod.config.TestConfig")
}

kotlin {

    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_2
    }
    jvmToolchain(21)
}



fun ClocheExtension.entrypoint(name: String, vararg definitions: String) {
    for (target in targets)
        target.entrypoint(name, metadata.modId.get(), *definitions)
}

fun MinecraftTarget.entrypoint(name: String, modid: String, vararg definitions: String) {
    val FABRIC_SPECIAL_EPS = setOf("init", "lateinit", "client", "server", "registrar",
        "config", "network", "model-loading-plugin", "model-loading-plugin-preparable")

    when (this) {
        is FabricTarget ->
            metadata {
                for (d in definitions)
                    entrypoint(if (name in FABRIC_SPECIAL_EPS) "kritter:$name" else name, d)
            }
        is ForgeLikeTarget ->
            metadata {
                withToml {
                    withElement {
                        buildTomlTable {
                            elements(this@withElement)
                            val oldprops = this@withElement["modproperties"] as? TomlTable
                            element("modproperties", buildTomlTable {
                                oldprops?.let(::elements)
                                val oldmodprops = oldprops?.get(modid) as? TomlTable
                                element(modid, buildTomlTable {
                                    oldmodprops?.let(::elements)
                                    val oldKritter = oldmodprops?.get("kritter") as? TomlTable
                                    element("kritter", buildTomlTable {
                                        oldKritter?.let(::elements)
                                        val oldEntrypoints = oldKritter?.get("entrypoints") as? TomlTable
                                        element("entrypoints", buildTomlTable {
                                            oldEntrypoints?.let(::elements)
                                            var oldNamed = oldEntrypoints?.get(name)
                                            if (oldNamed is TomlLiteral)
                                                oldNamed = TomlArray(oldNamed)
                                            element(name, buildTomlArray {
                                                if (oldNamed != null) elements(oldNamed as TomlArray)
                                                for (def in definitions)
                                                    element(TomlLiteral(def))
                                            })
                                        })
                                    })
                                })
                            })
                        }
                    }
                }
            }
    }
}