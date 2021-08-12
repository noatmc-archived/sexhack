package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.guiscreen.settings.WurstplusSetting;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.WurstplusHack;

public class WurstplusCapes extends WurstplusHack {

    public WurstplusCapes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }

    WurstplusSetting cape = create("Cape", "CapeCape", "New", combobox("New", "OG", "Clockwork", "Clockwork MC", "ifarticuhm", "Ping Players", "teejwrld", "cringesyringe"));

}
