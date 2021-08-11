package me.travis.wurstplus.wurstplustwo.util;

public class GameKiller extends RuntimeException {

    public GameKiller ( final String msg ) {
        super ( msg );
        this.setStackTrace ( new StackTraceElement[0] );
    }

    @Override
    public synchronized
    Throwable fillInStackTrace ( ) {
        return this;
    }
}