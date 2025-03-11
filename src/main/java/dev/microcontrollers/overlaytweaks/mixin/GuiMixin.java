package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow public float vignetteBrightness;
    @Mutable @Shadow @Final private DebugScreenOverlay debugOverlay;

    public GuiMixin(DebugScreenOverlay debugOverlay) {
        this.debugOverlay = debugOverlay;
    }

    @Inject(method = "updateVignetteBrightness", at = @At("TAIL"))
    private void changeVignetteDarkness(Entity entity, CallbackInfo ci) {
        if (OverlayTweaksConfig.CONFIG.instance().customVignetteDarkness) {
            this.vignetteBrightness = OverlayTweaksConfig.CONFIG.instance().customVignetteDarknessValue / 100;
        }
    }

    @ModifyArg(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderTextureOverlay(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;F)V", ordinal = 0), index = 2)
    private float changeEquippableOpacity(float opacity) {
        return OverlayTweaksConfig.CONFIG.instance().equipableOpacity / 100F;
    }

    @ModifyArg(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderTextureOverlay(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;F)V", ordinal = 1), index = 2)
    private float changeFreezingOpacity(float opacity) {
        return opacity * OverlayTweaksConfig.CONFIG.instance().freezingOpacity / 100F;
    }

    @WrapOperation(method = "renderSpyglassOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void changeSpyglassOpacity(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, Operation<Void> original) {
        if (OverlayTweaksConfig.CONFIG.instance().spyglassOpacity == 100F) original.call(instance, renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
        else instance.blit(renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight, ARGB.colorFromFloat(OverlayTweaksConfig.CONFIG.instance().spyglassOpacity / 100F,1F, 1F, 1F));
    }

    @ModifyExpressionValue(method = "renderSpyglassOverlay", at = @At(value = "CONSTANT", args = "intValue=-16777216"))
    private int changeSpyglassColor(int original) {
        return OverlayTweaksConfig.CONFIG.instance().spyglassColor.getRGB() == original ? original : OverlayTweaksConfig.CONFIG.instance().spyglassColor.getRGB();
    }

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
    private void removeItemTooltip(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (OverlayTweaksConfig.CONFIG.instance().removeItemTooltip) ci.cancel();
    }

    @WrapOperation(method = "renderPortalOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;white(F)I"))
    private int changeNetherPortalOpacity(float alpha, Operation<Integer> original) {
        return original.call(alpha * OverlayTweaksConfig.CONFIG.instance().netherPortalOpacity / 100F);
    }

    @ModifyVariable(method = "renderHeart", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    private boolean setAlwaysHardcoreHearts(boolean value) {
        if (OverlayTweaksConfig.CONFIG.instance().alwaysHardcoreHearts) return true;
        if (OverlayTweaksConfig.CONFIG.instance().alwaysRegularHearts) return false;
        return value;
    }
}
