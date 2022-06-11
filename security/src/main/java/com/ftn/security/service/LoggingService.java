package com.ftn.security.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Service
public class LoggingService {
    private static Logger logger = Logger.getLogger(LoggingService.class.getName());
    public FileHandler fileHandler= new FileHandler("log%g.log",15000000,40,true);

    public LoggingService() throws IOException {
        this.init();
    }

    public void init(){
        logger.addHandler(fileHandler);
    }

    public void MakeLogWithRecord(LogRecord record){
        logger.log(record);
    }

    public void MakeInfoLog(String record){
        logger.info(record);
    }

    public void MakeWarningLog(String record){
        logger.warning(record);
    }

    public void MakeSevereLog(String record){
        logger.severe(record);
    }

}
