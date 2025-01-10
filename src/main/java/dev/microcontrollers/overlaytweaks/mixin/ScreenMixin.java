package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Screen.class)
public class ScreenMixin {
    @WrapWithCondition(method = "renderTransparentBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"))
    private boolean shouldRenderBackground(GuiGraphics instance, int x1, int y1, int x2, int y2, int colorFrom, int colorTo) {
        return OverlayTweaksConfig.CONFIG.instance().containerOpacity > 0;
    }

    @ModifyArgs(method = "renderTransparentBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"))
    private void changeContainerBackgroundOpacity(Args args) {
        args.set(4, withOpacity(args.get(4), Math.max(OverlayTweaksConfig.CONFIG.instance().containerOpacity / 100F - 16 / 255F, 0)));
        args.set(5, withOpacity(args.get(5), OverlayTweaksConfig.CONFIG.instance().containerOpacity / 100F));
    }

    @Unique
    private int withOpacity(int color, float opacity) {
        return (int) (opacity * 255) << 24 | (color & 0xFFFFFF);
    }
}
