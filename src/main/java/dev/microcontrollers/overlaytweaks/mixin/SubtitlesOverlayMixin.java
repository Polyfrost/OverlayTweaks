package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.gui.components.SubtitleOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SubtitleOverlay.class)
public class SubtitlesOverlayMixin {
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getBackgroundColor(F)I"))
    private int modifySubtitleColor(int original) {
        return OverlayTweaksConfig.CONFIG.instance().subtitleColor.getRGB() == original ? original : OverlayTweaksConfig.CONFIG.instance().subtitleColor.getRGB();
    }
}
