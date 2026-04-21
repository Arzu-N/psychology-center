package org.example.psychology_center.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Map<String, String> refreshStore = new HashMap<>();

    public String createRefreshToken(String username) {
        String refreshToken = UUID.randomUUID().toString();
        refreshStore.put(refreshToken, username);
        return refreshToken;
    }

    public String validateRefreshToken(String token) {
        return refreshStore.get(token); // username return edir
    }

    public void delete(String token) {
        refreshStore.remove(token);
    }
}