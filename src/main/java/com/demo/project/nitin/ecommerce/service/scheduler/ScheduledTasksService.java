package com.demo.project.nitin.ecommerce.service.scheduler;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.repository.ProductVariationRepo;
import com.demo.project.nitin.ecommerce.repository.TokenRepo;
import com.demo.project.nitin.ecommerce.repository.UserRepo;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledTasksService {

    private final TokenRepo tokenRepo;
    private final UserRepo userRepo;
    private final NotificationHelperService notificationHelperService;
    private final ProductVariationRepo productVariationRepo;

    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteExpiredTokens() {
        log.info("Deleting expired refresh tokens...");
        int deletedCount = tokenRepo.deleteByExpiryDateBeforeAndType_Refresh(LocalDateTime.now(), TokenType.REFRESH);
        log.info("Deleted {} expired refresh tokens", deletedCount);
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void expireOldUsersAndSendMail() {
        log.info("Checking users with expired passwords...");
        LocalDate thresholdDate = LocalDate.now().minusDays(Constants.Security.PASSWORD_EXPIRY_DAYS);

        List<User> usersToExpire = userRepo.findAllByPasswordUpdateDateIsNotNullAndPasswordUpdateDateBeforeAndIsExpiredFalse(thresholdDate);

        for (User user : usersToExpire) {
            user.setExpired(true);
            userRepo.save(user);
            notificationHelperService.sendPasswordExpiryMail(user);
            log.info("User {} marked expired and email sent", user.getEmail());
        }
    }

    @Scheduled(cron = "0 0 6 * * MON")
    public void sendOutOfStockNotification() {
        List<ProductVariation> outOfStockVariations = productVariationRepo.findOutOfStockVariations();
        Map<Seller, List<ProductVariation>> sellerVariationMap = outOfStockVariations.stream()
                .collect(Collectors.groupingBy(pv -> pv.getProduct().getSeller()));
        sellerVariationMap.forEach((seller, variations) -> {
            String message = String.format(
                    "The following product variations are out of stock: %s",
                    variations.stream()
                            .map(v -> v.getProduct().getName() + " (Variation ID: " + v.getId() + ")      ")
                            .collect(Collectors.joining(",\n"))
            );
            notificationHelperService.sendOutOfStockNotification(seller,message);
        });
    }
}
