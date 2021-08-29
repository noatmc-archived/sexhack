package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.event.events.WurstplusEventRender;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.Module;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

public class WurstplusAlwaysNight extends Module {

    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }

    @EventHandler
    private Listener<WurstplusEventRender> on_render = new Listener<>(event -> {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    });

    @Override
    public void update() {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    }
}
