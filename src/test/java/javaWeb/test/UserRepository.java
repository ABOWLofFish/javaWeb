package javaWeb.test;

import org.springframework.stereotype.Repository;
import springfox.documentation.annotations.Cacheable;

@Repository
public class UserRepository {
    @Cacheable(value = "user")
    public User getUserByName(String username) {
        User user = new User();
        user.setName(username);
        user.setAge(30);
        user.setPassword("root");
        System.out.println("javaWeb.test.User info from database");
        return user;
    }

}
