package com.bookforest.project_bookforest_intj.user.vo;

import lombok.Data;

import java.util.List;

@Data
public class RegisterReqDto {
    private String aeId;
    private String ccgU001;
    private String userNm;
    private String userId;
    private String address;
    private String email;
    private String enabled;
    private String password;
    private String phone;
    private String username;

    //RegisterReqDto : Auth = 1 : N
    private List<AuthVO> authVOList;
}
