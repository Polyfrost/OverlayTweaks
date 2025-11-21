package org.polyfrost.overlaytweaks.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import org.polyfrost.overlaytweaks.OverlayTweaks;
import org.polyfrost.overlaytweaks.hooks.ZoomHook;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;

    @ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 0.01f))
    private float waterBreathing(float constant) {
        return constant * Math.min(1f, OverlayTweaks.config.waterDensity / 100f);
    }

    @ModifyConstant(method = "setupFog", constant = @Constant(floatValue = 0.1f, ordinal = 2))
    private float water(float constant) {
        return constant * OverlayTweaks.config.waterDensity / 100f;
    }

    @ModifyExpressionValue(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;getFOVModifier(FZ)F"))
    private float getHandFOVModifier(float original, @Local(argsOnly = true) float partialTicks) {
        if (OverlayTweaks.config.renderHandWhenZoomed && ZoomHook.zoomed) {
            float f = 70f;
            //#if MC==10809
            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, this.mc.thePlayer, partialTicks);
            //#else
            //$$ IBlockState block = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.world, this.mc.player, partialTicks);
            //#endif

            if (block.getMaterial() == Material.water) {
                f = f * 60.0F / 70.0F;
            }
            return f;
        }
        return original;
    }

    @Dynamic("OptiFine")
    @ModifyVariable(method = "getFOVModifier", name = "zoomActive", at = @At(value = "LOAD", ordinal = 0))
    private boolean patcher$handleZoomStateChanged(boolean zoomActive) {
        ZoomHook.handleZoomStateChange(zoomActive);
        return zoomActive;
    }
}
