package dev.microcontrollers.overlaytweaks.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.ColorController;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.Color;

public class OverlayTweaksConfig {
    public static final ConfigClassHandler<OverlayTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(OverlayTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("overlaytweaks.json"))
                    .build())
            .build();

    // Miscellaneous

    @SerialEntry public float containerOpacity = (208/255F) * 100F;
    @SerialEntry public float containerTextureOpacity = 100F;
    @SerialEntry public boolean keepHand = false;
    @SerialEntry public float handInvisibilityOpacity = 0F;
    @SerialEntry public HeartDisplay heartDisplayType = HeartDisplay.DEFAULT;

    // HUD

    @SerialEntry public boolean removeWaterOverlay = true;
    @SerialEntry public boolean removeSubmergedFov = true;
    @SerialEntry public boolean removeFireOverlay = true;
    @SerialEntry public double fireOverlayHeight = 0.0;
    @SerialEntry public float customFireOverlayOpacity = 100F;
    @SerialEntry public boolean removeItemTooltip = false;
    @SerialEntry public Color subtitleColor = new Color(0F, 0F, 0F, 1F);

    // Effects

    @SerialEntry public float elderGuardianOpacity = 100F;
    @SerialEntry public float elderGuardianScale = 1F;
    @SerialEntry public float equipableOpacity = 100F;
    @SerialEntry public float freezingOpacity = 100F;
    @SerialEntry public float spyglassOpacity = 100F;
    @SerialEntry public Color spyglassColor = new Color(-16777216);
    @SerialEntry public boolean customVignetteDarkness = false;
    @SerialEntry public float customVignetteDarknessValue = 0F;
    @SerialEntry public float suffocationOverlayBrightness = 10F;
    @SerialEntry public float netherPortalOpacity = 100F;

    // Items

    @SerialEntry public float customShieldHeight = 0F;
    @SerialEntry public float customShieldOpacity = 100F;
    @SerialEntry public boolean colorShieldCooldown = false;
    @SerialEntry public Color shieldColorHigh = new Color(1F, 0F, 0F);
    @SerialEntry public Color shieldColorMid = new Color(0.75F, 0.37F, 0.2F);
    @SerialEntry public Color shieldColorLow = new Color(1F, 1F, 0F);

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Component.translatable("overlay-tweaks.overlay-tweaks"))

                // Miscellaneous

                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("overlay-tweaks.miscellaneous"))

                        // Containers

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.containers"))
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.container-background-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.container-background-opacity.description")))
                                        .binding((208/255F) * 100F, () -> config.containerOpacity, newVal -> config.containerOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.container-texture-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.container-texture-opacity.description")))
                                        .binding(100F, () -> config.containerTextureOpacity, newVal -> config.containerTextureOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())

                        // Hand

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.hand"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.keep-hand-in-hidden-hud"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.keep-hand-in-hidden-hud.description")))
                                        .binding(defaults.keepHand, () -> config.keepHand, newVal -> config.keepHand = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.hand-invisibility-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.hand-invisibility-opacity.description")))
                                        .binding(0F, () -> config.handInvisibilityOpacity, newVal -> config.handInvisibilityOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.hotbar"))
                                .option(Option.<HeartDisplay>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.heart-display-type"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.heart-display-type.description")))
                                        .binding(defaults.heartDisplayType, () -> config.heartDisplayType, newVal -> config.heartDisplayType = newVal)
                                        .controller(opt -> EnumControllerBuilder.create(opt).enumClass(HeartDisplay.class)
                                                .formatValue(value -> Component.translatable("overlay-tweaks.heart-display-type." + value.name().toLowerCase())))
                                        .build())
                                .build())
                        .build())

                // HUD

                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("overlay-tweaks.hud"))
                        // Water

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.liquid"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.remove-water-overlay"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.remove-water-overlay.description")))
                                        .binding(defaults.removeWaterOverlay, () -> config.removeWaterOverlay, newVal -> config.removeWaterOverlay = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.remove-submerged-fov-change"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.remove-submerged-fov-change.description")))
                                        .binding(defaults.removeSubmergedFov, () -> config.removeSubmergedFov, newVal -> config.removeSubmergedFov = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())

                        // Fire

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.fire"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.remove-fire-overlay-when-resistant"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.remove-fire-overlay-when-resistant.description")))
                                        .binding(defaults.removeFireOverlay, () -> config.removeFireOverlay, newVal -> config.removeFireOverlay = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Double>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.fire-overlay-height"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.fire-overlay-height.description")))
                                        .binding(0.0, () -> config.fireOverlayHeight, newVal -> config.fireOverlayHeight = newVal)
                                        .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                                                .range(-0.5, 0.0)
                                                .step(0.01))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.fire-overlay-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.fire-overlay-opacity.description")))
                                        .binding(100F, () -> config.customFireOverlayOpacity, newVal -> config.customFireOverlayOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())

                        // Elements

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.elements"))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.remove-held-item-name-tooltip"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.remove-held-item-name-tooltip.description")))
                                        .binding(defaults.removeItemTooltip, () -> config.removeItemTooltip, newVal -> config.removeItemTooltip = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.subtitle-background-color"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.subtitle-background-color")))
                                        .binding(defaults.subtitleColor, () -> config.subtitleColor, value -> config.subtitleColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .build())
                        .build())

                // Effects

                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("overlay-tweaks.effects"))

                        // Elder Guardian

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.elder-guardian"))
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.elder-guardian-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.elder-guardian-opacity.description")))
                                        .binding(100F, () -> config.elderGuardianOpacity, newVal -> config.elderGuardianOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.elder-guardian-scale"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.elder-guardian-scale.description")))
                                        .binding(1F, () -> config.elderGuardianScale, newVal -> config.elderGuardianScale = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.1f", value) + "x"))
                                                .range(0.1F, 3F)
                                                .step(0.1F))
                                        .build())
                                .build())

                        // Full Screen Effects

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.full-screen-effects"))
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.equippable-overlay-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.equippable-overlay-opacity.description")))
                                        .binding(100F, () -> config.equipableOpacity, newVal -> config.equipableOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.freezing-overlay-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.freezing-overlay-opacity.description")))
                                        .binding(100F, () -> config.freezingOpacity, newVal -> config.freezingOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.spyglass-overlay-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.spyglass-overlay-opacity.description")))
                                        .binding(100F, () -> config.spyglassOpacity, newVal -> config.spyglassOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.spyglass-background-color"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.spyglass-background-color.description")))
                                        .binding(defaults.spyglassColor, () -> config.spyglassColor, value -> config.spyglassColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.constant-vignette-darkness"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.constant-vignette-darkness.description")))
                                        .binding(defaults.customVignetteDarkness, () -> config.customVignetteDarkness, newVal -> config.customVignetteDarkness = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.constant-vignette-darkness-value"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.constant-vignette-darkness-value.description")))
                                        .binding(0F, () -> config.customVignetteDarknessValue, newVal -> config.customVignetteDarknessValue = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.suffocation-overlay-brightness"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.suffocation-overlay-brightness.description")))
                                        .binding(10F, () -> config.suffocationOverlayBrightness, newVal -> config.suffocationOverlayBrightness = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value)))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.nether-portal-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.nether-portal-opacity.description")))
                                        .binding(10F, () -> config.netherPortalOpacity, newVal -> config.netherPortalOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .build())
                        .build())

                // Items

                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("overlay-tweaks.items"))

                        // Shield

                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("overlay-tweaks.shields"))
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.shield-height"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.shield-height.description")))
                                        .binding(0F, () -> config.customShieldHeight, newVal -> config.customShieldHeight = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.2f", value)))
                                                .range(-0.5F, 0F)
                                                .step(0.01F))
                                        .build())
                                .option(Option.<Float>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.shield-opacity"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.shield-opacity.description")))
                                        .binding(100F, () -> config.customShieldOpacity, newVal -> config.customShieldOpacity = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Component.translatable(String.format("%,.0f", value) + "%"))
                                                .range(0F, 100F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.color-shield-cooldown"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.color-shield-cooldown.description")))
                                        .binding(defaults.colorShieldCooldown, () -> config.colorShieldCooldown, newVal -> config.colorShieldCooldown = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.shield-color-high"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.shield-color-high.description")))
                                        .binding(defaults.shieldColorHigh, () -> config.shieldColorHigh, value -> config.shieldColorHigh = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.shield-color-mid"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.shield-color-mid.description")))
                                        .binding(defaults.shieldColorMid, () -> config.shieldColorMid, value -> config.shieldColorMid = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("overlay-tweaks.shield-color-low"))
                                        .description(OptionDescription.of(Component.translatable("overlay-tweaks.shield-color-low.description")))
                                        .binding(defaults.shieldColorLow, () -> config.shieldColorLow, value -> config.shieldColorLow = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())
        )).generateScreen(parent);
    }
}
