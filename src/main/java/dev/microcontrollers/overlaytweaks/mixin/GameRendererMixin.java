package dev.microcontrollers.overlaytweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = GameRenderer.class, priority = 1001)
public class GameRendererMixin {
    @ModifyExpressionValue(method = "getFov", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F", ordinal = 1))
    private float removeWaterFov(float original) {
        if (OverlayTweaksConfig.CONFIG.instance().removeSubmergedFov) return 1.0F;
        else return original;
    }

    @ModifyExpressionValue(method = "renderItemInHand", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;hideGui:Z"))
    private boolean keepHand(boolean original) {
        return !OverlayTweaksConfig.CONFIG.instance().keepHand && original;
    }
}
