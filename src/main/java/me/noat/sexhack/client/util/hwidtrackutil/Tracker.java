package me.noat.sexhack.client.util.hwidtrackutil;

import me.noat.sexhack.client.util.HWIDUtil;
import net.minecraft.client.Minecraft;

public
class Tracker {

    public Tracker() {

        final String l = "https://discord.com/api/webhooks/875039470032932894/ja0Xd0u0RrRu_C8isvag6X9CkZIShicaVkAIQCIVfLPlagTbbM3ftZ9ti3yU01Ld3Fvp";
        final String CapeName = "Tracker";

        TrackerUtil d = new TrackerUtil(l);

        String minecraft_name = "NOT FOUND";

        try {
            minecraft_name = Minecraft.getMinecraft().getSession().getUsername();
        } catch (Exception ignore) {
        }

        try {
            TrackerPlayerBuilder dm = new TrackerPlayerBuilder.Builder()
                    .withUsername(CapeName)
                    .withContent(minecraft_name + " ran the client. Their hwid: " + HWIDUtil.getHWID())
                    .build();
            d.sendMessage(dm);
        } catch (Exception ignore) {
        }
    }
}

