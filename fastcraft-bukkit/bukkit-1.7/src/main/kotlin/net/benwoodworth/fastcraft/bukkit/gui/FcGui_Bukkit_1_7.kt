package net.benwoodworth.fastcraft.bukkit.gui

import net.benwoodworth.fastcraft.bukkit.player.FcPlayer_Bukkit
import net.benwoodworth.fastcraft.bukkit.player.bukkit
import net.benwoodworth.fastcraft.platform.gui.FcGui
import net.benwoodworth.fastcraft.platform.gui.FcGuiClick
import net.benwoodworth.fastcraft.platform.gui.FcGuiClickModifier
import net.benwoodworth.fastcraft.platform.gui.FcGuiLayout
import net.benwoodworth.fastcraft.platform.player.FcPlayer
import net.benwoodworth.fastcraft.platform.text.FcText
import net.benwoodworth.fastcraft.platform.text.FcTextConverter
import org.bukkit.Server
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.InventoryView
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import javax.inject.Inject
import javax.inject.Singleton

class FcGui_Bukkit_1_7<TLayout : FcGuiLayout>(
    override val player: FcPlayer,
    createInventory: (owner: InventoryHolder) -> Inventory,
    createLayout: (inventory: Inventory) -> TLayout,
    plugin: Plugin,
    pluginManager: PluginManager,
    fcPlayerOperations: FcPlayer.Operations,
) : FcGui_Bukkit<TLayout>, InventoryHolder,
    FcPlayer_Bukkit.Operations by fcPlayerOperations.bukkit {

    override var listener: FcGui.Listener = FcGui.Listener.Default

    private val inventory: Inventory = createInventory(this)

    override val layout: TLayout = createLayout(inventory)

    init {
        @Suppress("LeakingThis")
        pluginManager.registerEvents(InventoryListener(), plugin)
    }

    override fun open() {
        player.player.openInventory(inventory)
    }

    override fun close() {
        if (player.player.openInventory.topInventory.holder === this) {
            player.player.closeInventory()
        }
    }

    override fun getInventory(): Inventory {
        return layout.inventory
    }

    private fun InventoryEvent.isGuiSlot(rawSlot: Int): Boolean {
        return rawSlot != InventoryView.OUTSIDE && rawSlot == view.convertSlot(rawSlot)
    }

    private fun InventoryClickEvent.getGui(): FcGui<*> {
        return view.topInventory.holder as FcGui<*>
    }

    private fun InventoryClickEvent.getGuiClick(): FcGuiClick? {
        val modifiers = mutableSetOf<FcGuiClickModifier>()
            .apply {
                if (isShiftClick) {
                    add(FcGuiClickModifier.Shift)
                }

                if (click == ClickType.CONTROL_DROP) {
                    add(FcGuiClickModifier.Control)
                }

                if (click == ClickType.DOUBLE_CLICK) {
                    add(FcGuiClickModifier.DoubleClick)
                }
            }
            .toSet()

        return when {
            isLeftClick -> FcGuiClick.Primary(modifiers)
            isRightClick -> FcGuiClick.Secondary(modifiers)
            click == ClickType.MIDDLE -> FcGuiClick.Middle(modifiers)
            click == ClickType.DROP || click == ClickType.CONTROL_DROP -> FcGuiClick.Drop(modifiers)
            hotbarButton != -1 -> FcGuiClick.Number(hotbarButton, modifiers)
            else -> return null
        }
    }

    private inner class InventoryListener : Listener {
        @Suppress("unused")
        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
        private fun onInventoryClick(event: InventoryClickEvent) {
            if (event.inventory.holder !== this@FcGui_Bukkit_1_7) {
                return
            }

            val slot = event.rawSlot
            if (event.isGuiSlot(slot)) {
                event.isCancelled = true

                layout.getSlotButton(slot)?.let { button ->
                    try {
                        val click = event.getGuiClick()
                        if (click != null) {
                            button.listener.onClick(event.getGui(), button, click)
                        }
                    } catch (throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                }
            } else {
                event.isCancelled = when (event.click) {
                    // Clicks allowed outside the GUI inv
                    ClickType.LEFT,
                    ClickType.RIGHT,
                    ClickType.WINDOW_BORDER_LEFT,
                    ClickType.WINDOW_BORDER_RIGHT,
                    ClickType.MIDDLE,
                    ClickType.NUMBER_KEY,
                    ClickType.DOUBLE_CLICK,
                    ClickType.DROP,
                    ClickType.CONTROL_DROP,
                    ClickType.CREATIVE,
                    -> false

                    // Clicks disallowed
                    ClickType.SHIFT_LEFT,
                    ClickType.SHIFT_RIGHT,
                    ClickType.UNKNOWN,
                    -> true

                    // Unhandled clicks
                    else -> true
                }
            }
        }

        @Suppress("unused")
        @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
        private fun onInventoryDrag(event: InventoryDragEvent) {
            if (event.inventory.holder !== this@FcGui_Bukkit_1_7) {
                return
            }

            event.isCancelled = event.rawSlots
                .any { event.isGuiSlot(it) }
        }

        @Suppress("unused")
        @EventHandler(ignoreCancelled = true)
        private fun onInventoryClose(event: InventoryCloseEvent) {
            if (event.inventory.holder !== this@FcGui_Bukkit_1_7) {
                return
            }

            HandlerList.unregisterAll(this)
            listener.onClose()
        }

        @Suppress("unused", "UNUSED_PARAMETER")
        @EventHandler(ignoreCancelled = true)
        private fun onPluginDisable(event: PluginDisableEvent) {
            close()
        }
    }

    @Singleton
    class Factory @Inject constructor(
        private val plugin: Plugin,
        private val pluginManager: PluginManager,
        private val server: Server,
        private val fcTextConverter: FcTextConverter,
        private val fcGuiLayoutFactory: FcGuiLayout_Bukkit.Factory,
        private val fcPlayerOperations: FcPlayer.Operations,
    ) : FcGui_Bukkit.Factory,
        FcPlayer_Bukkit.Operations by fcPlayerOperations.bukkit {

        override fun createChestGui(player: FcPlayer, title: FcText?, height: Int): FcGui<FcGuiLayout.Grid> {
            val legacyTitle = title?.let {
                fcTextConverter.toLegacy(it, player.locale)
            }

            return FcGui_Bukkit_1_7(
                player,
                { owner ->
                    when (legacyTitle) {
                        null -> server.createInventory(owner, 9 * height)
                        else -> server.createInventory(owner, 9 * height, legacyTitle)
                    }
                },
                { inventory ->
                    fcGuiLayoutFactory.createGridLayout(
                        9,
                        height,
                        inventory,
                        player.locale,
                    )
                },
                plugin = plugin,
                pluginManager = pluginManager,
                fcPlayerOperations = fcPlayerOperations,
            )
        }
    }
}
