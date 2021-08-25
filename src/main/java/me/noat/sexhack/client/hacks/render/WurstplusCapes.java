package me.noat.sexhack.client.hacks.render;

import me.noat.sexhack.client.guiscreen.settings.Setting;
import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.Module;

public class WurstplusCapes extends Module {

    public WurstplusCapes() {
        super(WurstplusCategory.WURSTPLUS_RENDER);

        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }

    Setting cape = create("Cape", "CapeCape", "New", combobox("New", "OG", "Clockwork", "Clockwork MC", "ifarticuhm", "Ping Players", "teejwrld", "cringesyringe"));

}
