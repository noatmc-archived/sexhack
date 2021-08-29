package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;

public class WurstplusEventMotionUpdate extends WurstplusEventCancellable {

    public int stage;

    public WurstplusEventMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }

}