package dev.mayaqq.testmod.config

import invoke.kitty.kritter.config.api.Config
import invoke.kitty.kritter.config.formats.Json5Format

object TestConfig : Config("testmod", Json5Format) {
}