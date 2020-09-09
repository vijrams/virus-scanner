package com.wkelms.clamav.exceptions

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class CommunicationException extends RuntimeException {

    public CommunicationException(Throwable cause) {
        super("Error while communicating with the server", cause)
    }
}