package com.wkelms.clamav.commands

import com.wkelms.clamav.exceptions.InvalidResponseException

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
public class VersionCommands extends Command<Collection<String>> {

    public static final String COMMAND = "VERSIONCOMMANDS"
    private static final String COMMANDS_START_TAG = "| COMMANDS:"

    @Override
    public String getCommandString() {
        return COMMAND
    }

    @Override
    protected CommandFormat getFormat() {
        return CommandFormat.NEW_LINE
    }

    @Override
    protected Collection<String> parseResponse(String responseString) {
        int commandsStartPos = responseString.indexOf(COMMANDS_START_TAG)

        if (commandsStartPos == -1) {
            throw new InvalidResponseException(responseString)
        }
        return responseString.split(" ")
    }
}

