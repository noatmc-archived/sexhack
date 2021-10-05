package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;

public
class WurstplusEventPlayerJump extends WurstplusEventCancellable {

    public final double motion_x;
    public final double motion_y;

    public
    WurstplusEventPlayerJump(double motion_x, double motion_y) {
        super();

        this.motion_x = motion_x;
        this.motion_y = motion_y;
    }

}