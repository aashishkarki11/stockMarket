package hamro.stockmarket.stockmarket.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Configuration class for setting up Redis caching in the application.
 * This class defines beans for Redis cache configuration and customization
 * of the RedisCacheManager.
 * Author: [Aashish Karki]
 */
@Configuration
public class RedisConfig {

  /**
   * Configures the default Redis cache settings.
   *
   * @return a RedisCacheConfiguration object with the specified settings: - Entry TTL
   * (time-to-live) of 60 minutes - Caching of null values is disabled - Values are
   * serialized using GenericJackson2JsonRedisSerializer
   */
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
      return RedisCacheConfiguration.defaultCacheConfig()
          .entryTtl(Duration.ofMinutes(60))
          .disableCachingNullValues()
          .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

  /**
   * Customizes the RedisCacheManager with specific cache configurations for different
   * caches.
   *
   * @return a RedisCacheManagerBuilderCustomizer that customizes the cache manager with:
   * - An "itemCache" cache with an entry TTL of 10 minutes - A "customerCache" cache with
   * an entry TTL of 5 minutes
   */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheMangerBuilderCustomizer() {
      return builder -> builder
          .withCacheConfiguration("itemCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
          .withCacheConfiguration("customerCache", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }
  }
