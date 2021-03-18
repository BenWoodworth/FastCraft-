package net.benwoodworth.fastcraft.bukkit.world

import net.benwoodworth.fastcraft.platform.world.FcItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcItemOrderComparator_Bukkit_1_7 @Inject constructor(
    fcItemOperations: FcItem.Operations,
) : FcItemOrderComparator_Bukkit,
    FcItem_Bukkit.Operations by fcItemOperations.bukkit {

    override fun compare(item0: FcItem, item1: FcItem): Int {
        @Suppress("DEPRECATION")
        return item0.material.id - item1.material.id
    }
}
