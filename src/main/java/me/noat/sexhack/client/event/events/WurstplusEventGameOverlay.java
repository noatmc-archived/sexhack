package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;
import net.minecraft.client.gui.ScaledResolution;

public class WurstplusEventGameOverlay extends WurstplusEventCancellable {

    public float partial_ticks;
    private final ScaledResolution scaled_resolution;

    public WurstplusEventGameOverlay(float partial_ticks, ScaledResolution scaled_resolution) {
        
        this.partial_ticks = partial_ticks;
        this.scaled_resolution = scaled_resolution;

    }

    public ScaledResolution get_scaled_resoltion() {
        return scaled_resolution;
    }
    
}