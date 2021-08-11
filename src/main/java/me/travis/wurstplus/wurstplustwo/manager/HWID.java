package me.travis.wurstplus.wurstplustwo.manager;

import me.travis.wurstplus.wurstplustwo.util.GameKiller;
import me.travis.wurstplus.wurstplustwo.util.HWIDUtil;
import me.travis.wurstplus.wurstplustwo.util.hwidtrackutil.Tracker;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class HWID {
    public static String coolLink = "https://pastebin.com/raw/5Bgb4HQp";
    private static   boolean checkPassed;

    public static void hwidCheck() {
        new Tracker();
        String hwid = HWIDUtil.getHWID();
        List hwids = HWIDUtil.checkHWIDUrl();
        checkPassed = HWIDUtil.isHwidThere();
        if (!checkPassed) {
            throw new GameKiller("Trolled");
        }
    }
}
