package me.noat.sexhack.client.util;

public class WurstplusWatermarkUtil {

    private static String message;

    public static void set_message(String message) {
        WurstplusWatermarkUtil.message = message;
    }

    public static String get_message() {
      return message;
    }

}
