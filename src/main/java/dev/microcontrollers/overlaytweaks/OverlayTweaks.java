package dev.microcontrollers.overlaytweaks;

import dev.microcontrollers.overlaytweaks.config.OverlayTweaksConfig;
//? if fabric
import net.fabricmc.api.ModInitializer;
//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}

//? if neoforge
/*@Mod(value = "overlaytweaks", dist = Dist.CLIENT)*/
public class OverlayTweaks /*? if fabric {*/ implements ModInitializer /*?}*/ {
    //? if fabric {
    @Override
    public void onInitialize() {
        OverlayTweaksConfig.CONFIG.load();
    }
    //?}

    //? if neoforge {
    /*public OverlayTweaks() {
        OverlayTweaksConfig.CONFIG.load();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, parent) -> OverlayTweaksConfig.configScreen(parent));
    }
    *///?}
}