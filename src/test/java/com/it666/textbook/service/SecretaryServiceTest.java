package com.it666.textbook.service;

import com.it666.textbook.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SecretaryServiceTest {

    @Autowired
    private SecretaryService secretaryService;

    @Test
    public void savaTeacher() {
        User u = new User();
        u.setUserType(1);
        u.setUserName("冷鱼不冷");
        u.setUserPassword("cf123456");
        secretaryService.saveTeacher(u);
    }

    @Test
    public void edit() {
        User u = new User();
        u.setId(7);
        u.setUserType(2);
        u.setUserPassword("hahahha");
        u.setUserName("woliuliuliu");
        secretaryService.edit(u);
    }
}
