package me.travis.wurstplus.wurstplustwo.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public
class WurstplusEventStage
        extends Event {
    private int stage;

    public
    WurstplusEventStage ( ) {
    }

    public
    WurstplusEventStage ( int stage ) {
        this.stage = stage;
    }

    public
    int getStage ( ) {
        return this.stage;
    }

    public
    void setStage ( int stage ) {
        this.stage = stage;
    }
}
