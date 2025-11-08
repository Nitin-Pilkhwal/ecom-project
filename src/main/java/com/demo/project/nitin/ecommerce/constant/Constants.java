package com.demo.project.nitin.ecommerce.constant;

public final class Constants {

    public static final String CONSTANT_CLASS = "Constant class";

    private Constants(){
        throw new IllegalStateException(CONSTANT_CLASS);
    }

    public static class Security {
        private Security(){
            throw new IllegalStateException(CONSTANT_CLASS);
        }

        public static final String AUTHORIZATION_HEADER = "Authorization";
        public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
        public static final int INVALID_ATTEMPTS = 3;
        public static final long PASSWORD_EXPIRY_DAYS = 30;
        public static final String ROLES = "roles";
        public static final String DEFAULT_AUDITOR = "SYSTEM";
    }

    public static class API{

        private API(){
            throw new IllegalStateException(CONSTANT_CLASS);
        }

        public static final String ACTIVATION_PATH = "/auth/activate/customer";
        public static final String PASSWORD_RESET_PATH = "/auth/reset-password";
        private static final String BASE_URL = "http://localhost:8090";
        public static final String ACTIVATION_URL = BASE_URL +ACTIVATION_PATH+"?token=";
        public static final String PASSWORD_RESET_URL = BASE_URL + PASSWORD_RESET_PATH + "?token=";
        public static final String INITIATE_FORGOT_PASSWORD = BASE_URL + "/auth/initiate/forgot-password";

        public static final int DEFAULT_PAGE_SIZE = 10;
        public static final int DEFAULT_PAGE_OFFSET = 0;
        public static final String DEFAULT_SORT_FIELD = "createdAt";
    }

    public static class AddressFields{
        private AddressFields(){
            throw new IllegalStateException(CONSTANT_CLASS);
        }

        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String COUNTRY = "country";
        public static final String ADDRESS_LINE = "addressLine";
        public static final String ZIPCODE = "zipcode";
        public static final String LABEL = "label";
    }

    public static class UserFields{
        private UserFields(){
            throw new IllegalStateException(CONSTANT_CLASS);
        }

        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String MIDDLE_NAME = "middleName";
        public static final String CONTACT = "country";
        public static final String COMPANY_NAME = "companyName";
        public static final String COMPANY_CONTACT = "countryCode";
        public static final String GST = "GST";
    }

    public static class Regex{
        private Regex(){throw new IllegalStateException(CONSTANT_CLASS);}

        public static final String ADDRESS_NAME = "^[A-Za-z]+(?: [A-Za-z]+)*$";
        public static final String ZIPCODE = "\\d{5,6}";
        public static final String GST ="^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9]{1}$";
        public static final String ALL_LANG_NAME ="^[\\p{L}]{2,50}$";
        public static final String COMPANY_NAME = "^(?=.{1,100}$)[A-Za-z0-9](?:[A-Za-z0-9 .,&'_-]*[A-Za-z0-9.])$";
        public static final String COUNTRY_CODE = "^\\+?[0-9]{1,2}$";
        public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        public static final String EMAIL = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";
        public static final String GENERIC_NAME = "^(?=.{1,60}$)[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$";
        public static final String ID = "^[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}$";
    }
}
