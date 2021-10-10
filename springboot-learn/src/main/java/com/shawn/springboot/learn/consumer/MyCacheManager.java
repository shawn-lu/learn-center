package com.shawn.springboot.learn.consumer;

import com.yonghui.common.cache.GenericValueCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class MyCacheManager extends GenericValueCacheManager<String, String> {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String get(String id) {
        return super.get(id);
    }

    @Override
    protected String makeFullCacheKey(String id) {
        return id;
    }


    public void updateNotModifyExpireTime(String id,String newVal){
        String key = this.makeFullCacheKey(id);
        Long time = redisTemplate.getExpire(key);
        System.out.println(time);
        Date date = new Date(System.currentTimeMillis() + time * 1000);

        redisTemplate.opsForValue().set(key,newVal);
        redisTemplate.expireAt(key,date);
    }


    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }
}
