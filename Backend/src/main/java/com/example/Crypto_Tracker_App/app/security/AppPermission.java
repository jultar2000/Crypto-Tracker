package com.example.Crypto_Tracker_App.app.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppPermission {
    READ_USER_DATA("manage:readUser"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    DB_UPDATE("db:update");

    private final String permission;
}