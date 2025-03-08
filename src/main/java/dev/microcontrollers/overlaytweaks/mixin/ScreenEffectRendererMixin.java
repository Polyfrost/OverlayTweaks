package dev.microcontrollers.overlaytweaks.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE;

/*
  Fire overlay height code was taken from Easeify with permission from Wyvest
  Only changes to variables have been made
  https://github.com/Polyfrost/Easeify/blob/main/LICENSE
 */
@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @Unique
    private static boolean hasPushed = false;

    @Inject(method = "renderWater", at = @At("HEAD"), cancellable = true)
    private static void cancelWaterOverlay(Minecraft minecraft, PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (OverlayTweaksConfig.CONFIG.instance().removeWaterOverlay) ci.cancel();
    }

    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void cancelFireOverlay(PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (OverlayTweaksConfig.CONFIG.instance().removeFireOverlay && Minecraft.getInstance().player != null && (Minecraft.getInstance().player.isCreative() || Minecraft.getInstance().player.hasEffect(FIRE_RESISTANCE)))
            ci.cancel();
    }

    @Inject(method = "renderFire", at = @At("HEAD"))
    private static void moveFireOverlay(PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        hasPushed = true;
        poseStack.pushPose();
        poseStack.translate(0, OverlayTweaksConfig.CONFIG.instance().fireOverlayHeight, 0);
    }

    @Inject(method = "renderFire", at = @At("RETURN"))
    private static void resetFireOverlay(PoseStack poseStack, MultiBufferSource bufferSource, CallbackInfo ci) {
        if (hasPushed) {
            hasPushed = false;
            poseStack.popPose();
        }
    }

    @ModifyArg(method = "renderFire", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setColor(FFFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"), index = 3)
    private static float enableFireOverlayOpacity(float opacity) {
        return OverlayTweaksConfig.CONFIG.instance().customFireOverlayOpacity / 100F;
    }

    @ModifyArgs(method = "renderTex", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;colorFromFloat(FFFF)I"))
    private static void changeSuffocationOverlayColor(Args args) {
        args.set(1, OverlayTweaksConfig.CONFIG.instance().suffocationOverlayBrightness / 100F);
        args.set(2, OverlayTweaksConfig.CONFIG.instance().suffocationOverlayBrightness / 100F);
        args.set(3, OverlayTweaksConfig.CONFIG.instance().suffocationOverlayBrightness / 100F);
    }
}
