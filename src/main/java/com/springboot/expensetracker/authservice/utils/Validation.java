package com.springboot.expensetracker.authservice.utils;

import java.util.regex.Pattern;


public class Validation {


    public static Boolean validateEmail(String email){
        String regexPattern = "^[A-Za-z][A-Za-z0-9+_.-]*[A-Za-z0-9]@[A-Za-z0-9.-]+$";//        String regexPattern = "^[A-Za-z][A-Za-z0-9+_.-]*[A-Za-z0-9]@[A-Za-z0-9-]+\\\\.[A-Za-z]{2,}$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static Boolean validatePhoneNumber(String phoneNumber){
        String regexPattern = "^\\d{10}$";
        return Pattern.compile(regexPattern)
                .matcher(phoneNumber)
                .matches();
    }

    public static void main(String[] args) {
        String[] emails = {"example@gmail.com", "user.name@domain.co.in", "user@domain", "user@domain..com", "user|name@domain.com", "user'name@domain.com", "user\"name@domain.com", ".user@domain.com", "user.@domain.com", "user@domain.com"};

        for (String email : emails) {
            if (validateEmail(email)) {
                System.out.println(email + " - valid");
            } else {
                System.out.println(email + " - Invalid");
            }
        }
    }


}
