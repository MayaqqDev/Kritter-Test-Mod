package dev.mayaqq.testmod

import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.block.BlockRenderType
import invoke.kitty.kritter.registry.block.block
import invoke.kitty.kritter.registry.block.renderType
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.SoundType

class TestRegistrar : Registrar<Block> by Registrar("testmod", Registries.BLOCK) {

    val TestBlok by block("blok", ::Block) {
        properties {
            strength(13f, 13f)
            sound(SoundType.ROOTED_DIRT)
        }
        renderType = BlockRenderType.CUTOUT_MIPPED
    }

    val TestBlok2 by block("blok2", ::SlabBlock) {
        properties {
            sound(SoundType.AMETHYST_CLUSTER)
        }
    }
}