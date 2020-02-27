package net.benwoodworth.fastcraft.bukkit

import dagger.Component
import net.benwoodworth.fastcraft.FastCraftFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [BukkitFastCraftModule::class])
interface BukkitFastCraftFactory : FastCraftFactory