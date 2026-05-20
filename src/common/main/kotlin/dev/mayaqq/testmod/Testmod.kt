package dev.mayaqq.testmod

import invoke.kitty.kritter.events.InteractionEvents
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.Platform
import net.minecraft.world.InteractionResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.ServiceLoader
import javax.imageio.ImageIO
import javax.imageio.spi.IIORegistry
import javax.imageio.spi.ImageInputStreamSpi
import javax.imageio.spi.ImageOutputStreamSpi
import javax.imageio.spi.ImageReaderSpi
import javax.imageio.spi.ImageWriterSpi

object Testmod : Logger by LoggerFactory.getLogger("testmod") {
    fun init(mod: Mod) {
        info("${mod.modId} INIT")
        InteractionEvents.UseBlock.subscribe { player, level, hand, hit ->

            InteractionResult.PASS
        }
    }
}

