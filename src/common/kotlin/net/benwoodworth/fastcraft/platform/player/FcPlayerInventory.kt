package net.benwoodworth.fastcraft.platform.player

import net.benwoodworth.fastcraft.platform.item.FcInventory
import net.benwoodworth.fastcraft.platform.item.FcInventorySlot

interface FcPlayerInventory : FcInventory {
    val storage: Sequence<FcInventorySlot>

    val helmet: FcInventorySlot

    val chestPlate: FcInventorySlot

    val leggings: FcInventorySlot

    val boots: FcInventorySlot
}
