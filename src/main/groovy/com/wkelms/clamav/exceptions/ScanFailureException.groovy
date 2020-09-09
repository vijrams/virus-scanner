package com.wkelms.clamav.exceptions

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class ScanFailureException extends RuntimeException {

    public ScanFailureException(String responseString) {
        super(String.format("Scan failure: %s", responseString));
    }
}