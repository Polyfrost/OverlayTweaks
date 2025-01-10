package dev.microcontrollers.overlaytweaks.mixin;

//? if <=1.21.3 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
*///?}
//? if >=1.21.4
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if <=1.21.3 {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}

/*
    This code was taken and modified from Show Me Your Skin! under the MIT license https://github.com/enjarai/show-me-your-skin/blob/master/LICENSE
    It has been modified to not alter opacity for shields in containers and as items and only in first person, as well as to add other features
 */
@Mixin(/*? if <=1.21.3 {*/ /*BlockEntityWithoutLevelRenderer*//*?} else {*/Minecraft/*?}*/.class)
public class BlockEntityWithoutLevelRendererMixin {
    //? if <=1.21.3 {
    /*@WrapOperation(method = "renderByItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ShieldModel;renderType(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;", ordinal = 0))
    private RenderType enableShieldTransparency(ShieldModel instance, ResourceLocation resourceLocation, Operation<RenderType> original) {
        if (OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 100 && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) return RenderType.entityTranslucent(resourceLocation); // if 100, let's skip the consequences of translucency
        return original.call(instance, resourceLocation);
    }

    @Inject(method = "renderByItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"), cancellable = true)
    private void cancelShieldRendering(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, CallbackInfo ci) {
        if (stack.is(Items.SHIELD) && (displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity == 0) ci.cancel(); // if 0, just cancel it
    }

    @WrapOperation(method = "renderByItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V"))
    private void changeShieldColorAndTransparencyPre(ModelPart instance, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, Operation<Void> original, ItemStack stack, @Local(argsOnly = true) LocalRef<ItemDisplayContext> mode) {
        if (Minecraft.getInstance().player == null) return;
        float cooldown = Minecraft.getInstance().player.getCooldowns().getCooldownPercent(stack, 0);
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
    *///?}
}
