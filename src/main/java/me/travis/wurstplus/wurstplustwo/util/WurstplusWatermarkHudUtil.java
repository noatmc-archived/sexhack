package me.travis.wurstplus.wurstplustwo.util;

import me.travis.wurstplus.Wurstplus;

public class WurstplusWatermarkHudUtil {

    private static String message;

    public static void set_message(String message) {
        WurstplusWatermarkHudUtil.message = message;
    }

    public static String get_message() {
      return message;
    }
}
