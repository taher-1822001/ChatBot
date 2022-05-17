package com.example.chatbot;

public class chatsModal {
    private String message;
    private String sender;

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public chatsModal(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }
}
