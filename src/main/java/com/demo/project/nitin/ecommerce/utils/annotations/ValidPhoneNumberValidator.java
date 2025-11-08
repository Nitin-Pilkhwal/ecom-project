package com.demo.project.nitin.ecommerce.utils.annotations;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidPhoneNumberValidator implements ConstraintValidator<ValidatePhoneNumber, String> {

    private static final String[] SUPPORTED_REGIONS = {"IN","FR","DE","NL"};

    @Override
    public boolean isValid(String number, ConstraintValidatorContext constraintValidatorContext) {
        if (number == null) return true;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        for(String supportedRegion : SUPPORTED_REGIONS) {
            try {
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(number, supportedRegion);
                if (phoneNumberUtil.isValidNumber(phoneNumber)) {
                    String normalized = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
                    if (number.equals(normalized)) {
                        return true;
                    }
                }
            } catch (NumberParseException e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }
}
