package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationMessageRepo extends JpaRepository<NotificationMessage, UUID> {
}
