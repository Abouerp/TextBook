package com.abouerp.textbook;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.dao.RoleRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.Authority;
import com.abouerp.textbook.domain.Role;

import com.abouerp.textbook.exception.RoleNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.*;
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
        Role superRole = roleRepository.findFirstByName("超级管理员").orElseThrow(RoleNotFoundException::new);
        superRole.setAuthorities(Arrays.stream(Authority.values()).collect(Collectors.toSet()));
        roleRepository.save(superRole);
        long count = administratorRepository.count();
        if (count == 0) {
            Set<Role> role = objectMapper.readValue(read("data/role.json"), new TypeReference<List<Role>>() {
            }).stream().map(it -> {
                roleRepository.findFirstByName(it.getName()).map(Role::getId).ifPresent(it::setId);
                if (it.getAuthorities() == null) {
                    it.setAuthorities(Arrays.stream(Authority.values()).collect(Collectors.toSet()));
                }
                return roleRepository.save(it);
            }).collect(Collectors.toSet());

            objectMapper.readValue(read("data/administrator.json"), new TypeReference<List<Administrator>>() {
            }).forEach(it -> {
                administratorRepository.findFirstByUsername(it.getUsername()).map(Administrator::getId).ifPresent(it::setId);
                if (it.getUsername().equals("admin")) {
                    it.setRoles(role.stream().filter(its -> its.getName().equals("超级管理员")).collect(Collectors.toSet()));
                } else {
                    it.setRoles(role.stream().filter(its -> its.getName().equals("教师")).collect(Collectors.toSet()));
                }
                administratorRepository.save(it);
            });
        }
    }
}
