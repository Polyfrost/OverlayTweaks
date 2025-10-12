package org.polyfrost.overlaytweaks.mixins;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.polyfrost.overlaytweaks.OverlayTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {

    @Shadow
    public float prevVignetteBrightness;

    @Inject(method = "renderVignette", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableDepth()V"))
    private void overlaytweaks$beforeVignette(float lightLevel, ScaledResolution scaledRes, CallbackInfo ci) {
        if (OverlayTweaks.config.customVignetteDarkness) {
            this.prevVignetteBrightness = (float) OverlayTweaks.config.customVignetteDarknessValue / 100;
        }
    }

    @ModifyArgs(method = "renderPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private void overlaytweaks$modifyPortalColor(Args args) {
        float alpha = (float) OverlayTweaks.config.netherPortalOpacity / 100;
        args.set(3, (float) args.get(3) * alpha);
    }
}
