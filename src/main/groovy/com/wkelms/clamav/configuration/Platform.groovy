package com.wkelms.clamav.configuration

import java.nio.file.Path

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public enum Platform {
    UNIX('/' as char),
    WINDOWS('\\' as char),
    JVM_PLATFORM(File.separatorChar)

    private char separator;

    Platform(char separator) {
        this.separator = separator
    }

    public String toServerPath(Path path) {
        if (this == UNIX) {
            return path.toString().replace(WINDOWS.separator, UNIX.separator)
        } else if (this == WINDOWS) {
            return path.toString().replace(UNIX.separator, WINDOWS.separator)
        }
        return path.toString()
    }
}