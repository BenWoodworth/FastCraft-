package net.benwoodworth.fastcraft

import net.benwoodworth.fastcraft.platform.server.FcPermission
import net.benwoodworth.localeconfig.api.LocaleApi
import java.util.*

object Strings {
    // region locale keys
    private const val LOCALIZATION_TRANSLATOR = "localization.translator"
    private const val LOCALIZATION_TRANSLATOR_FROM = "localization.translator.from"
    private const val GUI_TITLE = "gui.title"
    private const val GUI_RECIPE_INGREDIENTS = "gui.recipe.ingredients"
    private const val GUI_RECIPE_INGREDIENTS_ITEM = "gui.recipe.ingredients.item"
    private const val GUI_RECIPE_RESULTS = "gui.recipe.results"
    private const val GUI_RECIPE_RESULTS_ITEM = "gui.recipe.results.item"
    private const val GUI_RECIPE_ID = "gui.recipe.id"
    private const val GUI_BUTTON_PAGE_TITLE = "gui.button.page.title"
    private const val GUI_BUTTON_PAGE_DESCRIPTION_0 = "gui.button.page.description.0"
    private const val GUI_BUTTON_PAGE_DESCRIPTION_1 = "gui.button.page.description.1"
    private const val GUI_BUTTON_PAGE_DESCRIPTION_2 = "gui.button.page.description.2"
    private const val GUI_BUTTON_REFRESH_TITLE = "gui.button.refresh.title"
    private const val GUI_BUTTON_REFRESH_DESCRIPTION_0 = "gui.button.refresh.description.0"
    private const val GUI_BUTTON_CRAFT_AMOUNT_TITLE = "gui.button.craft-amount.title"
    private const val GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_0 = "gui.button.craft-amount.description.0"
    private const val GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_1 = "gui.button.craft-amount.description.1"
    private const val GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_2 = "gui.button.craft-amount.description.2"
    private const val GUI_BUTTON_CRAFTING_GRID_TITLE = "gui.button.crafting-grid.title"
    private const val GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_0 = "gui.button.crafting-grid.description.0"
    private const val GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_1 = "gui.button.crafting-grid.description.1"
    private const val COMMAND_ERROR_USAGE = "command.error.usage"
    private const val COMMAND_ERROR_PERMISSION = "command.error.permission"
    private const val COMMAND_ERROR_CONSOLE_ONLY = "command.error.console.only"
    private const val COMMAND_ERROR_PLAYER_ONLY = "command.error.player.only"
    private const val COMMAND_ERROR_PLAYER_UNKNOWN = "command.error.player.unknown"
    private const val COMMAND_SET_ENABLED_TRUE = "command.set.enabled.true"
    private const val COMMAND_SET_ENABLED_FALSE = "command.set.enabled.false"
    private const val COMMAND_SET_ENABLED_TRUE_PLAYER = "command.set.enabled.true.player"
    private const val COMMAND_SET_ENABLED_FALSE_PLAYER = "command.set.enabled.false.player"
    private const val COMMAND_RELOAD_RELOADED = "command.reload.reloaded"
    // endregion

    fun load() {
        LocaleApi.load("fastcraft", "/lang")
    }

    fun localizationTranslator(locale: Locale): String {
        return LocaleApi.get(locale, LOCALIZATION_TRANSLATOR)
            ?.toString()
            ?: LOCALIZATION_TRANSLATOR
    }

    fun localizationTranslatorFrom(locale: Locale): String {
        return LocaleApi.get(locale, LOCALIZATION_TRANSLATOR_FROM)
            ?.toString()
            ?: LOCALIZATION_TRANSLATOR_FROM
    }

    fun guiTitle(locale: Locale): String {
        return LocaleApi.get(locale, GUI_TITLE)
            ?.substitute()
            ?: GUI_TITLE
    }

    fun guiRecipeIngredients(locale: Locale): String {
        return LocaleApi.get(locale, GUI_RECIPE_INGREDIENTS)
            ?.substitute()
            ?: GUI_RECIPE_INGREDIENTS
    }

    fun guiRecipeIngredientsItem(locale: Locale, amount: Int, item: String): String {
        return LocaleApi.get(locale, GUI_RECIPE_INGREDIENTS_ITEM)
            ?.substitute(
                "amount", amount,
                "item", item,
            )
            ?: "$GUI_RECIPE_INGREDIENTS_ITEM(amount=$amount, item=$item)"
    }

    fun guiRecipeResults(locale: Locale): String {
        return LocaleApi.get(locale, GUI_RECIPE_RESULTS)
            ?.substitute()
            ?: GUI_RECIPE_RESULTS
    }

    fun guiRecipeResultsItem(locale: Locale, amount: Int, item: String): String {
        return LocaleApi.get(locale, GUI_RECIPE_RESULTS_ITEM)
            ?.substitute(
                "amount", amount,
                "item", item,
            )
            ?: "$GUI_RECIPE_RESULTS_ITEM(amount=$amount, item=$item)"
    }

    fun guiRecipeId(locale: Locale, id: String): String {
        return LocaleApi.get(locale, GUI_RECIPE_ID)
            ?.substitute(
                "id", id,
            )
            ?: "$GUI_RECIPE_ID(id=$id)"
    }

    fun guiButtonPageTitle(locale: Locale, page: Int, total: Int): String {
        return LocaleApi.get(locale, GUI_BUTTON_PAGE_TITLE)
            ?.substitute(
                "page", page,
                "total", total,
            )
            ?: "$GUI_BUTTON_PAGE_TITLE(page=$page, total=$total)"
    }

    fun guiButtonPageDescription0(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_PAGE_DESCRIPTION_0)
            ?.substitute()
            ?: GUI_BUTTON_PAGE_DESCRIPTION_0
    }

    fun guiButtonPageDescription1(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_PAGE_DESCRIPTION_1)
            ?.substitute()
            ?: GUI_BUTTON_PAGE_DESCRIPTION_1
    }

    fun guiButtonPageDescription2(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_PAGE_DESCRIPTION_2)
            ?.substitute()
            ?: GUI_BUTTON_PAGE_DESCRIPTION_2
    }

    fun guiButtonRefreshTitle(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_REFRESH_TITLE)
            ?.substitute()
            ?: GUI_BUTTON_REFRESH_TITLE
    }

    fun guiButtonRefreshDescription0(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_REFRESH_DESCRIPTION_0)
            ?.substitute()
            ?: GUI_BUTTON_REFRESH_DESCRIPTION_0
    }

    fun guiButtonCraftAmountTitle(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFT_AMOUNT_TITLE)
            ?.substitute()
            ?: GUI_BUTTON_CRAFT_AMOUNT_TITLE
    }

    fun guiButtonCraftAmountDescription0(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_0)
            ?.substitute()
            ?: GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_0
    }

    fun guiButtonCraftAmountDescription1(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_1)
            ?.substitute()
            ?: GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_1
    }

    fun guiButtonCraftAmountDescription2(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_2)
            ?.substitute()
            ?: GUI_BUTTON_CRAFT_AMOUNT_DESCRIPTION_2
    }

    fun guiButtonCraftingGridTitle(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFTING_GRID_TITLE)
            ?.substitute()
            ?: GUI_BUTTON_CRAFTING_GRID_TITLE
    }

    fun guiButtonCraftingGridDescription0(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_0)
            ?.substitute()
            ?: GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_0
    }

    fun guiButtonCraftingGridDescription1(locale: Locale): String {
        return LocaleApi.get(locale, GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_1)
            ?.substitute()
            ?: GUI_BUTTON_CRAFTING_GRID_DESCRIPTION_1
    }

    fun commandErrorUsage(locale: Locale, usage: String): String {
        return LocaleApi.get(locale, COMMAND_ERROR_USAGE)
            ?.substitute(
                "usage", usage,
            )
            ?: COMMAND_ERROR_USAGE
    }

    fun commandErrorPermission(locale: Locale, permission: FcPermission): String {
        return LocaleApi.get(locale, COMMAND_ERROR_PERMISSION)
            ?.substitute(
                "permission", permission.name,
            )
            ?: COMMAND_ERROR_PERMISSION
    }

    fun commandErrorConsoleOnly(locale: Locale): String {
        return LocaleApi.get(locale, COMMAND_ERROR_CONSOLE_ONLY)
            ?.substitute()
            ?: COMMAND_ERROR_CONSOLE_ONLY
    }

    fun commandErrorPlayerOnly(locale: Locale): String {
        return LocaleApi.get(locale, COMMAND_ERROR_PLAYER_ONLY)
            ?.substitute()
            ?: COMMAND_ERROR_PLAYER_ONLY
    }

    fun commandErrorPlayerUnknown(locale: Locale, player: String): String {
        return LocaleApi.get(locale, COMMAND_ERROR_PLAYER_UNKNOWN)
            ?.substitute(
                "player", player,
            )
            ?: COMMAND_ERROR_PLAYER_UNKNOWN
    }

    fun commandSetEnabledTrue(locale: Locale): String {
        return LocaleApi.get(locale, COMMAND_SET_ENABLED_TRUE)
            ?.substitute()
            ?: COMMAND_SET_ENABLED_TRUE
    }

    fun commandSetEnabledFalse(locale: Locale): String {
        return LocaleApi.get(locale, COMMAND_SET_ENABLED_FALSE)
            ?.substitute()
            ?: COMMAND_SET_ENABLED_FALSE
    }

    fun commandSetEnabledTruePlayer(locale: Locale, player: String): String {
        return LocaleApi.get(locale, COMMAND_SET_ENABLED_TRUE_PLAYER)
            ?.substitute(
                "player", player,
            )
            ?: COMMAND_SET_ENABLED_TRUE_PLAYER
    }

    fun commandSetEnabledFalsePlayer(locale: Locale, player: String): String {
        return LocaleApi.get(locale, COMMAND_SET_ENABLED_FALSE_PLAYER)
            ?.substitute(
                "player", player,
            )
            ?: COMMAND_SET_ENABLED_FALSE_PLAYER
    }

    fun commandReloadReloaded(locale: Locale): String {
        return LocaleApi.get(locale, COMMAND_RELOAD_RELOADED)
            ?.substitute()
            ?: COMMAND_RELOAD_RELOADED
    }
}
