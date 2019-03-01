package net.benwoodworth.fastcraft.platform.item

import net.benwoodworth.fastcraft.platform.text.FcText

interface FcItemType {

    val name: FcText

    val description: FcText

    val maxAmount: Int
}
