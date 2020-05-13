package net.benwoodworth.fastcraft.bukkit.item

import net.benwoodworth.fastcraft.platform.item.FcItemStack
import net.benwoodworth.fastcraft.platform.item.FcMaterial
import net.benwoodworth.fastcraft.platform.text.FcText
import org.bukkit.Server
import org.bukkit.inventory.ItemStack
import javax.inject.Inject
import javax.inject.Singleton

open class BukkitFcItemStack_1_7(
    override val bukkitItemStack: ItemStack,
    protected val materials: FcMaterial.Factory,
    protected val textFactory: FcText.Factory,
) : BukkitFcItemStack {
    override val type: FcMaterial
        get() = materials.fromMaterialData(bukkitItemStack.data)

    override val amount: Int
        get() = bukkitItemStack.amount

    override val name: FcText
        get() = bukkitItemStack
            .takeIf { it.hasItemMeta() }
            ?.itemMeta
            ?.takeIf { it.hasDisplayName() }
            ?.displayName
            ?.let { textFactory.create(it) }
            ?: type.blockName

    override val lore: List<FcText>
        get() = bukkitItemStack
            .takeIf { it.hasItemMeta() }
            ?.itemMeta
            ?.takeIf { it.hasLore() }
            ?.lore
            ?.map { textFactory.create(it ?: "") }
            ?: emptyList()

    override val hasMetadata: Boolean
        get() = bukkitItemStack.hasItemMeta()

    override fun toBukkitItemStack(): ItemStack {
        return bukkitItemStack.clone()
    }

    override fun equals(other: Any?): Boolean {
        return other is FcItemStack && bukkitItemStack == other.bukkitItemStack
    }

    override fun hashCode(): Int {
        return bukkitItemStack.hashCode()
    }

    @Singleton
    open class Factory @Inject constructor(
        protected val materials: FcMaterial.Factory,
        protected val textFactory: FcText.Factory,
        protected val server: Server,
    ) : BukkitFcItemStack.Factory {
        override fun create(material: FcMaterial, amount: Int): FcItemStack {
            return create(material.toItemStack(amount))
        }

        override fun copyItem(itemStack: FcItemStack, amount: Int): FcItemStack {
            try {
                if (amount == itemStack.amount) {
                    return itemStack
                }

                val bukkitItemStack = itemStack.toBukkitItemStack()
                bukkitItemStack.amount = amount

                return create(bukkitItemStack)
            } catch (e: AssertionError) {
                System.err.println("""
                    Share this with Kepler:
                    - itemStack = ${itemStack.bukkitItemStack}
                    - amount = $amount
                """.trimIndent())
                throw e
            }
        }

        override fun parseOrNull(item: String, amount: Int): FcItemStack? {
            val materialId: String
            val data: String?

            when (val dataIndex = item.indexOf('{')) {
                -1 -> {
                    materialId = item
                    data = null
                }
                else -> {
                    materialId = item.substring(0 until dataIndex)
                    data = item.substring(dataIndex)
                }
            }

            val material = materials.parseOrNull(materialId) ?: return null
            val itemStack = material.toItemStack(amount)

            if (data != null) {
                server.unsafe.modifyItemStack(itemStack, data)
            }

            return create(itemStack)
        }

        override fun create(itemStack: ItemStack): FcItemStack {
            return BukkitFcItemStack_1_7(
                bukkitItemStack = itemStack,
                materials = materials,
                textFactory = textFactory
            )
        }
    }
}
