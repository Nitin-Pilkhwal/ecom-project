package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.dto.filter.CustomerFilter;
import com.demo.project.nitin.ecommerce.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    private CustomerSpecification() {}

    public static Specification<Customer> build(CustomerFilter filter) {
        return Specification.allOf(
                hasEmail(filter.getEmail()),
                hasFirstName(filter.getFirstName()),
                hasLastName(filter.getLastName()),
                hasMiddleName(filter.getMiddleName()),
                hasCountryContact(filter.getCountryCode()),
                isActive(filter.getIsActive())
        );
    }

    private static Specification<Customer> hasEmail(String email) {
        return (root, query, cb) ->
                email == null ? cb.conjunction() :
                        cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    private static Specification<Customer> hasFirstName(String firstName) {
        return (root, query, cb) ->
                firstName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    private static Specification<Customer> hasLastName(String lastName) {
        return (root, query, cb) ->
                lastName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }

    private static Specification<Customer> hasMiddleName(String middleName) {
        return (root, query, cb) ->
                middleName == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("middleName")), "%" + middleName.toLowerCase() + "%");
    }

    private static Specification<Customer> hasCountryContact(String countryCode) {
        return (root, query, cb) ->
                countryCode == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("contact")), countryCode + "%");
    }

    private static Specification<Customer> isActive(Boolean isActive) {
        return (root, query, cb) ->
                isActive == null ? cb.conjunction() : cb.equal(root.get("isActive"), isActive);
    }
}
