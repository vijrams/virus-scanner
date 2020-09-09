package com.wkelms.clamav.commands.scan

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
class ScanResult {
    private Status status
    private String foundViruses

    ScanResult(def status, def foundViruses){
        this.status = status
        this.foundViruses = foundViruses
    }

    public enum Status {
        OK, VIRUS_FOUND
    }
}
