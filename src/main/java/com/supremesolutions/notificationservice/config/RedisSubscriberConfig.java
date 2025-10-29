package com.supremesolutions.notificationservice.config;

import com.supremesolutions.notificationservice.listener.NotificationEventSubscriber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisSubscriberConfig {

    @Autowired
    private ApplicationContext applicationContext; // ✅ safer access after bean creation

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            NotificationEventSubscriber subscriber) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, new ChannelTopic("notification-events"));
        return container;
    }

    @PostConstruct
    public void startRedisListener() {
        new Thread(() -> {
            try {
                Thread.sleep(5000); // small delay to let Redis & env fully load
                RedisMessageListenerContainer container =
                        applicationContext.getBean(RedisMessageListenerContainer.class);

                container.start();
                System.out.println("✅ Redis listener started successfully!");
            } catch (Exception e) {
                System.err.println("⚠️ Failed to start Redis listener: " + e.getMessage());
            }
        }).start();
    }
}
