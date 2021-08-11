package me.travis.wurstplus.wurstplustwo.util;

import me.travis.wurstplus.wurstplustwo.manager.HWID;
import net.minecraft.client.Minecraft;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HWIDUtil {
    public static ArrayList s;
    public static String hwid;
    // from kambing leak of perry phobos because i have no idea on how to get hwid :^)
    public static String getHWID() {
        return DigestUtils.sha256Hex ( DigestUtils.sha256Hex ( System.getenv ( "os" )
                + System.getProperty ( "os.name" )
                + System.getProperty ( "os.arch" )
                + System.getProperty ( "user.name" )
                + System.getenv ( "SystemRoot" )
                + System.getenv ( "HOMEDRIVE" )
                + System.getenv ( "PROCESSOR_LEVEL" )
                + System.getenv ( "PROCESSOR_REVISION" )
                + System.getenv ( "PROCESSOR_IDENTIFIER" )
                + System.getenv ( "PROCESSOR_ARCHITECTURE" )
                + System.getenv ( "PROCESSOR_ARCHITEW6432" )
                + System.getenv ( "NUMBER_OF_PROCESSORS" )
        ) );
    }

    public static List <String> checkHWIDUrl() {
        List< String > s = new ArrayList<>();
        try {
            final URL url = new URL ( HWID.coolLink );
            BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader ( url.openStream ( ) ) );
            String hwid;
            while ( ( hwid = bufferedReader.readLine ( ) ) != null ) {
                s.add ( hwid );
            }
        } catch ( Exception e ) {
        }
        return s;
    }

    public static boolean isHwidThere() {
        List yes = checkHWIDUrl();
        if (yes.contains(getHWID())) {
            return true;
        } else {
            return false;
        }
    }
}
