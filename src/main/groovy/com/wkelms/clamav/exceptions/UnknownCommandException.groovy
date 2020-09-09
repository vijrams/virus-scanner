package com.wkelms.clamav.exceptions

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class UnknownCommandException extends RuntimeException {

    public UnknownCommandException(String command) {
        super(String.format("Unknown command: %s", command))
    }
}
