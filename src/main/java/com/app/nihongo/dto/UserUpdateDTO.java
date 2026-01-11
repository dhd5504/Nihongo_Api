package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String password;
    private String avatar;


}
