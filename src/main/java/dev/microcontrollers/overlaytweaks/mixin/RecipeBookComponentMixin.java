package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin {
    @ModifyReturnValue(method = "updateScreenPosition", at = @At("RETURN"))
    private int removeRecipeBookScreenShift(int original, int width, int imageWidth) {
        return OverlayTweaksConfig.CONFIG.instance().removeRecipeBookShift ? (width - imageWidth) / 2 : original;
    }

    @ModifyReturnValue(method = "getXOrigin", at = @At("RETURN"))
    private int changeRecipeBookPosition(int original) {
        return OverlayTweaksConfig.CONFIG.instance().removeRecipeBookShift ? original - 77 : original;
    }

    @ModifyArg(method = "updateTabs", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookTabButton;setPosition(II)V"), index = 0)
    private int changeRecipeBookTabButtonPosition(int original) {
        return OverlayTweaksConfig.CONFIG.instance().removeRecipeBookShift ? original - 77 : original;
    }
}
