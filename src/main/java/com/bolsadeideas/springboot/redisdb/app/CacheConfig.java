package com.bolsadeideas.springboot.redisdb.app;

import java.util.HashMap;
import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { FreeMarkerAutoConfiguration.class })
@EnableCaching
public class CacheConfig {
	
	@Value("${spring.redis.host}")
	private String redisHost;
	
	@Value("${spring.redis.port}")
	private String redisPort;
	
	@Bean
	public CacheManager getCache(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();
		config.put("employeesListCache", new CacheConfig());
		return new RedissonSpringCacheManager(redissonClient);
	}
	
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://"+redisHost+":"+redisPort);
		return Redisson.create(config);
	}

}
