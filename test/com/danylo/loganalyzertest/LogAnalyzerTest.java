package com.danylo.loganalyzertest;

import com.danylo.loganalyzer.HttpMethod;
import com.danylo.loganalyzer.LogAnalyzer;
import com.danylo.loganalyzer.LogToken;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

public class LogAnalyzerTest {
    @Test
    public void analyzeTest() throws Exception {
        String path = "test/com/danylo/loganalyzertest/accesslog/access_log";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XXXX", Locale.US);
        LocalDateTime timeFrom = LocalDateTime.parse("07/Mar/2004:17:21:44 -0800", formatter);
        LocalDateTime timeTo = LocalDateTime.parse("07/Mar/2004:17:58:00 -0800", formatter);
        ArrayList<LogToken> logs = (ArrayList<LogToken>) LogAnalyzer.analyze(path, timeFrom, timeTo);
        assertTrue(logs.size() == 17);
        LogToken log0 = logs.get(0);
        String message0 = log0.getMessage();
        assertTrue(message0.equals("/twiki/bin/attach/TWiki/TablePlugin HTTP/1.1"));
        assertTrue(log0.getMethod() == HttpMethod.GET);
    }
}
