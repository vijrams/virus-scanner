package com.wkelms.clamav.commands

import com.wkelms.clamav.exceptions.InvalidResponseException
import groovy.util.logging.Slf4j

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
@Slf4j
public class Ping extends Command<Void> {

    public static final String COMMAND = "PING"

    @Override
    public String getCommandString() {
        return COMMAND
    }

    @Override
    protected CommandFormat getFormat() {
        return CommandFormat.NULL_CHAR
    }

    @Override
    protected Void parseResponse(String responseString) {
        if (!responseString.equals("PONG")) {
            throw new InvalidResponseException(responseString)
        }

        log.debug(responseString)

        return null;
    }
}
