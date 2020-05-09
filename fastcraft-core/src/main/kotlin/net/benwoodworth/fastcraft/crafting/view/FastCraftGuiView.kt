package net.benwoodworth.fastcraft.crafting.view

import net.benwoodworth.fastcraft.Strings
import net.benwoodworth.fastcraft.crafting.view.buttons.*
import net.benwoodworth.fastcraft.platform.gui.FcGui
import net.benwoodworth.fastcraft.platform.player.FcPlayer
import net.benwoodworth.fastcraft.platform.text.FcTextFactory
import javax.inject.Inject

class FastCraftGuiView(
    player: FcPlayer,
    guiFactory: FcGui.Factory,
    workbenchButtonFactory: WorkbenchButtonView.Factory,
    pageButtonFactory: PageButtonView.Factory,
    recipeButtonFactory: RecipeButtonView.Factory,
    craftAmountButtonFactory: CraftAmountButtonView.Factory,
    refreshButtonFactory: RefreshButtonView.Factory,
    textFactory: FcTextFactory,
) {
    val gui = guiFactory.createChestGui(
        player = player,
        title = textFactory.createLegacy(Strings.guiTitle(player.locale)),
        height = 6
    )

    private val width = gui.layout.width
    private val height = gui.layout.height

    val workbenchButton = workbenchButtonFactory
        .create(gui.layout.getButton(width - 1, 0), player.locale)

    val craftAmountButton = craftAmountButtonFactory
        .create(gui.layout.getButton(width - 1, 1), player.locale)

    val refreshButton = refreshButtonFactory
        .create(gui.layout.getButton(width - 1, 2), player.locale)

    val pageButton = pageButtonFactory
        .create(gui.layout.getButton(width - 1, height - 1), player.locale)

    val recipeButtons = List((width - 2) * height) { i ->
        recipeButtonFactory.create(
            gui.layout.getButton(
                column = i % (width - 2),
                row = i / (width - 2)
            ),
            player.locale
        )
    }

    class Factory @Inject constructor(
        private val guiFactory: FcGui.Factory,
        private val workbenchButtonFactory: WorkbenchButtonView.Factory,
        private val pageButtonFactory: PageButtonView.Factory,
        private val recipeButtonFactory: RecipeButtonView.Factory,
        private val craftAmountButtonFactory: CraftAmountButtonView.Factory,
        private val refreshButtonFactory: RefreshButtonView.Factory,
        private val textFactory: FcTextFactory,
    ) {
        fun create(player: FcPlayer): FastCraftGuiView {
            return FastCraftGuiView(
                player = player,
                guiFactory = guiFactory,
                workbenchButtonFactory = workbenchButtonFactory,
                pageButtonFactory = pageButtonFactory,
                recipeButtonFactory = recipeButtonFactory,
                craftAmountButtonFactory = craftAmountButtonFactory,
                refreshButtonFactory = refreshButtonFactory,
                textFactory = textFactory,
            )
        }
    }
}
