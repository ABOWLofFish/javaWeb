package javaWeb.test.serviceImpl;

import javaWeb.test.service.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * set存数据
     * @param key: 键
     * @param value: 值
     * @return boolean
     **/
    @Override
    public boolean set(final String key, final String value) {
        return Boolean.TRUE.equals(redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(Objects.requireNonNull(serializer.serialize(key)), Objects.requireNonNull(serializer.serialize(value)));
                return true;
            }
        }));
    }

    /**
     * get获取数据
     * @param key: 键
     * @return String key
     **/
    @Override
    public String get(final String key) {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = connection.get(Objects.requireNonNull(serializer.serialize(key)));
            return serializer.deserialize(value);
        });
    }

    /**
     * expire 设置过期时间
     * @param key: 键
     * @param expire: 有效时间
     * @return boolean
     **/
    @Override
    public boolean expire(final String key, long expire) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, expire, TimeUnit.SECONDS));
    }

    /**
     * remove删除数据
     * @param key: 键
     * @return boolean
     **/
    @Override
    public boolean remove(final String key) {
        return Boolean.TRUE.equals(redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(key.getBytes());
                return true;
            }
        }));
    }
}
