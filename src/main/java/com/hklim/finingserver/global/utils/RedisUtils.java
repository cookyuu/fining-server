package com.hklim.finingserver.global.utils;

import com.hklim.finingserver.global.exception.ApplicationErrorException;
import com.hklim.finingserver.global.exception.ApplicationErrorType;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final StringRedisTemplate redisTemplate;

    // key를 통해 Value 리턴
    public String getData(String key) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        } catch (RedisException e) {
            throw new ApplicationErrorException(ApplicationErrorType.REDIS_ERROR, "[REDIS-GET] Fail to get Data in Redis. ");
        }
    }

    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    // 유효시간 동안 key,value 저장
    public void setDataExpire(String key, String value, long duration) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            Duration expiredDuration = Duration.ofSeconds(duration);
            valueOperations.set(key, value, expiredDuration);
        } catch (RedisException e) {
            throw new ApplicationErrorException(ApplicationErrorType.REDIS_ERROR, "[REDIS-SAVE] Fail to save Data in Redis. ");
        }
    }

    // 삭제
    public void deleteData(String key) {
        try {
            redisTemplate.delete(key);
        } catch (RedisException e) {
            throw new ApplicationErrorException(ApplicationErrorType.REDIS_ERROR, "[REDIS-DELETE] Fail to delete data in Redis. ");
        }
    }

}