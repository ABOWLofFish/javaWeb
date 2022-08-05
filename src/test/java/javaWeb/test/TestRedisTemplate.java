package javaWeb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisTemplate {
    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Test
    public void test() {

        // 保存字符串
        redisTemplate.opsForValue().set("hello:redis", "hello spring boot");
        System.out.println("hello redis: "+redisTemplate.opsForValue().get("hello:redis"));
    }
}
