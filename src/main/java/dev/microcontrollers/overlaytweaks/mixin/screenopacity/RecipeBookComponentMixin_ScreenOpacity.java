package dev.microcontrollers.overlaytweaks.mixin.screenopacity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.microcontrollers.overlaytweaks.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(RecipeBookComponent.class)
public class RecipeBookComponentMixin_ScreenOpacity {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void recipeBookContainerOpacityStart(GuiGraphics instance, Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, Operation<Void> original) {
        if (ScreenHelper.INSTANCE.isDefault()) original.call(instance, renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
        else instance.blit(renderTypeGetter, atlasLocation, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight, ScreenHelper.INSTANCE.getColor());
    }


//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
//    private void toggleButtonOpacityStart(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        RenderSystem.enableBlend();
//        RenderSystem.setShaderColor(1F, 1F, 1F, OverlayTweaksConfig.CONFIG.instance().containerTextureOpacity / 100F);
//    }
//
//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ToggleButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER))
//    private void toggleButtonOpacityEnd(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        RenderSystem.disableBlend();
//        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
//    }
//
//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
//    private void searchOpacityStart(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        RenderSystem.enableBlend();
//        RenderSystem.setShaderColor(1F, 1F, 1F, OverlayTweaksConfig.CONFIG.instance().containerTextureOpacity / 100F);
//    }
//
//    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.AFTER))
//    private void searchOpacityEnd(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        RenderSystem.disableBlend();
//        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
//    }
}
