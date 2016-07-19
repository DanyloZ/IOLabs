package com.danylo.logAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class LogAnalyzer {
    public static Collection<LogToken> analyze(String path, java.time.LocalDateTime timeFrom, LocalDateTime timeTo) {
        ArrayList<LogToken> tokensList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while (reader.ready()) {
                String line = reader.readLine();
                String timeString = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss XXXX", Locale.US);
                LocalDateTime time = LocalDateTime.parse(timeString, formatter);
                if(time.isAfter(timeTo)) {
                    break;
                }
                if (time.isAfter(timeFrom) || time.isEqual(timeFrom)) {
                    String message = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                    String methodString = message.substring(0, message.indexOf(" "));
                    HttpMethod method = null;
                    if (methodString.equals("GET")) {
                        method = HttpMethod.GET;
                    }
                    if (methodString.equals("POST")) {
                        method = HttpMethod.POST;
                    }
                    message = message.substring(message.indexOf(" ") + 1, message.length());
                    tokensList.add(new LogToken(time, method, message));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return tokensList;
    }
}
