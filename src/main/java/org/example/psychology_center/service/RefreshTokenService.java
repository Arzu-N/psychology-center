package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.exception.ValidationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final long TTL_DAYS = 7;

    public String createRefreshToken(String username) {

        String userKey = "refresh:user:" + username;

        Object oldTokenObj =
                redisTemplate.opsForValue().get(userKey);

        if (oldTokenObj != null) {

            String oldToken = oldTokenObj.toString();

            redisTemplate.delete("refresh:token:" + oldToken);
        }

        String newToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                userKey,
                newToken,
                TTL_DAYS,
                TimeUnit.DAYS
        );

        redisTemplate.opsForValue().set(
                "refresh:token:" + newToken,
                username,
                TTL_DAYS,
                TimeUnit.DAYS
        );

        return newToken;
    }

    public void validateRefreshToken(String username, String token) {

        Object savedToken =
                redisTemplate.opsForValue()
                        .get("refresh:user:" + username);

        if (savedToken == null || !savedToken.equals(token)) {
            throw new ValidationException("Invalid refresh token");
        }
    }

    public String getUsernameByToken(String token) {

        Object username =
                redisTemplate.opsForValue()
                        .get("refresh:token:" + token);

        if (username == null) {
            throw new ValidationException("Invalid refresh token");
        }

        return username.toString();
    }

    public String rotateRefreshToken(String username) {

        String userKey = "refresh:user:" + username;

        Object oldTokenObj =
                redisTemplate.opsForValue().get(userKey);

        if (oldTokenObj != null) {

            String oldToken = oldTokenObj.toString();

            redisTemplate.delete("refresh:token:" + oldToken);
        }

        String newToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(
                userKey,
                newToken,
                TTL_DAYS,
                TimeUnit.DAYS
        );

        redisTemplate.opsForValue().set(
                "refresh:token:" + newToken,
                username,
                TTL_DAYS,
                TimeUnit.DAYS
        );

        return newToken;
    }

    public void logout(String username, String token) {

        redisTemplate.delete("refresh:user:" + username);
        redisTemplate.delete("refresh:token:" + token);
    }
}