package com.ecommerce.project.config;

import com.ecommerce.project.entity.Role;
import com.ecommerce.project.entity.RoleName;
import com.ecommerce.project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            roleRepository.save(Role.builder()
                    .name(RoleName.ADMIN)
                    .build());
        }

        if (roleRepository.findByName(RoleName.CUSTOMER).isEmpty()) {
            roleRepository.save(Role.builder()
                    .name(RoleName.CUSTOMER)
                    .build());
        }
    }
}
