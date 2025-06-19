package dev.microcontrollers.overlaytweaks.mixin.screenopacity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.microcontrollers.overlaytweaks.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin_ScreenOpacity {
    @WrapOperation(method = "renderErrorIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private void anvilInvalidArrowOpacity(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int x, int y, int width, int height, Operation<Void> original) {
        if (ScreenHelper.INSTANCE.isDefault()) original.call(instance, renderTypeGetter, sprite, x, y, width, height);
        instance.blitSprite(renderTypeGetter, sprite, x, y, width, height, ScreenHelper.INSTANCE.getColor());
    }
}
