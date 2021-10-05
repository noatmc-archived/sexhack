package me.noat.sexhack.client.event.events;

import me.noat.sexhack.client.event.WurstplusEventCancellable;

public
class WurstplusEventPlayerTravel extends WurstplusEventCancellable {

    public final float Strafe;
    public final float Vertical;
    public final float Forward;

    public
    WurstplusEventPlayerTravel(float p_Strafe, float p_Vertical, float p_Forward) {
        Strafe = p_Strafe;
        Vertical = p_Vertical;
        Forward = p_Forward;
    }

}