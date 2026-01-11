package com.app.nihongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDTO {
    private String name;
    private Integer userXP;
    private String phone;
    private String email;
    private String avatar;


}
