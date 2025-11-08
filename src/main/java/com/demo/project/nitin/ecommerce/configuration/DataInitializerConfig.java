package com.demo.project.nitin.ecommerce.configuration;

import com.demo.project.nitin.ecommerce.constant.enums.Authority;
import com.demo.project.nitin.ecommerce.entity.*;
import com.demo.project.nitin.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializerConfig {

    private final ApplicationConfig applicationConfig;

    @Bean
    public CommandLineRunner initData(RoleRepo roleRepo,
                                  UserRepo userRepo,
                                  CategoryMetadataFieldRepo categoryMetadataFieldRepo,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            log.debug("Init data");
            if(roleRepo.count() == 0) {
                log.debug("No roles found, initializing default roles");
                for(Authority authority : Authority.values()) {
                    Role role = new Role();
                    role.setAuthority(authority);
                    role.setCreatedBy("System");
                    roleRepo.save(role);
                }
            }

            if(userRepo.count() == 0) {
                log.debug("No users found, initializing default admin user");
                User admin = new User();
                admin.setFirstName(applicationConfig.getAdminProperties().getFirstName());
                admin.setLastName(applicationConfig.getAdminProperties().getLastName());
                admin.setEmail(applicationConfig.getAdminProperties().getEmail());
                admin.setPassword(passwordEncoder.encode(applicationConfig.getAdminProperties().getPassword()));
                admin.setActive(true);
                admin.setCreatedBy("System");
                admin.setRoles(List.of(roleRepo.findByAuthority(Authority.ADMIN).get()));
                userRepo.save(admin);
            }

            if(categoryMetadataFieldRepo.count() == 0) {
                log.debug("No category metadata fields found, initializing default fields");
                String[] fields = {
                        "RAM", "Color", "Size", "ROM", "Battery", "Processor", "Display Size", "Material"
                };
                for(String fieldName : fields) {
                    CategoryMetadataField field = new CategoryMetadataField();
                    field.setName(fieldName);
                    field.setCreatedBy("System");
                    categoryMetadataFieldRepo.save(field);
                }
            }

        };
    }
}
