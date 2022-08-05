package javaWeb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class TestRedis2 {
    UserRepository userRepository = new UserRepository();
    @Test
    public void testGetUserByName() {
        User user = userRepository.getUserByName("hhh");
        System.out.println("name: "+user.getName()+", age: "+user.getAge());

        user = userRepository.getUserByName("hhh");
        System.out.println("name: "+user.getName()+", age: "+user.getAge());
    }
}
