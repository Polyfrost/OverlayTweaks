package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @WrapOperation(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void renderTransparentArm(ModelPart instance, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Operation<Void> original) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (OverlayTweaksConfig.CONFIG.instance().handInvisibilityOpacity != 0F && OverlayTweaksConfig.CONFIG.instance().handInvisibilityOpacity != 100F && player != null && player.isInvisible())
            instance.render(poseStack, buffer, packedLight, packedOverlay, ARGB.colorFromFloat(OverlayTweaksConfig.CONFIG.instance().handInvisibilityOpacity / 100F, 1F, 1F, 1F));
        else original.call(instance, poseStack, buffer, packedLight, packedOverlay);
    }
}
