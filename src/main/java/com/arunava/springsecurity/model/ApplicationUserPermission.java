package com.arunava.springsecurity.model;

public enum ApplicationUserPermission {
    STUDENT_READ ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");


    // one single has much permission so that we can add dependency in pom.xml file com.google.guava

     private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
