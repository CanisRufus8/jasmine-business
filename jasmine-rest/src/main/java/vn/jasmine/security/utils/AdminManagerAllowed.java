package vn.jasmine.security.utils;

import jakarta.annotation.security.RolesAllowed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static vn.jasmine.security.constants.RoleConst.ADMIN;
import static vn.jasmine.security.constants.RoleConst.MANAGER;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RolesAllowed({ADMIN, MANAGER})
public @interface AdminManagerAllowed {
}
