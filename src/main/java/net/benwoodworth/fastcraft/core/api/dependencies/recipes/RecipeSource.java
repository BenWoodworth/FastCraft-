package net.benwoodworth.fastcraft.core.api.dependencies.recipes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A source of recipes. (e.g. from plugins that register custom recipes)
 *
 * @param <TItem> the native item type
 */
public interface RecipeSource<TItem> {

    /**
     * Get this recipe source's crafting recipes.
     *
     * @return the crafting recipes
     */
    @NotNull
    List<FcCustomRecipe<TItem>> getCraftingRecipes();
}
