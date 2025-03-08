package dev.microcontrollers.overlaytweaks.mixin.screenopacity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.microcontrollers.overlaytweaks.ScreenHelper;
import dev.microcontrollers.overlaytweaks.mixin.GuiGraphicsMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(HorseInventoryScreen.class)
public class HorseInventoryScreenMixin {
    @Unique
    private GuiSpriteManager sprites = Minecraft.getInstance().getGuiSprites();

    @WrapOperation(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void horseContainerOpacityStart(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, Operation<Void> original) {
        if (ScreenHelper.INSTANCE.isDefault()) original.call(instance, renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
        else instance.blit(renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight, ScreenHelper.INSTANCE.getColor());
    }

    @WrapOperation(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V"))
    private void horseSpriteOpacity2(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation sprite, int textureWidth, int textureHeight, int uPosition, int vPosition, int x, int y, int uWidth, int vHeight, Operation<Void> original) {
        if (ScreenHelper.INSTANCE.isDefault()) original.call(instance, renderTypeGetter, sprite, textureWidth, textureHeight, uPosition, vPosition, x, y, uWidth, vHeight);
        ((GuiGraphicsMixin) instance).invokeBlitSprite(renderTypeGetter, sprites.getSprite(sprite), textureWidth, textureHeight, uPosition, vPosition, x, y, uWidth, vHeight, ScreenHelper.INSTANCE.getColor());
    }
}
