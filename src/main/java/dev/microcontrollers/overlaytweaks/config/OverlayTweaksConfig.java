package dev.microcontrollers.overlaytweaks.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class OverlayTweaksConfig {
    public static final ConfigClassHandler<OverlayTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(OverlayTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("overlaytweaks.json"))
                    .build())
            .build();

    // Miscellaneous

    @SerialEntry public float containerOpacity = (208/255F) * 100F;
    @SerialEntry public float containerTextureOpacity = 100F;
    @SerialEntry public boolean disableHandViewSway = false;
    @SerialEntry public boolean keepHand = false;
    //#if MC <= 1.20.4
    //$$ @SerialEntry public float tabBackgroundOpacity = 100F;
    //#endif

    // HUD

    @SerialEntry public boolean removeWaterOverlay = true;
    @SerialEntry public boolean removeWaterFov = true;
    @SerialEntry public boolean removeFireOverlay = true;
    @SerialEntry public double fireOverlayHeight = 0.0;
    @SerialEntry public float customFireOverlayOpacity = 100F;
    @SerialEntry public boolean removeItemTooltip = false;
    //#if MC <= 1.20.1
    //$$ @SerialEntry public boolean hideScoreboardInDebug = false;
    //#endif
    @SerialEntry public boolean classicDebugStyle = false;
    @SerialEntry public Color subtitleColor = new Color(0F, 0F, 0F, 1F);

    // Effects

    @SerialEntry public float elderGuardianOpacity = 100F;
    @SerialEntry public float elderGuardianScale = 1F;
    @SerialEntry public float pumpkinOpacity = 100F;
    @SerialEntry public float freezingOpacity = 100F;
    @SerialEntry public float spyglassOpacity = 100F;
    @SerialEntry public Color spyglassColor = new Color(-16777216);
    @SerialEntry public boolean customVignetteDarkness = false;
    @SerialEntry public float customVignetteDarknessValue = 0F;
    @SerialEntry public float suffocationOverlayBrightness = 10F;

    // Items

    @SerialEntry public float customShieldHeight = 0F;
    @SerialEntry public float customShieldOpacity = 100F;
    @SerialEntry public boolean colorShieldCooldown = false;
    @SerialEntry public Color shieldColorHigh = new Color(1F, 0F, 0F);
    @SerialEntry public Color shieldColorMid = new Color(0.75F, 0.37F, 0.2F);
    @SerialEntry public Color shieldColorLow = new Color(1F, 1F, 0F);
    @SerialEntry public boolean potionGlint = false;

    @SuppressWarnings("deprecation")
    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.literal("Overlay Tweaks"))

                // Miscellaneous

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Miscellaneous"))

                        // Containers

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Containers"))
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Container Background Opacity"))
                                        .description(OptionDescription.of(Text.of("Set the transparency of container backgrounds. Set to 0 to make it completely transparent.")))
                                        .binding((208/255F) * 100F, () -> config.containerOpacity, newVal -> config.containerOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Container Texture Opacity"))
                                        .description(OptionDescription.of(Text.of("Set the transparency of container textures. Set to 0 to make it completely transparent.")))
                                        .binding(100F, () -> config.containerTextureOpacity, newVal -> config.containerTextureOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())

                        // Hand

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Hand"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Disable Hand View Sway"))
                                        .description(OptionDescription.of(Text.of("Disables the hand view sway when moving your mouse.")))
                                        .binding(defaults.disableHandViewSway, () -> config.disableHandViewSway, newVal -> config.disableHandViewSway = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Keep Hand in Hidden HUD"))
                                        .description(OptionDescription.of(Text.of("Keep your hand/held item in view when hiding hud (pressing F1).")))
                                        .binding(defaults.keepHand, () -> config.keepHand, newVal -> config.keepHand = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())

                        // GUIs

                        //#if MC <= 1.20.44
                        //$$ .group(OptionGroup.createBuilder()
                        //$$    .name(Text.literal("Tab Background"))
                        //$$    .option(Option.createBuilder(float.class)
                        //$$            .name(Text.literal("Tab Background Opacity"))
                        //$$            .description(OptionDescription.of(Text.of("Replaces the black bar background in tab navigation screens. Use 60% for a value similar to Benonardo's standalone mod.")))
                        //$$            .binding(10F, () -> config.tabBackgroundOpacity, newVal -> config.tabBackgroundOpacity = newVal)
                        //$$            .controller(opt -> FloatSliderControllerBuilder.create(opt)
                        //$$                    .valueFormatter(value -> Text.of(String.format("%,.0f", value)))
                        //$$                    .range(0F, 100F)
                        //$$                    .step(1F))
                        //$$            .build())
                        //$$    .build())
                        //#endif

                        .build())

                // HUD

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("HUD"))
                        // Water

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Water"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Remove Water Overlay"))
                                        .description(OptionDescription.of(Text.of("Removes the underwater overlay when in water.")))
                                        .binding(defaults.removeWaterOverlay, () -> config.removeWaterOverlay, newVal -> config.removeWaterOverlay = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Remove Underwater FOV Change"))
                                        .description(OptionDescription.of(Text.of("Stops the FOV from changing when you go underwater.")))
                                        .binding(defaults.removeWaterFov, () -> config.removeWaterFov, newVal -> config.removeWaterFov = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())

                        // Fire

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Fire"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Remove Fire Overlay When Resistant"))
                                        .description(OptionDescription.of(Text.of("Removes the fire overlay when you are resistant to fire, such as when you have fire resistance or are in creative mode.")))
                                        .binding(defaults.removeFireOverlay, () -> config.removeFireOverlay, newVal -> config.removeFireOverlay = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(double.class)
                                        .name(Text.literal("Fire Overlay Height"))
                                        .description(OptionDescription.of(Text.of("Change the height of the fire overlay if your pack does not have low fire. May improve visibility.")))
                                        .binding(0.0, () -> config.fireOverlayHeight, newVal -> config.fireOverlayHeight = newVal)
                                        .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                                                .range(-0.5, 0.0)
                                                .step(0.01))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Fire Overlay Opacity"))
                                        .description(OptionDescription.of(Text.of("The value for fire overlay opacity.")))
                                        .binding(100F, () -> config.customFireOverlayOpacity, newVal -> config.customFireOverlayOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())

                        // Elements

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Elements"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Remove Held Item Name Tooltip"))
                                        .description(OptionDescription.of(Text.of("Removes the tooltip above the hotbar when holding an item.")))
                                        .binding(defaults.removeItemTooltip, () -> config.removeItemTooltip, newVal -> config.removeItemTooltip = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                //#if MC <= 1.20.1
                                //$$ .option(Option.createBuilder(boolean.class)
                                //$$         .name(Text.literal("Hide Scoreboard In Debug Menu"))
                                //$$         .description(OptionDescription.of(Text.of("Removes the scoreboard when you have the F3 menu open.")))
                                //$$         .binding(defaults.hideScoreboardInDebug, () -> config.hideScoreboardInDebug, newVal -> config.hideScoreboardInDebug = newVal)
                                //$$         .controller(TickBoxControllerBuilder::create)
                                //$$         .build())
                                //#endif
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Classic Debug Style"))
                                        .description(OptionDescription.of(Text.of("Removes the background of the F3 text and renders the text with a shadow instead.")))
                                        .binding(defaults.classicDebugStyle, () -> config.classicDebugStyle, newVal -> config.classicDebugStyle = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Subtitle Background Color"))
                                        .binding(defaults.subtitleColor, () -> config.subtitleColor, value -> config.subtitleColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .build())
                        .build())

                // Effects

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Effects"))

                        // Elder Guardian

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Elder Guardian"))
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Elder Guardian Opacity"))
                                        .description(OptionDescription.of(Text.of("Changes the opacity of the elder guardian particle effect from showing on your screen.")))
                                        .binding(100F, () -> config.elderGuardianOpacity, newVal -> config.elderGuardianOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Elder Guardian Scale"))
                                        .description(OptionDescription.of(Text.of("Changes the scale of the elder guardian particle effect from showing on your screen.")))
                                        .binding(1F, () -> config.elderGuardianScale, newVal -> config.elderGuardianScale = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.1f", value) + "%"))
                                                .range(0.1F, 3F)
                                                .step(0.1F))
                                        .build())
                                .build())

                        // Full Screen Effects

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Full Screen Effects"))
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Pumpkin Overlay Opacity"))
                                        .description(OptionDescription.of(Text.of("Changes the opacity of the pumpkin overlay when wearing a pumpkin.")))
                                        .binding(100F, () -> config.pumpkinOpacity, newVal -> config.pumpkinOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Freezing Overlay Opacity"))
                                        .description(OptionDescription.of(Text.of("Changes the maximum opacity of the freezing overlay when inside powdered snow. It will take the same amount of time to reach the maximum opacity as vanilla does.")))
                                        .binding(100F, () -> config.freezingOpacity, newVal -> config.freezingOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Spyglass Overlay Opacity"))
                                        .description(OptionDescription.of(Text.of("Changes the opacity of the spyglass overlay.")))
                                        .binding(100F, () -> config.spyglassOpacity, newVal -> config.spyglassOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Spyglass Background Color"))
                                        .description(OptionDescription.of(Text.of("Allows setting a custom color for the background of nametags.")))
                                        .binding(defaults.spyglassColor, () -> config.spyglassColor, value -> config.spyglassColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Constant Vignette Darkness"))
                                        .description(OptionDescription.of(Text.of("Apply a constant vignette regardless of sky light level.")))
                                        .binding(defaults.customVignetteDarkness, () -> config.customVignetteDarkness, newVal -> config.customVignetteDarkness = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Constant Vignette Darkness Value"))
                                        .description(OptionDescription.of(Text.of("The value for constant vignette.")))
                                        .binding(0F, () -> config.customVignetteDarknessValue, newVal -> config.customVignetteDarknessValue = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Change Suffocation Overlay Brightness"))
                                        .description(OptionDescription.of(Text.of("Change Suffocation Overlay Brightness")))
                                        .binding(10F, () -> config.suffocationOverlayBrightness, newVal -> config.suffocationOverlayBrightness = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value)))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())
                        .build())

                // Items

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Items"))

                        // Shield

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Shield"))
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Shield Height"))
                                        .description(OptionDescription.of(Text.of("The value for shield height.")))
                                        .binding(0F, () -> config.customShieldHeight, newVal -> config.customShieldHeight = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.2f", value)))
                                                .range(-0.5F, 0F)
                                                .step(0.01F))
                                        .build())
                                .option(Option.createBuilder(float.class)
                                        .name(Text.literal("Shield Opacity"))
                                        .description(OptionDescription.of(Text.of("The value for shield opacity. Currently does not work for shields with banner designs.")))
                                        .binding(100F, () -> config.customShieldOpacity, newVal -> config.customShieldOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .valueFormatter(value -> Text.of(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Color Shield Cooldown"))
                                        .description(OptionDescription.of(Text.of("Adds a color to your shield depending on the cooldown remaining")))
                                        .binding(defaults.colorShieldCooldown, () -> config.colorShieldCooldown, newVal -> config.colorShieldCooldown = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Shield Color High"))
                                        .binding(defaults.shieldColorHigh, () -> config.shieldColorHigh, value -> config.shieldColorHigh = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Shield Color Mid"))
                                        .binding(defaults.shieldColorMid, () -> config.shieldColorMid, value -> config.shieldColorMid = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Shield Color Low"))
                                        .binding(defaults.shieldColorLow, () -> config.shieldColorLow, value -> config.shieldColorLow = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())

                        // Potions

                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Potions"))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Potion Glint"))
                                        .description(OptionDescription.of(Text.of("Add the potion glint that was removed in 1.19.4.")))
                                        .binding(defaults.potionGlint, () -> config.potionGlint, newVal -> config.potionGlint = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}