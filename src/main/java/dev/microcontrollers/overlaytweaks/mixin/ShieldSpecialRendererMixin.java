package dev.microcontrollers.overlaytweaks.mixin;

//? if >=1.21.4 {
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
//?}
//? if <=1.21.3
/*import net.minecraft.client.Minecraft;*/
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Mixin(/*? if >=1.21.4 {*/ ShieldSpecialRenderer/*?} else {*//*Minecraft*//*?}*/.class)
public class ShieldSpecialRendererMixin {
    //? if >=1.21.4 {
    @WrapOperation(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ShieldModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;", ordinal = 0))
    private RenderType enableShieldTransparency(ShieldModel instance, ResourceLocation resourceLocation, Operation<RenderType> original) {
        if (OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 100 && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) return RenderType.entityTranslucent(resourceLocation); // if 100, let's skip the consequences of translucency
        return original.call(instance, resourceLocation);
    }

    @Inject(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"), cancellable = true)
    private void cancelShieldRendering(DataComponentMap dataComponentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl, CallbackInfo ci) {
        if (itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity == 0) ci.cancel(); // if 0, just cancel it
    }

    @WrapOperation(method = "render(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void changeShieldColorAndTransparencyPre(ModelPart instance, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Operation<Void> original, @Local(argsOnly = true) LocalRef<ItemDisplayContext> mode) {
        if (Minecraft.getInstance().player == null) return;
        float cooldown = Minecraft.getInstance().player.getCooldowns().getCooldownPercent(new ItemStack(Items.SHIELD), 0);
        if (mode.get() == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || mode.get() == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) {
            float alpha = OverlayTweaksConfig.CONFIG.instance().customShieldOpacity / 100;
            float red = 1F; float green = 1F; float blue = 1F;
            // shield colors
            if (OverlayTweaksConfig.CONFIG.instance().colorShieldCooldown) {
                if (cooldown <= 1.0F && cooldown > 0.65F) {
                    red = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getRed() / 255F;
                    green = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getGreen() / 255F;
                    blue = OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getBlue() / 255F;
                } else if (cooldown <= 0.65F && cooldown > 0.35F) {
                    red = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getRed() / 255F;
                    green = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getGreen() / 255F;
                    blue = OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getBlue() / 255F;
                } else if (cooldown <= 0.35F && cooldown > 0F) {
                    red = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getRed() / 255F;
                    green = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getGreen() / 255F;
                    blue = OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getBlue() / 255F;
                }
            }
            instance.render(poseStack, buffer, packedLight, packedOverlay, ARGB.colorFromFloat(alpha, red, green, blue));
            return;
        }
        original.call(instance, poseStack, buffer, packedLight, packedOverlay);
    }
    //?}
}
