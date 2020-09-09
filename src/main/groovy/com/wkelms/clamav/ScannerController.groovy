package com.wkelms.clamav

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * Created by vijay.ramamoorthi on 6/2/2020, Tue
 **/
@RestController
@Slf4j
@RequestMapping(value = "/clamav")
class ScannerController {

    @Value('${app.clamd-host}')
    private String host

    @Value('${app.clamd-port}')
    private int port

    @RequestMapping(value = "/ping", method= RequestMethod.GET, produces = "application/json" )
    public ping(){
        try {
            ClamavClient client = new ClamavClient(host, port)
            client.ping()
            return buildJsonResponse("OK", "clamd available")
        }catch(Exception e){
            log.error(e.stackTrace.toString())
            return buildJsonResponse("ERROR", "clamd not available")
        }
    }

    @RequestMapping(value = "/scan", method= RequestMethod.POST, produces = "application/json" )
    public scan(@RequestParam(value = "lawId", required = true) String lawId,
                @RequestParam(value = "file", required = true) MultipartFile file){
        try {
            ClamavClient client = new ClamavClient(host, port)
            log.info("Scaning the $file.name for user $lawId")
            def r = client.scan(file.inputStream)
            return buildJsonResponse(r.status.toString(), r.foundViruses)
        }catch(Exception e){
            log.error("Exception while scanning $file.name for user $lawId $e")
            return buildJsonResponse("ERROR", e.message)
        }
    }

    @RequestMapping(value = "/reload", method= RequestMethod.POST, produces = "application/json" )
    public reload(){
        try {
            ClamavClient client = new ClamavClient(host, port)
            client.reloadVirusDatabases()
            return buildJsonResponse("OK", "ok")
        }catch(Exception e){
            return buildJsonResponse("ERROR", e.message)
        }
    }

    def buildJsonResponse(String success, def message) {
        JsonBuilder builder = new JsonBuilder()
        message = message?:""
        builder{
            result  success
            msg message.toString()
        }
        if(log.isDebugEnabled()) {
            log.debug("response = " + builder.toString())
        }
        return builder.toString()
    }


}
