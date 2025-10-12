package org.polyfrost.overlaytweaks;

//#if FABRIC
//$$ import net.fabricmc.api.ModInitializer;
//#elseif FORGE
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif

import org.polyfrost.overlaytweaks.config.OverlayTweaksConfig;

//#if FORGE-LIKE
@Mod(modid = OverlayTweaks.ID, name = OverlayTweaks.NAME, version = OverlayTweaks.VERSION)
//#endif
public class OverlayTweaks
    //#if FABRIC
    //$$ implements ModInitializer
    //#endif
{
    public static final String ID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    public static OverlayTweaksConfig config;

    //#if FABRIC
    //$$ @Override
    //#elseif FORGE
    @Mod.EventHandler
    //#endif
    public void onInitialize(
        //#if FORGE
        FMLInitializationEvent event
        //#endif
    ) {
        config = new OverlayTweaksConfig();
    }
}
