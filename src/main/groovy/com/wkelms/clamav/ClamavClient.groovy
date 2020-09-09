package com.wkelms.clamav

import com.wkelms.clamav.commands.Command
import com.wkelms.clamav.commands.Ping
import com.wkelms.clamav.commands.Reload
import com.wkelms.clamav.commands.scan.InStream
import com.wkelms.clamav.commands.scan.ScanResult

/*
import com.wkelms.clamav.commands.Reload
import com.wkelms.clamav.commands.Stats
import com.wkelms.clamav.commands.Version
import com.wkelms.clamav.commands.VersionCommands
import com.wkelms.clamav.commands.scan.ContScan
import com.wkelms.clamav.commands.scan.InStream
import com.wkelms.clamav.commands.scan.MultiScan
import com.wkelms.clamav.commands.scan.Scan
import com.wkelms.clamav.commands.scan.result.ScanResult

 */
import com.wkelms.clamav.configuration.Platform
import com.wkelms.clamav.exceptions.ClamavException
import com.wkelms.clamav.exceptions.UnsupportedCommandException
import com.wkelms.clamav.commands.VersionCommands

//import lombok.Getter

import java.nio.file.Path

/**
 * Created by vijay.ramamoorthi on 6/4/2020, Thu
 **/
class ClamavClient {
    public static final int DEFAULT_SERVER_PORT = 3310
    public static final Platform DEFAULT_SERVER_PLATFORM = Platform.JVM_PLATFORM

    private InetSocketAddress server
    private Platform serverPlatform
    private Collection<String> availableCommands

    public ClamavClient(String serverHostname, int serverPort) {
        this.server = new InetSocketAddress(serverHostname, serverPort)
        this.serverPlatform = DEFAULT_SERVER_PLATFORM
    }

    public void ping() throws ClamavException {
        sendCommand(new Ping())
    }

    public void reloadVirusDatabases() throws ClamavException {
        sendCommand(new Reload())
    }

    public ScanResult scan(InputStream inputStream) throws ClamavException {
        return sendCommand(new InStream(inputStream))
    }

    /*   public String version() throws xyz.capybara.clamav.exceptions.ClamavException {
           return sendCommand(new Version())
       }

       public String stats() throws xyz.capybara.clamav.exceptions.ClamavException {
           return sendCommand(new Stats())
       }



       public void shutdownServer() throws xyz.capybara.clamav.exceptions.ClamavException {
           sendCommand(new Shutdown())
       }


       public ScanResult scan(Path path) throws xyz.capybara.clamav.exceptions.ClamavException {
           return scan(path, false)
       }

       public ScanResult scan(Path path, boolean continueScan) throws xyz.capybara.clamav.exceptions.ClamavException {
           if (continueScan) {
               return sendCommand(new ContScan(serverPlatform.toServerPath(path)))
           } else {
               return sendCommand(new Scan(serverPlatform.toServerPath(path)))
           }
       }

       public ScanResult parallelScan(Path path) throws xyz.capybara.clamav.exceptions.ClamavException {
           return sendCommand(new MultiScan(serverPlatform.toServerPath(path)))
       }
   */

    private Collection<String> getAvailableCommands() {
        if (availableCommands == null) {
            availableCommands = new VersionCommands().send(server)
        }
        return availableCommands
    }

    private <T> T sendCommand(Command<T> command) throws ClamavException {
        try {
            if (getAvailableCommands() != null && getAvailableCommands().contains(command.getCommandString())) {
                return command.send(server)
            }
            throw new UnsupportedCommandException(command.getCommandString())
        } catch (Exception cause) {
            throw new ClamavException(cause)
        }
    }
}
