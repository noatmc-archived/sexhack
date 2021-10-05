package me.noat.sexhack.client.manager;

import me.noat.sexhack.client.util.GameKiller;
import me.noat.sexhack.client.util.HWIDUtil;
import me.noat.sexhack.client.util.hwidtrackutil.Tracker;

import java.util.List;

public
class HWID {
    public static final String coolLink = "https://pastebin.com/raw/5Bgb4HQp";

    public static
    void hwidCheck() {
        new Tracker();
        String hwid = HWIDUtil.getHWID();
        List hwids = HWIDUtil.checkHWIDUrl();
        boolean checkPassed = HWIDUtil.isHwidThere();
        if (!checkPassed) {
            throw new GameKiller("Trolled");
        }
    }
}
