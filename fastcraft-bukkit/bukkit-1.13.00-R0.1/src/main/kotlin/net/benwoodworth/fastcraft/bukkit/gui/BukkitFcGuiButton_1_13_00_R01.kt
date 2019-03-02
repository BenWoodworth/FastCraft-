package net.benwoodworth.fastcraft.bukkit.gui

import net.benwoodworth.fastcraft.bukkit.bukkit
import net.benwoodworth.fastcraft.bukkit.item.BukkitFcItemConverter
import net.benwoodworth.fastcraft.events.HandlerSet
import net.benwoodworth.fastcraft.platform.gui.FcGuiClickEvent
import net.benwoodworth.fastcraft.platform.item.FcItem
import net.benwoodworth.fastcraft.platform.text.FcLocale
import org.bukkit.inventory.Inventory

class BukkitFcGuiButton_1_13_00_R01(
    private val inventory: Inventory,
    private val locale: FcLocale,
    private val slotIndex: Int,
    private val itemConverter: BukkitFcItemConverter
) : BukkitFcGuiButton {

    override val onClick: HandlerSet<FcGuiClickEvent> = HandlerSet()

    override var item: FcItem? = null
        set(value) {
            field = value

            with(itemConverter) {
                inventory.setItem(slotIndex, item?.bukkit?.toItemStack(locale))
            }
        }
}