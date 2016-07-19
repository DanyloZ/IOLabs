package com.danylo.logAnalyzer;

import java.time.LocalDateTime;


public class LogToken {
    private LocalDateTime time;
    private HttpMethod method;
    private String message;

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getMessage() {
        return message;
    }

    public LogToken(LocalDateTime time, HttpMethod method, String message) {
        this.time = time;
        this.method = method;
        this.message = message;
    }
}
