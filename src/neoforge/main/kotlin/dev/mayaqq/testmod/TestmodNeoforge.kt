package dev.mayaqq.testmod

import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod

@Mod("testmod")
class TestmodNeoforge(container: ModContainer) {
    init {
        Testmod.init()
    }
}