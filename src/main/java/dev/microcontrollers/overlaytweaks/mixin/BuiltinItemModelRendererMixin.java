package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/*
    This code was taken and modified from Show Me Your Skin! under the MIT license https://github.com/enjarai/show-me-your-skin/blob/master/LICENSE
    It has been modified to not alter opacity for shields in containers and as items and only in first person, as well as to add other features
 */
@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinItemModelRendererMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ShieldEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;", ordinal = 0))
    private RenderLayer enableShieldTransparency(ShieldEntityModel model, Identifier texture, Operation<RenderLayer> original) {
        if (OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 100) return RenderLayer.getEntityTranslucent(texture); // if 100, let's skip the consequences of translucency
        return original.call(model, texture);
    }

    //#if MC >= 1.21
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"), cancellable = true)
    //#else
    //$$ @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"), cancellable = true)
    //#endif
    private void cancelShieldRendering(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        if (stack.isOf(Items.SHIELD) && (mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND)
                && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity == 0) ci.cancel(); // if 0, just cancel it
    }

    // We need to get the parent method parameters for mode, so we need to use @ModifyArgs.
    //#if MC < 1.21
    //$$ @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    //$$ private void changeShieldColorAndTransparency(Args args, ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
    //$$     if (MinecraftClient.getInstance().player == null) return;
    //$$     ClientPlayerEntity player = MinecraftClient.getInstance().player;
    //$$     float cooldown = player.getItemCooldownManager().getCooldownProgress(Items.SHIELD, 0);
    //$$     // We want this to only change shields that are being held so it doesn't affect containers/dropped items, as well as first person only.
    //$$     if (mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
    //$$             && OverlayTweaksConfig.CONFIG.instance().customShieldOpacity != 0) {
    //$$         args.set(7, OverlayTweaksConfig.CONFIG.instance().customShieldOpacity / 100);
    //$$         // shield colors
    //$$         if (!OverlayTweaksConfig.CONFIG.instance().colorShieldCooldown) return;
    //$$         if (cooldown <= 1.0F && cooldown > 0.65F) {
    //$$             args.set(4, OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getRed() / 255F);
    //$$             args.set(5, OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getGreen() / 255F);
    //$$             args.set(6, OverlayTweaksConfig.CONFIG.instance().shieldColorHigh.getBlue() / 255F);
    //$$         } else if (cooldown <= 0.65F && cooldown > 0.35F) {
    //$$             args.set(4, OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getRed() / 255F);
    //$$             args.set(5, OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getGreen() / 255F);
    //$$            args.set(6, OverlayTweaksConfig.CONFIG.instance().shieldColorMid.getBlue() / 255F);
    //$$         } else if (cooldown <= 0.35F && cooldown > 0F) {
    //$$             args.set(4, OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getRed() / 255F);
    //$$             args.set(5, OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getGreen() / 255F);
    //$$             args.set(6, OverlayTweaksConfig.CONFIG.instance().shieldColorLow.getBlue() / 255F);
    //$$         }
    //$$     }
    //$$ }
    //#endif

    //#if MC >= 1.21
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    private void changeShieldColorAndTransparencyPre(ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original, @Local(argsOnly = true) LocalRef<ModelTransformationMode> mode) {
        if (MinecraftClient.getInstance().player == null) return;
        float cooldown = MinecraftClient.getInstance().player.getItemCooldownManager().getCooldownProgress(Items.SHIELD, 0);
        if (mode.get() == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || mode.get() == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND
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
            instance.render(matrices, vertices, light, overlay, ColorHelper.Argb.fromFloats(alpha, red, green, blue));
            return;
        }
        original.call(instance, matrices, vertices, light, overlay);
    }
    //#endif

}
