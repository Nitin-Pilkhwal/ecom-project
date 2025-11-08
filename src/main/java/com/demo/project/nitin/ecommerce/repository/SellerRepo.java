package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SellerRepo extends JpaRepository<Seller, UUID>, JpaSpecificationExecutor<Seller> {

    List<Seller> findByCompanyNameOrGst(String companyName, String gst);

    boolean existsByCompanyNameAndIdNot(String companyName, UUID id);
    boolean existsByGstAndIdNot(String gst, UUID id);

    Optional<Seller> findByEmail(String email);
}
