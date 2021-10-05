package me.noat.sexhack.client.hacks.misc;

import me.noat.sexhack.client.hacks.Module;
import me.noat.sexhack.client.hacks.WurstplusCategory;

public
class RPC extends Module {
    public
    RPC() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "RPC";
        this.description = "cool";
        this.tag = "DiscordRPC";
    }

    @Override
    public
    void enable() {
        me.noat.sexhack.RPC.startRPC();
    }

    @Override
    public
    void disable() {
        me.noat.sexhack.RPC.stopRPC();
    }
}
