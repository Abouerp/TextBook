package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.RoleRepository;
import com.abouerp.textbook.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    public Optional<Role> findById(Integer id){
        return roleRepository.findById(id);
    }

    public List<Role> findByIdIn(List<Integer> ids){
        return roleRepository.findByIdIn(ids);
    }
}
