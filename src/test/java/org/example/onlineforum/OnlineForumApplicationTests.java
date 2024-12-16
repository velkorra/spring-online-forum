package org.example.onlineforum;

import org.example.onlineforum.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@SpringBootTest
class OnlineForumApplicationTests {
//    @Autowired
//    private UserRepository userRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void hashPasswords(){}

    @Test
    void hashPassword(){
        var pw = new BCryptPasswordEncoder();
        System.out.println(pw.encode("reimu"));
    }
}
