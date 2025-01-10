package dev.microcontrollers.overlaytweaks;

import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
import net.minecraft.util.ARGB;

public class ScreenHelper {
    public boolean isDefault() {
        return OverlayTweaksConfig.CONFIG.instance().containerOpacity == 100F;
    }

    public int getColor() {
        return ARGB.colorFromFloat(OverlayTweaksConfig.CONFIG.instance().containerTextureOpacity / 100F, 1F, 1F, 1F);
    }

    public static final ScreenHelper INSTANCE = new ScreenHelper();
}
