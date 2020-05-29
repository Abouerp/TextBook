package com.abouerp.textbook;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.dao.RoleRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.Authority;
import com.abouerp.textbook.domain.Role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@Component
public class init implements CommandLineRunner {

    private final AdministratorRepository administratorRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    public init(AdministratorRepository administratorRepository,
                RoleRepository roleRepository,
                ObjectMapper objectMapper) {
        this.administratorRepository = administratorRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
    }

    private static byte[] read(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }

    @Override
    public void run(String... args) throws Exception {
        Set<Role> role = objectMapper.readValue(read("data/role.json"), new TypeReference<List<Role>>() {
        }).stream().map(it -> {
            roleRepository.findFirstByName(it.getName()).map(Role::getId).ifPresent(it::setId);
            if (it.getAuthorities() == null) {
                it.setAuthorities(Arrays.stream(Authority.values()).collect(Collectors.toSet()));
            }
            return roleRepository.save(it);
        }).collect(Collectors.toSet());

        List<Administrator> administratorList = administratorRepository.findAll();
        if (administratorList == null || administratorList.size() == 0) {
            Administrator administrator = new Administrator().setUsername("admin")
                    .setAccountNonExpired(true)
                    .setAccountNonLocked(true)
                    .setCredentialsNonExpired(true)
                    .setEnabled(true)
                    .setCollege("计算机学院")
                    .setEmail("1057240821@qq.com")
                    .setJobNumber("001")
                    .setPassword("{noop}admin")
                    .setSex("1")
                    .setRoles(role);
            administratorRepository.save(administrator);
        }
    }
}
