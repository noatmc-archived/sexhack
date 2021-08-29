package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.gui.GuiScreen;

// External.


public class WurstplusEventGUIScreen extends WurstplusEventCancellable {
    private final GuiScreen guiscreen;

    public WurstplusEventGUIScreen(GuiScreen screen) {
        super();

        guiscreen = screen;
    }

    public GuiScreen get_guiscreen() {
        return guiscreen;
    }
}