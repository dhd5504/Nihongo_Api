package com.app.nihongo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String gender;
    private String activeNumber;
    private boolean isActived;
}
