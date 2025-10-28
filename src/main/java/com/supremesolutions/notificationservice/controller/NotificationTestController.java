package com.supremesolutions.notificationservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/test-redis")
    public String testRedis() {
        try {
            redisTemplate.opsForValue().set("notification_test", "Hello from Notification Service!");
            String value = redisTemplate.opsForValue().get("notification_test");
            return "✅ Redis Connected (Notification Service)! Value: " + value;
        } catch (Exception e) {
            return "❌ Redis connection failed: " + e.getMessage();
        }
    }
}
