package com.wkelms.clamav.exceptions

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class InvalidResponseException extends RuntimeException {

    public InvalidResponseException(String responseString) {
        super(String.format("Invalid response: %s", responseString))
    }
}
