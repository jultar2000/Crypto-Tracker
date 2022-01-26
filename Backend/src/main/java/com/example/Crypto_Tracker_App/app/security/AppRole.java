package com.example.Crypto_Tracker_App.app.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.Crypto_Tracker_App.app.security.AppPermission.*;

@AllArgsConstructor
@Getter
public enum AppRole {
    USER(Sets.newHashSet(USER_WRITE,USER_READ)),
    ADMIN(Sets.newHashSet(READ_USER_DATA, USER_READ, DB_UPDATE));

    private final Set<AppPermission> permissions;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
