package com.abouerp.textbook.security;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.exception.UnauthorizedException;
import com.abouerp.textbook.mapper.AdministratorMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    public UserDetailsServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findFirstByUsername(s).orElseThrow(null);
        if (administrator == null) {
            throw new UnauthorizedException();
        }
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
