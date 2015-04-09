package com.github.nekop.example.jaxrs.jsonp;

public class RestMessage {

    public RestMessage() {
    }

    public RestMessage(String message) {
        setMessage(message);
    }

    private String message;
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
