package com.example.Crypto_Tracker_App.app.exceptions;

public class MailSendException extends RuntimeException {
    public MailSendException(String message, Exception exception){
        super(message, exception);
    }
}
