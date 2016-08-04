package com.danylo.loganalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class LogAnalyzer {
    public static Collection<LogToken> analyze(String path, java.time.LocalDateTime timeFrom, LocalDateTime timeTo) throws IOException{
        ArrayList<LogToken> tokensList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
            while (reader.ready()) {
                String line = reader.readLine();
                String timeString = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XXXX", Locale.US);
                LocalDateTime time = LocalDateTime.parse(timeString, formatter);
                if(time.isAfter(timeTo)) {
                    break;
                }
                if (time.isAfter(timeFrom) || time.isEqual(timeFrom)) {
                    String content = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                    String methodString = content.substring(0, content.indexOf(" "));
                    HttpMethod method = null;
                    if ("GET".equals(methodString)) {
                        method = HttpMethod.GET;
                    }
                    if ("POST".equals(methodString)) {
                        method = HttpMethod.POST;
                    }
                    String message = content.substring(content.indexOf(" ") + 1, content.length());
                    tokensList.add(new LogToken(time, method, message));
                }
            }
        return tokensList;
    }
}
