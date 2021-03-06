package com.abouerp.textbook.config;

import com.abouerp.textbook.utils.UserUtils;
import org.springframework.data.domain.AuditorAware;


import java.util.Optional;

/**
 * @author Abouerp
 */
public class UserAuditorConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        return UserUtils.getCurrentAuditor();
    }
}
