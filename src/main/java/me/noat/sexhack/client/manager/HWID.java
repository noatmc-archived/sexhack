package me.noat.sexhack.client.manager;

import me.noat.sexhack.client.util.GameKiller;
import me.noat.sexhack.client.util.HWIDUtil;
import me.noat.sexhack.client.util.hwidtrackutil.Tracker;

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
