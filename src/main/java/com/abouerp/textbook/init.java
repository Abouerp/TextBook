package com.abouerp.textbook;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Abouerp
 */
@Component
public class init implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;

    public init(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Administrator> administratorList = administratorRepository.findAll();
        if (administratorList==null||administratorList.size()==0){
            Administrator administrator = new Administrator().setUsername("admin")
                    .setAccountNonExpired(false)
                    .setAccountNonLocked(false)
                    .setCredentialsNonExpired(false)
                    .setEnabled(false)
                    .setCollege("test")
                    .setEmail("1057240821@qq.com")
                    .setJobNumber("001")
                    .setPassword("{noop}admin")
                    .setSex("1");
            administratorRepository.save(administrator);
        }
    }
}
