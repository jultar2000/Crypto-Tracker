package com.example.Crypto_Tracker_App.app.exceptions;

public class AppException extends RuntimeException {

    public AppException(String message, Exception exception){
        super(message, exception);
    }

    public AppException(String message){
        super(message);
    }

}
