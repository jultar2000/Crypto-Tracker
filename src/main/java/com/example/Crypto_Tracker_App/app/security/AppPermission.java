package com.example.Crypto_Tracker_App.app.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppPermission {
    USER_READ_ALL("user:readAll"),
    USER_WRITE("user:write"),
    DB_UPDATE("db:update");

    private final String permission;
}
