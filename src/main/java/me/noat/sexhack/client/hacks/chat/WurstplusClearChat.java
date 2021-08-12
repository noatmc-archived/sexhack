package me.noat.sexhack.client.hacks.chat;


import me.noat.sexhack.client.hacks.WurstplusCategory;
import me.noat.sexhack.client.hacks.WurstplusHack;

public class WurstplusClearChat extends WurstplusHack {
    public WurstplusClearChat() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Clear Chatbox";
        this.tag = "ClearChatbox";
        this.description = "Removes the default minecraft chat outline.";
    }
}