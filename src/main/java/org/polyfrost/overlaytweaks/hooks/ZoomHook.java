package org.polyfrost.overlaytweaks.hooks;

public class ZoomHook {

    public static boolean zoomed = false;

    public static void handleZoomStateChange(boolean newZoomed) {
        zoomed = newZoomed;
    }
}
