package com.shop.user.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read", "user:buy","user:create_organization"};  // user
    public static final String[] EMPLOYEE_AUTHORITIES = { "user:read", "user:buy","user:create_organization", // user
            "user:create_product"};                              // employee
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:buy","user:create_organization", // user
            "user:create_product",                             // employee
            "user:update_organization", "user:update_product", "user:update_user", "user:create_notification",
            "user:getAllUsers", "user:getAllOrganization"};                                                     // admin
}
