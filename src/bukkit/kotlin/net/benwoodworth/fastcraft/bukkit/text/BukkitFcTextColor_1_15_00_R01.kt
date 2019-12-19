package net.benwoodworth.fastcraft.bukkit.text

import net.benwoodworth.fastcraft.platform.text.FcTextColor
import org.bukkit.ChatColor

class BukkitFcTextColor_1_15_00_R01(
    override val id: String,
    override val chatColor: ChatColor
) : BukkitFcTextColor {
    override fun equals(other: Any?): Boolean {
        return other is FcTextColor && id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}