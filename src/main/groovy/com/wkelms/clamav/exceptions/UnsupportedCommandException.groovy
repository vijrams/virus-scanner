package com.wkelms.clamav.exceptions

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class UnsupportedCommandException extends RuntimeException {

    public UnsupportedCommandException(String command) {
        super(String.format("Unsupported command: %s", command))
    }
}
