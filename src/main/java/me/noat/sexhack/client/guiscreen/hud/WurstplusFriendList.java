package me.noat.sexhack.client.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.noat.sexhack.SexHack;
import me.noat.sexhack.client.guiscreen.render.pinnables.WurstplusPinnable;
import me.noat.sexhack.client.util.WurstplusOnlineFriends;
import net.minecraft.entity.Entity;

public class WurstplusFriendList extends WurstplusPinnable {

    public static ChatFormatting bold = ChatFormatting.BOLD;
    int passes;

    public WurstplusFriendList() {
        super("Friends", "Friends", 1, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").getValue(1);
        int nl_g = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").getValue(1);
        int nl_b = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").getValue(1);
        int nl_a = SexHack.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").getValue(1);

        String line1 = bold + "block game goons: ";

        passes = 0;

        create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);

        if (!WurstplusOnlineFriends.getFriends().isEmpty()) {
            for (Entity e : WurstplusOnlineFriends.getFriends()) {
                passes++;
                create_line(e.getName(), this.docking(1, e.getName()), this.get(line1, "height") * passes, nl_r, nl_g, nl_b, nl_a);
            }
        }

        this.set_width(this.get(line1, "width") + 2);
        this.set_height(this.get(line1, "height") + 5);
    }

}
