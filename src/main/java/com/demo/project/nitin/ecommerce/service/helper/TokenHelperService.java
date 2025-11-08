package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.utils.JwtUtils;
import com.demo.project.nitin.ecommerce.configuration.ApplicationConfig;
import com.demo.project.nitin.ecommerce.constant.enums.JwtTokenType;
import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import com.demo.project.nitin.ecommerce.dto.TokenDTO;
import com.demo.project.nitin.ecommerce.entity.Token;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.TokenExpiredException;
import com.demo.project.nitin.ecommerce.repository.TokenRepo;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenHelperService {
    
    private final ApplicationConfig applicationConfig;
    private final TokenRepo tokenRepo;
    private final JwtUtils jwtUtils;

    public Token findByToken(String token,TokenType type) {
        return tokenRepo.findByTokenValueAndType(token,type)
                .orElseThrow(() -> new JwtException(MessageKeys.INVALID_TOKEN));
    }

    public TokenDTO createTokenPair(User user){
        Token refreshTokenEntity = createTokenEntity(user, TokenType.REFRESH);
        String refreshToken = jwtUtils.generateToken(user,
                toUUID(refreshTokenEntity.getTokenValue()), JwtTokenType.REFRESH);
        String accessToken = jwtUtils.generateToken(user,
                refreshTokenEntity.getId(),JwtTokenType.ACCESS);
        return new TokenDTO(accessToken,refreshToken);
    }

    public Token createTokenEntity(User user, TokenType type) {
        Token token = new Token();
        if(type != TokenType.REFRESH) {
            token = tokenRepo.findByUserAndType(user, type).orElse(token);
        }
        token.setUser(user);
        token.setType(type);
        token.setTokenValue(UUID.randomUUID().toString());
        LocalDateTime expiryTime = switch (type) {
            case ACTIVATION -> LocalDateTime.now().plusSeconds(
                    applicationConfig.getJwtProperties().getActivationTokenExpirationTime() / 1000
            );
            case REFRESH -> LocalDateTime.now().plusSeconds(
                    applicationConfig.getJwtProperties().getRefreshTokenExpirationTime() / 1000
            );
            case FORGOT_PASSWORD -> LocalDateTime.now().plusSeconds(
                    applicationConfig.getJwtProperties().getPasswordResetTokenExpirationTime() / 1000
            );
        };
        token.setExpiredAt(expiryTime);

        return tokenRepo.save(token);
    }

    public void verifyExpiration(Token entity) {
        if (entity.getExpiredAt().isBefore(LocalDateTime.now())) {
            tokenRepo.delete(entity);
            log.info("{} token: {} is expired and deleted from db",entity.getType(), entity.getTokenValue());
            throw new TokenExpiredException(MessageKeys.TOKEN_ALREADY_EXPIRED);
        }
    }

    public void deleteAllTokensByUser(User user) {
        tokenRepo.deleteAllByUser(user);
    }

    public void deleteById(String id) {
        tokenRepo.deleteById(toUUID(id));
    }
}
