package org.polyfrost.overlaytweaks.mixins;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.polyfrost.overlaytweaks.OverlayTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private Minecraft mc;

    @Unique
    private float overlaytweaks$partialTicksCopy;

    @Inject(method = "renderWaterOverlayTexture", at = @At("HEAD"), cancellable = true)
    private void overlaytweaks$removeWaterOverlay(CallbackInfo ci) {
        if (OverlayTweaks.config.removeWaterOverlay) {
            ci.cancel();
        }
    }

    @Inject(method = "renderOverlays", at = @At("HEAD"))
    private void overlaytweaks$copyPartialTicksValue(float partialTicks, CallbackInfo ci) {
        this.overlaytweaks$partialTicksCopy = partialTicks;
    }

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    private void overlaytweaks$changeHeightAndFixOverlay(CallbackInfo ci) {
        if (this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1").getFrameCount() == 0) {
            ci.cancel();
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0f, OverlayTweaks.config.fireOverlayHeight, 0f);
    }

    @Inject(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void overlaytweaks$enableFireOpacity(CallbackInfo ci) {
        float fireOpacity = overlaytweaks$getFireOpacity();
        if (fireOpacity == 1.0f) return;
        GlStateManager.color(1, 1, 1, fireOpacity);
    }

    @Inject(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;popMatrix()V"))
    private void overlaytweaks$disableFireOpacity(CallbackInfo ci) {
        GlStateManager.color(1, 1, 1, 1);
    }

    @Inject(method = "renderFireInFirstPerson", at = @At("TAIL"))
    private void overlaytweaks$popMatrix(CallbackInfo ci) {
        GlStateManager.popMatrix();
    }

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderBlockInHand(FLnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private void overlaytweaks$beforeBlockInHandRender(float partialTicks, CallbackInfo ci) {
        float brightness = OverlayTweaks.config.suffocationOverlayBrightness / 100f;
        if (brightness < 1.0f) {
            GlStateManager.color(brightness, brightness, brightness, 1.0f);
        }
    }


    @Unique
    private float overlaytweaks$getFireOpacity() {
        float fireOpacity = OverlayTweaks.config.fireOverlayOpacity / 100f;
        if (OverlayTweaks.config.hideFireOverlayWithFireResistance && this.mc.thePlayer.isPotionActive(Potion.fireResistance)) {
            int duration = this.mc.thePlayer.getActivePotionEffect(Potion.fireResistance).getDuration();
            fireOpacity *= duration > 100 ? 0.0F : 0.5F - MathHelper.sin(((float) duration - this.overlaytweaks$partialTicksCopy) * (float) Math.PI * 0.2F) * 0.5F;
        }
        return fireOpacity;
    }
}
