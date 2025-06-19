package dev.microcontrollers.overlaytweaks.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsMixin {
    @Invoker
    void invokeBlitSprite(Function<ResourceLocation, RenderType> function, TextureAtlasSprite textureAtlasSprite, int i, int j, int k, int l, int m, int n, int o, int p, int q);
}
