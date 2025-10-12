package org.polyfrost.overlaytweaks.config;

import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Slider;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;
import org.polyfrost.overlaytweaks.OverlayTweaks;

public class OverlayTweaksConfig extends Config {
    public OverlayTweaksConfig() {
        super(OverlayTweaks.ID + ".json", OverlayTweaks.NAME, Category.QOL);
    }

    @Slider(
        title = "Water Fog Density (%)",
        description = "Changes the fog density in water to improve visibility.",
        min = 0, max = 100
    )
    public int waterDensity = 100;

    // Miscellaneous

    @Slider(
        title = "Container Opacity (%)",
        description = "Change the opacity of supported containers.\nIncludes Chests & Survival Inventory."
    )
    public float containerOpacity = 100F;

    @Slider(
        title = "Container Background Opacity (%)",
        description = "Change the opacity of the dark background inside a container, or remove it completely. By default, this is 81.5%."
    )
    public float containerBackgroundOpacity = (208/255F) * 100F;

    @Switch(
        title = "Render Hand While Zoomed",
        description = "Keep your hand on screen when you zoom in."
    )
    public boolean renderHandWhenZoomed;

    // public float handInvisibilityOpacity = 0F;
    // public HeartDisplay heartDisplayType = HeartDisplay.DEFAULT;

    // HUD


    @Switch(
        title = "Remove Water Overlay",
        description = "Remove the water texture overlay when underwater."
    )
    public boolean removeWaterOverlay;

    @Slider(
        title = "Fire Overlay Height",
        description = "Change the height of the fire overlay.",
        min = -0.5F, max = 1.5F
    )
    public float fireOverlayHeight;

    @Slider(
        title = "Fire Overlay Opacity (%)",
        description = "Change the opacity of the fire overlay.",
        min = 0, max = 100
    )
    public int fireOverlayOpacity = 100;

    @Switch(
        title = "Hide Fire Overlay with Fire Resistance",
        description = "Hide the fire overlay when you have fire resistance active.\n" +
            "The overlay will blink 5 seconds before your fire resistance is about to run out."
    )
    public boolean hideFireOverlayWithFireResistance;


    // public boolean removeItemTooltip = false;  -- why does anyone want this
    // public Color subtitleColor = new Color(0F, 0F, 0F, 1F);  -- why, just let the server set it

    // Effects

    // public float elderGuardianOpacity = 100F;
    // public float elderGuardianScale = 1F;
    @Switch(
        title = "Custom Vignette Darkness",
        description = "Customize the darkness of the vignette effect."
    )
    public boolean customVignetteDarkness = false;

    @Slider(
        title = "Vignette Darkness (%)",
        description = "Change the darkness of the vignette effect.",
        min = 0, max = 100
    )
    public int customVignetteDarknessValue = 0;

    @Slider(
        title = "Suffocation Overlay Brightness (%)",
        description = "Change the brightness of the suffocation overlay.",
        min = 0, max = 100
    )
    public int suffocationOverlayBrightness = 100;

    @Slider(
        title = "Nether Portal Opacity (%)",
        description = "Change the opacity of the nether portal overlay.",
        min = 0, max = 100
    )
    public int netherPortalOpacity = 100;

    // ---- 1.17+ or smth ----
    // public float equipableOpacity = 100F;
    // public float freezingOpacity = 100F;
    // public float spyglassOpacity = 100F;
    // public Color spyglassColor = new Color(-16777216);


    // Items

    // ----- 1.9+ -----
    // public float customShieldHeight = 0F;
    // public float customShieldOpacity = 100F;
    // public boolean colorShieldCooldown = false;
    // public Color shieldColorHigh = new Color(1F, 0F, 0F);
    // public Color shieldColorMid = new Color(0.75F, 0.37F, 0.2F);
    // public Color shieldColorLow = new Color(1F, 1F, 0F);
}
