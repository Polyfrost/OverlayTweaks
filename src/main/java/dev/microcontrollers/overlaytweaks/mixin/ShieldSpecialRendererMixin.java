package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShieldSpecialRenderer.class)
public class ShieldSpecialRendererMixin {
    @WrapOperation(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ShieldModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;", ordinal = 0))
    private RenderType enableShieldTransparency(ShieldModel instance, ResourceLocation resourceLocation, Operation<RenderType> original) {
        if (OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 100 && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) return RenderType.entityTranslucent(resourceLocation); // if 100, let's skip the consequences of translucency
        return original.call(instance, resourceLocation);
    }

    @Inject(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"), cancellable = true)
    private void cancelShieldRendering(DataComponentMap dataComponentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl, CallbackInfo ci) {
        if ((itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity == 0) ci.cancel(); // if 0, just cancel it
    }

    @WrapOperation(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void changeShieldColorAndTransparencyPre(ModelPart instance, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Operation<Void> original, @Local(argsOnly = true) LocalRef<ItemDisplayContext> itemDisplayContext) {
        if (itemDisplayContext.get() == ItemDisplayContext.GUI || itemDisplayContext.get() == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext.get() == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) original.call(instance, poseStack, buffer, packedLight, packedOverlay);
        assert Minecraft.getInstance().player != null;
        float cooldown = Minecraft.getInstance().player.getCooldowns().getCooldownPercent(new ItemStack(Items.SHIELD), 0);
        if (itemDisplayContext.get() == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || itemDisplayContext.get() == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) {
            float a = OverlayTweaksConfig.CONFIG.instance().customShieldOpacity / 100F;
            float r = 1F; float g = 1F; float b = 1F;
            // shield colors
            if (OverlayTweaksConfig.CONFIG.instance().colorShieldCooldown) {
                if (cooldown <= 1.0F && cooldown > 0.65F) {
                    r = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getRed()   / 255F;
                    g = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getGreen() / 255F;
                    b = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getBlue()  / 255F;
                } else if (cooldown <= 0.65F && cooldown > 0.35F) {
                    r = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getRed()    / 255F;
                    g = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getGreen()  / 255F;
                    b = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getBlue()   / 255F;
                } else if (cooldown <= 0.35F && cooldown > 0F) {
                    r = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getRed()    / 255F;
                    g = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getGreen()  / 255F;
                    b = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getBlue()   / 255F;
                }
            }
            instance.render(poseStack, buffer, packedLight, packedOverlay, ARGB.colorFromFloat(a, r, g, b));
            return;
        }
        original.call(instance, poseStack, buffer, packedLight, packedOverlay);
    }
}
