package com.wkelms.clamav.commands.scan

import com.wkelms.clamav.commands.Command
import com.wkelms.clamav.exceptions.InvalidResponseException
import com.wkelms.clamav.exceptions.ScanFailureException

import java.util.regex.Pattern

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public abstract class ScanCommand extends Command<ScanResult> {

    private static final Pattern RESPONSE_OK = Pattern.compile('OK$', Pattern.UNIX_LINES )
    private static final Pattern RESPONSE_VIRUS_FOUND = Pattern.compile('(.+) FOUND$', Pattern.MULTILINE & Pattern.UNIX_LINES )
    private static final Pattern RESPONSE_ERROR = Pattern.compile('(.+) ERROR', Pattern.UNIX_LINES )
    private static final Pattern RESPONSE_VIRUS_FOUND_LINE = Pattern.compile('(.+: )?(?<filePath>.+): (?<virus>.+) FOUND$', Pattern.UNIX_LINES )

    @Override
    protected ScanResult parseResponse(String responseString) {
        try {
            responseString = responseString.replace("stream: ","")
            if(RESPONSE_OK.matcher(responseString).find()){
                return new ScanResult(ScanResult.Status.OK, responseString)
            }
            if (RESPONSE_VIRUS_FOUND.matcher(responseString).find()) {
                responseString = responseString.replace(" FOUND","").split(" ").toString()
                return new ScanResult(ScanResult.Status.VIRUS_FOUND, responseString)
            }
            if (RESPONSE_ERROR.matcher(responseString).matches()) {
                throw new ScanFailureException(responseString)
            }
        } catch (IllegalStateException e) {
            throw new InvalidResponseException(responseString)
        }
        throw new InvalidResponseException(responseString)
    }
}
