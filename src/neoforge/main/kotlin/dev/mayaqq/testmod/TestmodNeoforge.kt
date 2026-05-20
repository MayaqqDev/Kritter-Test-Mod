package dev.mayaqq.testmod

import invoke.kitty.kritter.platform.forge.modContainer
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.RegisterEvent

fun init(mod: invoke.kitty.kritter.platform.Mod) {
    mod.modContainer!!.eventBus?.addListener<RegisterEvent> {
        println("meow")
    }
}

@Mod("testmod")
class TestmodNeoforge(container: ModContainer) {
    init {
        //Testmod.init()
    }
}