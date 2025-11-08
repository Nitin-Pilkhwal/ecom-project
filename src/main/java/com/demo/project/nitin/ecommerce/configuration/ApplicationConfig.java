package com.demo.project.nitin.ecommerce.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application")
@Configuration
@Getter
@Setter
public class ApplicationConfig {

    private AdminProperties adminProperties = new AdminProperties();
    private JwtProperties jwtProperties = new JwtProperties();
    private ImageProperties imageProperties = new ImageProperties();

    @Getter
    @Setter
    public static class AdminProperties{
        private String firstName;
        private String lastName;
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class JwtProperties {
        private String secretKey;
        private long accessTokenExpirationTime;
        private long activationTokenExpirationTime;
        private long passwordResetTokenExpirationTime;
        private long refreshTokenExpirationTime;
        private String issuer;
    }

    @Getter
    @Setter
    public static class ImageProperties {
        private String baseDir;
        private String users;
        private String products;
    }
}
