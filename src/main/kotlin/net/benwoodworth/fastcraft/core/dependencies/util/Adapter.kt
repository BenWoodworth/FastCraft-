package net.benwoodworth.fastcraft.core.dependencies.util

/**
 * Used to adapt native implementations for use by FastCraft.
 */
abstract class Adapter<out TBase : Any>(
        val base: TBase
) {

    override fun equals(other: Any?): Boolean {
        return base == (other as? Adapter<TBase>)?.base
    }

    override fun hashCode() = base.hashCode()
}
