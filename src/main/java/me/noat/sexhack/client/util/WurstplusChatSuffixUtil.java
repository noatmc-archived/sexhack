package me.noat.sexhack.client.util;

public class WurstplusChatSuffixUtil {

    private static String message;

    public static String get_message() {
        if (message == null) {
            message = "wurstplus two";
            return message;
        } else {
            return message;
        }
    }

    public static void set_message(String message) {
        WurstplusChatSuffixUtil.message = message;
    }

}
