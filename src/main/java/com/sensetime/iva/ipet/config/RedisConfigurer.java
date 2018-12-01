package com.sensetime.iva.ipet.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensetime.iva.ipet.entity.AccountEntity;
import com.sensetime.iva.ipet.util.AccountUtils;
import com.sensetime.iva.ipet.web.controller.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * @Author: ChaiLongLong
 * //开启缓存
 */
@Configuration
@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfigurer.class);
    @Autowired
    private Environment env;
    /**
     * 生成key的策略
     * @return 返回key
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    /**
     * 管理缓存
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        //rcm.setDefaultExpiration(60); //秒
        return rcm;
    }

    /**
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * key=userId+serviceImplName
     * @return 立项时的key
     */
    @Bean
    public KeyGenerator projectKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                try {
                    //使用同一服务器上的redis服务，区分生产环境和正式环境的key
                    String[] activeProfiles = env.getActiveProfiles();
                    if(activeProfiles.length>0&&"test".equals(activeProfiles[0])){
                        sb.append("test_");
                    }
                    AccountEntity accountEntity = AccountUtils.getCurrentHr();
                    sb.append(accountEntity.getId());
                    sb.append(target.getClass().getSimpleName());
                }catch (Exception e){
                    logger.warn("get login account error {}",e.getMessage());
                    sb.append(-1);
                    sb.append(target.getClass().getSimpleName());
                }
                return sb.toString();
            }
        };
    }
}
