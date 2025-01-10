package dev.microcontrollers.overlaytweaks.mixin;

//? if <=1.21.3
/*import com.llamalad7.mixinextras.sugar.Local;*/
import com.mojang.blaze3d.vertex.PoseStack;
//? if <=1.21.3
/*import com.mojang.blaze3d.vertex.VertexConsumer;*/
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.MobAppearanceParticle;
//? if >=1.21.4
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobAppearanceParticle.class)
public class ElderGuardianAppearanceParticleMixin {
    @ModifyArg(method = /*? if >=1.21.4 {*/ "renderCustom" /*?} else {*/ /*"render" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;colorFromFloat(FFFF)I"), index = 0)
    private float changeElderGuardianOpacity(float g) {
        return g * OverlayTweaksConfig.CONFIG.instance().elderGuardianOpacity / 100F / 0.55F;
    }

    // just cancel everything if it's 0 anyways
    @Inject(method = /*? if >=1.21.4 {*/ "renderCustom" /*?} else {*/ /*"render" *//*?}*/, at = @At("HEAD"), cancellable = true)
    private void removeElderGuardianJumpscare(/*? if >=1.21.4 {*/ PoseStack poseStack, MultiBufferSource bufferSource /*?} else {*/ /*VertexConsumer buffer *//*?}*/, Camera camera, float partialTick, CallbackInfo ci) {
        if (OverlayTweaksConfig.CONFIG.instance().elderGuardianOpacity == 0F) ci.cancel();
    }

    // TODO: make it render in front of world always
    @Inject(method = /*? if >=1.21.4 {*/ "renderCustom" /*?} else {*/ /*"render" *//*?}*/, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void changeElderGuardianScale(/*? if >=1.21.4 {*/ PoseStack poseStack, MultiBufferSource bufferSource /*?} else {*/ /*VertexConsumer buffer *//*?}*/, Camera camera, float partialTicks, CallbackInfo ci /*? if <=1.21.3 {*//*, @Local PoseStack poseStack *//*?}*/) {
        float scale = OverlayTweaksConfig.CONFIG.instance().elderGuardianScale;
        poseStack.scale(scale, scale, scale);
    }
}
