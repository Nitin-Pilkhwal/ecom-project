package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.dto.filter.SellerFilter;
import com.demo.project.nitin.ecommerce.entity.Seller;
import org.springframework.data.jpa.domain.Specification;

public class SellerSpecification {

    private SellerSpecification() {}

    public static Specification<Seller> build(SellerFilter filter) {
        return Specification.allOf(
                hasEmail(filter.getEmail()),
                hasGst(filter.getGst()),
                hasCompanyName(filter.getCompanyName()),
                hasCompanyContact(filter.getCountryCode()),
                hasFirstName(filter.getFirstName()),
                hasLastName(filter.getLastName()),
                hasMiddleName(filter.getMiddleName()),
                isActive(filter.getIsActive())
        );
    }

    private static Specification<Seller> hasEmail(String email) {
        return (root, query, cb) ->
                email == null ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    private static Specification<Seller> hasGst(String gst) {
        return (root, query, cb) ->
                gst == null ? cb.conjunction() : cb.equal(root.get("gst"), gst);
    }

    private static Specification<Seller> hasCompanyName(String companyName) {
        return (root, query, cb) ->
                companyName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("companyName")), "%" + companyName.toLowerCase() + "%");
    }

    private static Specification<Seller> hasCompanyContact(String countryCode) {
        return (root, query, cb) ->
                countryCode == null ? cb.conjunction() :
            cb.like(cb.lower(root.get("companyContact")), countryCode + "%");
    }

    private static Specification<Seller> hasFirstName(String firstName) {
        return (root, query, cb) ->
                firstName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    private static Specification<Seller> hasLastName(String lastName) {
        return (root, query, cb) ->
                lastName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    private static Specification<Seller> hasMiddleName(String middleName) {
        return (root, query, cb) ->
                middleName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("middleName")), "%" + middleName.toLowerCase() + "%");
    }

    private static Specification<Seller> isActive(Boolean isActive) {
        return (root, query, cb) ->
                isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }
}
