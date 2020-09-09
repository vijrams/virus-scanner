package com.wkelms.clamav.commands

import com.wkelms.clamav.exceptions.CommunicationException
import com.wkelms.clamav.exceptions.UnknownCommandException
import groovy.util.logging.Slf4j

import java.nio.ByteBuffer
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
@Slf4j
public abstract class Command<T> {

    private static final String UNKNOWN_COMMAND = "UNKNOWN COMMAND"
    public abstract String getCommandString()

    public T send(InetSocketAddress server) {
        try {
            SocketChannel socketChannel = SocketChannel.open(server)
            socketChannel.write(getRawCommand())

            return readResponse(socketChannel)
        } catch (IOException e) {
            throw new CommunicationException(e)
        }
    }

    protected abstract CommandFormat getFormat()

    protected ByteBuffer getRawCommand() {
        StringBuilder rawCommand = new StringBuilder()
        rawCommand.append(getFormat().prefix)
                .append(getCommandString())
                .append(getFormat().terminator)
        log.info(rawCommand.toString())
        return ByteBuffer.wrap(rawCommand.toString().getBytes(StandardCharsets.US_ASCII))
    }

    protected T readResponse(SocketChannel socketChannel) throws IOException {
        StringBuilder responseStringBuilder = new StringBuilder()
        ByteBuffer rawResponsePart = ByteBuffer.allocate(32)

        for (int read = socketChannel.read(rawResponsePart); read > -1; read = socketChannel.read(rawResponsePart)) {
            String rawResponsePartString = new String(rawResponsePart.array(), StandardCharsets.US_ASCII)
            rawResponsePartString = rawResponsePartString.substring(0, read)
            responseStringBuilder.append(rawResponsePartString)
            rawResponsePart = ByteBuffer.allocate(32)
        }

        String responseString = removeResponseTerminator(responseStringBuilder.toString())

        if (responseString.equals(UNKNOWN_COMMAND)) {
            throw new UnknownCommandException(getCommandString())
        }

        log.info("{} - Response: {}", getCommandString(), responseString)

        return parseResponse(responseString)
    }

    private String removeResponseTerminator(String responseString) {
        return responseString.substring(0, responseString.lastIndexOf(getFormat().terminator.toString()))
    }

    protected abstract T parseResponse(String responseString)

    public enum CommandFormat {
        NULL_CHAR('z' as char, '\0' as char),
        NEW_LINE('n' as char, '\n' as char);

        private char prefix, terminator;

        CommandFormat(char prefix, char terminator) {
            this.prefix = prefix
            this.terminator = terminator
        }

        public static CommandFormat fromPrefix(char prefix) {
            CommandFormat format = null;
            switch (prefix) {
                case 'z':
                    format = NULL_CHAR
                    break;
                case 'n':
                    format = NEW_LINE
                    break;
            }
            return format;
        }
    }
}
