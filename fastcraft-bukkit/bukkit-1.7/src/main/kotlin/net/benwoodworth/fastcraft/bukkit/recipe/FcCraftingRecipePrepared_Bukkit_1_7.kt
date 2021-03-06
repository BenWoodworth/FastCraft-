package net.benwoodworth.fastcraft.bukkit.recipe

import net.benwoodworth.fastcraft.bukkit.world.create
import net.benwoodworth.fastcraft.platform.recipe.FcCraftingRecipe
import net.benwoodworth.fastcraft.platform.recipe.FcCraftingRecipePrepared
import net.benwoodworth.fastcraft.platform.recipe.FcIngredient
import net.benwoodworth.fastcraft.platform.world.FcItemStack
import net.benwoodworth.fastcraft.util.CancellableResult
import org.bukkit.Achievement
import org.bukkit.Material
import org.bukkit.Server
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.CraftingInventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import javax.inject.Inject
import javax.inject.Singleton

open class FcCraftingRecipePrepared_Bukkit_1_7(
    protected val player: Player,
    override val recipe: FcCraftingRecipe,
    override val ingredients: Map<FcIngredient, FcItemStack>,
    private val ingredientRemnants: List<FcItemStack>,
    override val resultsPreview: List<FcItemStack>,
    private val preparedCraftingView: InventoryView,
    private val fcItemStackFactory: FcItemStack.Factory,
    private val server: Server,
) : FcCraftingRecipePrepared_Bukkit {
    private var craftCalled = false

    override fun craft(): CancellableResult<List<FcItemStack>> {
        require(!craftCalled) { "Only callable once" }
        craftCalled = true

        val craftingInventory = preparedCraftingView.topInventory as CraftingInventory

        val craftEvent = CraftItemEvent(
            craftingInventory.recipe!!,
            preparedCraftingView,
            InventoryType.SlotType.RESULT,
            9,
            ClickType.SHIFT_LEFT,
            InventoryAction.MOVE_TO_OTHER_INVENTORY,
        )

        server.pluginManager.callEvent(craftEvent)

        val resultItem = craftingInventory.result
        val isCancelled = craftEvent.isCancelled || resultItem == null || resultItem.amount < 1

        return if (isCancelled) {
            CancellableResult.Cancelled
        } else {
            onCraft(resultItem)
            CancellableResult(
                listOf(fcItemStackFactory.create(resultItem!!)) + ingredientRemnants
            )
        }
    }

    protected open fun onCraft(result: ItemStack) {
        giveCraftAchievements(result.type)
        incrementCraftStats(result)
    }

    protected open fun giveCraftAchievements(material: Material) {
        val achievement = when (material) {
            Material.STONE_AXE -> Achievement.BUILD_BETTER_PICKAXE
            Material.FURNACE -> Achievement.BUILD_FURNACE
            Material.WOOD_PICKAXE -> Achievement.BUILD_PICKAXE
            Material.WORKBENCH -> Achievement.BUILD_WORKBENCH
            Material.CAKE -> Achievement.BAKE_CAKE

            Material.DIAMOND_SWORD,
            Material.GOLD_SWORD,
            Material.IRON_SWORD,
            Material.STONE_SWORD,
            Material.WOOD_SWORD,
            -> Achievement.BUILD_SWORD

            Material.DIAMOND_HOE,
            Material.GOLD_HOE,
            Material.IRON_HOE,
            Material.STONE_HOE,
            Material.WOOD_HOE,
            -> Achievement.BUILD_HOE

            else -> return
        }

        when {
            player.hasAchievement(achievement) -> return
            achievement.hasParent() && !player.hasAchievement(achievement.parent) -> return
            else -> player.awardAchievement(achievement)
        }
    }

    protected open fun incrementCraftStats(result: ItemStack) {
        try {
            player.incrementStatistic(Statistic.CRAFT_ITEM, result.type, result.amount)
        } catch (exception: IllegalArgumentException) {
        }
    }

    @Singleton
    class Factory @Inject constructor(
        private val fcItemStackFactory: FcItemStack.Factory,
        private val server: Server,
    ) : FcCraftingRecipePrepared_Bukkit.Factory {
        override fun create(
            player: Player,
            recipe: FcCraftingRecipe,
            ingredients: Map<FcIngredient, FcItemStack>,
            ingredientRemnants: List<FcItemStack>,
            resultsPreview: List<FcItemStack>,
            preparedCraftingView: InventoryView,
        ): FcCraftingRecipePrepared {
            return FcCraftingRecipePrepared_Bukkit_1_7(
                player = player,
                recipe = recipe,
                ingredients = ingredients,
                ingredientRemnants = ingredientRemnants,
                resultsPreview = resultsPreview,
                preparedCraftingView = preparedCraftingView,
                fcItemStackFactory = fcItemStackFactory,
                server = server,
            )
        }
    }
}
