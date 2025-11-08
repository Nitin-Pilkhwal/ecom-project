package com.demo.project.nitin.ecommerce.constant;

public final class MessageKeys {

    public static final String PRIMARY_IMAGE_REQUIRED = "{error.primary.image.required}";
    public static final String VALUES_NOT_BLANK = "{error.values.not.blank}";

    private MessageKeys() {}

    // Common validation messages
    public static final String INVALID_FIRST_NAME = "{firstName.invalid}";
    public static final String INVALID_LAST_NAME = "{lastName.invalid}";
    public static final String INVALID_MIDDLE_NAME = "{middleName.invalid}";
    public static final String INVALID_COMPANY_NAME = "{companyName.invalid}";
    public static final String COMPANY_CONTACT_REQUIRED = "{companyContact.required}";
    public static final String ADDRESS_REQUIRED = "{address.required}";
    public static final String EMAIL_INVALID = "{email.invalid}";
    public static final String EMAIL_REQUIRED = "{email.required}";
    public static final String CONTACT_INVALID = "{contact.invalid}";
    public static final String PASSWORD_REQUIRED = "{password.required}";
    public static final String PASSWORD_CONFIRM_REQUIRED = "{password.confirm.required}";
    public static final String CONFIRM_PASSWORD_PATTERN = "{confirm.password.pattern}";
    public static final String OLD_PASSWORD_PATTERN = "{old.password.pattern}";
    public static final String OLD_PASSWORD_REQUIRED = "{old.password.required}";
    public static final String PASSWORD_PATTERN = "{password.pattern}";
    public static final String FIRSTNAME_REQUIRED = "{firstName.required}";
    public static final String LASTNAME_REQUIRED = "{lastName.required}";
    public static final String USERNAME_REQUIRED = "{username.required}";

    // Address specific validation messages
    public static final String CITY_REQUIRED = "{city.required}";
    public static final String STATE_REQUIRED = "{state.required}";
    public static final String COUNTRY_REQUIRED = "{country.required}";
    public static final String ADDRESS_LINE_REQUIRED = "{addressLine.required}";
    public static final String ZIPCODE_REQUIRED = "{zipcode.required}";
    public static final String ZIPCODE_INVALID = "{zipcode.invalid}";
    public static final String LABEL_REQUIRED = "{label.required}";

    public static final String CITY_INVALID = "{city.invalid}";
    public static final String STATE_INVALID = "{state.invalid}";
    public static final String COUNTRY_INVALID = "{country.invalid}";
    public static final String LABEL_INVALID = "{label.invalid}";

    // Seller and customer specific validation messages
    public static final String GST_INVALID = "{gst.invalid}";
    public static final String COMPANY_NAME_REQUIRED = "{company.name.required}";
    public static final String COMPANY_CONTACT_INVALID = "{companyContact.invalid}";
    public static final String COMPANY_ADDRESS_REQUIRED = "{companyAddress.required}";
    public static final String CONTACT_REQUIRED = "{contact.required}";

    // Category Validation messages
    public static final String NAME_REQUIRED = "{name.required}";
    public static final String INVALID_NAME = "{invalid.metadata.field.name}";
    public static final String CATEGORY_NAME_REQUIRED = "{category.name.required}";
    public static final String CATEGORY_NAME_INVALID = "{category.name.invalid}";
    public static final String ID_REQUIRED = "{id.required}";
    public static final String FIELDS_REQUIRED = "{error.metadata.field.required}";
    public static final String FIELD_ID_REQUIRED = "{error.field.id.required}";
    public static final String VALUES_REQUIRED = "{error.values.required}";
    public static final String METADATA_REQUIRED = "{error.metadata.required}";

    // Product Validation Messages
    public static final String DESCRIPTION_REQUIRED = "{description.required}";
    public static final String CATEGORY_ID_REQUIRED = "{category.id.required}";
    public static final String BRAND_REQUIRED = "{brand.required}";
    public static final String BRAND_INVALID = "{brand.invalid}";
    public static final String DESCRIPTION_INVALID = "{description.invalid}";
    public static final String NAME_INVALID = "{name.invalid}";
    public static final String VARIATION_MIN_QUANTITY = "{error.variation.min.quantity}";
    public static final String VARIATION_MAX_PRICE = "{error.variation.max.price}";
    public static final String VARIATION_MIN_PRICE = "{error.variation.min.price}";

    // Product related error and success messages
    public static final String PRODUCT_NOT_FOUND = "error.product.not.found";
    public static final String PRODUCT_ALREADY_EXISTS = "error.product.already.exists";
    public static final String PRODUCT_ALREADY_ACTIVE = "error.product.already.active";
    public static final String PRODUCT_ALREADY_INACTIVE = "error.product.already.inactive";
    public static final String PRODUCT_CREATED = "success.product.created";
    public static final String PRODUCT_FETCHED = "success.product.fetched";
    public static final String PRODUCT_DELETED = "success.product.deleted";
    public static final String PRODUCT_ACTIVATED = "success.product.activate";
    public static final String PRODUCT_DEACTIVATED = "success.product.deactivated";
    public static final String PRODUCT_NOT_ACTIVE = "error.product.not.active";
    public static final String PRODUCT_VARIATION_CREATED = "success.product.variation.added";
    public static final String METADATA_FIELD_VALUE_NOT_FOUND = "error.metadata.field.value.not.found";
    public static final String PRODUCT_VARIATION_ALREADY_EXISTS = "error.product.variation.already.exists";
    public static final String PRODUCT_VARIATION_NOT_FOUND = "error.product.variation.not.found";
    public static final String PRODUCT_VARIATION_FETCHED = "success.product.variation.fetched";
    public static final String PRODUCT_UPDATED = "success.product.updated";
    public static final String PRODUCT_VARIATION_UPDATED = "success.product.variation.updated";

    // Product validation messages
    public static final String QUANTITY_AVAILABLE_INVALID = "{quantityAvailable.invalid}";
    public static final String PRICE_INVALID = "{price.invalid}";

    // Category related messages
    public static final String METADATA_FIELD_NAME_EXISTS = "metadata.field.name.exists";
    public static final String METADATA_FIELD_ADDED = "metadata.field.added";
    public static final String METADATA_FIELD_ADDED_WITH_VALUES = "metadata.field.and.values.added";
    public static final String METADATA_FIELD_FETCHED = "metadata.field.fetched";
    public static final String CATEGORY_NOT_FOUND = "category.not.found";
    public static final String CATEGORY_ADDED = "category.added";
    public static final String CATEGORY_EXISTS_ON_SAME_LEVEL = "category.exists.same.level";
    public static final String CATEGORY_EXISTS_ON_SAME_HIERARCHY = "category.exists.same.hierarchy";
    public static final String PRODUCT_EXISTS_FOR_PARENT_CATEGORY = "product.exists.for.parent.category";
    public static final String CATEGORY_UPDATED = "category.updated";
    public static final String CATEGORY_FETCHED = "category.fetched";
    public static final String METADATA_FIELD_NOT_FOUND = "category.metadata.field.not.found";
    public static final String NO_METADATA_FIELD_ADDED = "no.metadata.field.added";
    public static final String METADATA_FIELD_UPDATED_WITH_VALUES = "metadata.field.and.values.updated";
    public static final String NOT_LEAF_CATEGORY = "error.not.leaf.category";
    public static final String CATEGORY_EXISTS_ON_SUB_CATEGORIES = "error.category.exists.on.subcategories";

    // Token specific messages
    public static final String INVALID_TOKEN = "error.invalid.token";
    public static final String VALIDATION_FAILED = "error.validation.failed";
    public static final String TOKEN_ALREADY_EXPIRED = "error.token.expired";
    public static final String TOKEN_MALFORMED = "error.token.malformed";
    public static final String TOKEN_SIGNATURE_INVALID = "error.token.signature.invalid";
    public static final String ACCESS_TOKEN_GENERATED = "success.access.token.generated";
    public static final String ACTIVATION_TOKEN_EXPIRED_RESEND_EMAIL = "activation.token.expired.resend.email";

    // User related error messages
    public static final String CANNOT_DEACTIVATE_ADMIN = "error.cannot.deactivate.admin";
    public static final String USER_NOT_FOUND = "error.user.not.found";
    public static final String COMPANY_UNIQUE = "error.company.unique";
    public static final String PASSWORD_MISMATCH = "error.password.mismatch";
    public static final String EMAIL_EXISTS = "error.email.exists";
    public static final String USER_ALREADY_ACTIVE = "error.user.already.active";
    public static final String USER_ALREADY_DEACTIVATED = "error.user.already.deactivated";
    public static final String USER_ACCOUNT_LOCKED = "error.user.account.locked";
    public static final String USER_ACCOUNT_EXPIRED = "error.user.account.expired";
    public static final String USER_CREDENTIALS_EXPIRED = "error.user.credentials.expired";
    public static final String USER_ACCOUNT_NOT_ACTIVE = "error.user.account.not.active";
    public static final String INCORRECT_PASSWORD = "error.incorrect.password";
    public static final String ADDRESS_NOT_FOUND = "error.address.not.found";
    public static final String COMPANY_NAME_EXISTS = "error.company.name.exists";
    public static final String GST_EXISTS = "error.gst.exists";
    public static final String AT_LEAST_ONE_ADDRESS_REQUIRED = "error.at.least.one.address.required";
    public static final String OLD_PASSWORD_MISMATCH = "error.old.password.mismatch";
    public static final String SAME_PASSWORD = "error.same.password";
    public static final String INVALID_ID = "{error.invalid.id}";
    public static final String SELLER_NOT_FOUND = "error.seller.not.found";
    public static final String INVALID_UUID = "error.invalid.id";
    public static final String COUNTRY_CODE_INVALID = "{error.country.code.invalid}";

    // User related success messages
    public static final String USER_REGISTERED = "success.user.registered";
    public static final String USER_ACTIVATED = "success.user.activated";
    public static final String USER_LOGGED_IN = "success.user.logged.in";
    public static final String ACTIVATION_LINK_RESENT = "success.activation.link.resent";
    public static final String USER_LOGGED_OUT = "success.user.logged.out";
    public static final String PASSWORD_RESET_LINK_SENT = "success.password.reset.link.sent";
    public static final String PASSWORD_RESET = "success.password.reset";
    public static final String PASSWORD_CHANGED = "success.password.changed";
    public static final String USER_DEACTIVATED = "success.user.deactivated";
    public static final String ADDRESS_UPDATED = "success.address.updated";
    public static final String ADDRESS_ADDED = "success.address.added";
    public static final String ADDRESS_DELETED = "success.address.deleted";
    public static final String ADDRESSES_FETCHED = "success.addresses.fetched";
    public static final String CUSTOMER_FETCHED = "success.customers.fetch";
    public static final String SELLER_FETCHED = "success.sellers.fetch";
    public static final String CUSTOMER_PROFILE_VIEW = "success.customer.profile.view";
    public static final String SELLER_PROFILE_VIEW = "success.seller.profile.view";
    public static final String CUSTOMER_PROFILE_UPDATED = "success.customer.profile.updated";
    public static final String SELLER_PROFILE_UPDATED = "success.seller.profile.updated";
    public static final String CUSTOMER_NOT_FOUND = "error.customer.not.found";

    // Image related messages
    public static final String IMAGE_UPLOADED = "success.image.uploaded";
    public static final String ERROR_IMAGE_SAVE = "error.image.save";
    public static final String ERROR_IMAGE_NOT_FOUND = "error.image.not.found";
    public static final String ERROR_FILE_EMPTY = "error.file.empty";
    public static final String ERROR_FILE_NAME_NULL = "error.file.name.null";
    public static final String ERROR_EXTENSION_UNSUPPORTED = "error.extension.unsupported";
    public static final String FILE_SIZE_EXCEEDED = "error.file.size.exceeded";
    public static final String FILE_NOT_DELETED = "error.file.not.deleted";
    public static final String IMAGE_DELETED = "success.image.deleted";

    // HTTP related error messages
    public static final String JSON_MALFORMED = "error.json.malformed";
    public static final String HTTP_METHOD_NOT_SUPPORTED = "error.http.method.not.supported";
    public static final String ACCESS_DENIED = "error.access.denied";
    public static final String AUTH_REQUIRED = "error.auth.required";
    public static final String MISSING_REQUIRED_REQUEST_PARAM = "error.missing.required.request.param";
    public static final String NOT_FOUND = "error.not.found";

    // Notification
    public static final String TEMPLATE_CREATED = "success.template.created";
    public static final String TEMPLATE_FETCHED = "success.template.fetched";
    public static final String TEMPLATE_NOT_FOUND = "error.template.not.found";

    // Notification template validation messages
    public static final String TEMPLATE_TYPE_REQUIRED = "{templateType.required}";
    public static final String TEMPLATE_HTML_BLANK = "{templateHtml.blank}";
}
