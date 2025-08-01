package com.bookforest.project_bookforest_intj.user.vo;

import lombok.Data;

@Data
public class UserVO {

    private Long userId;

    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String address;

    public UserVO orElseThrow(Object o) {
        return null;
    }
}
