package com.bookforest.project_bookforest_intj.user.service.impl;

import com.bookforest.project_bookforest_intj.mapper.UsersMapper;
import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    //퍼시스턴스 레이어
    @Autowired
    UsersMapper usersMapper;

    //비밀번호 암호화
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //USERS 테이블에 insert(회원가입)
    @Override
    public int registerUser(RegisterReqDto registerReqDto){
         /*
        RegisterReqDto(aeId=null, ccgU001=null, userNm=3, userId=null
        , address=7, email=4@5, enabled=null, password=2, phone=6
        , username=1, authVOList=null)
         */
        //비밀번호 암호화
        String password = registerReqDto.getPassword();
        password = this.bCryptPasswordEncoder.encode(password);
        registerReqDto.setPassword(password);

        int result = this.usersMapper.registerUser(registerReqDto);

        return result;
    }

}
