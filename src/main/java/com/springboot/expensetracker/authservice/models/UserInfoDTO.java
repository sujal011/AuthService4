package com.springboot.expensetracker.authservice.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.springboot.expensetracker.authservice.entities.UserInfo;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

}
