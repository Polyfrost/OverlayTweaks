package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {
    @WrapWithCondition(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(IIIII)V"))
    private boolean removeDebugBackground(GuiGraphics instance, int minX, int minY, int maxX, int maxY, int color) {
        return !OverlayTweaksConfig.CONFIG.instance().classicDebugStyle;
    }

    @ModifyArg(method = "renderLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"), index = 5)
    private boolean addDebugShadow(boolean shadow) {
        return OverlayTweaksConfig.CONFIG.instance().classicDebugStyle;
    }
}
