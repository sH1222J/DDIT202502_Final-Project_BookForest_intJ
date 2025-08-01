package com.bookforest.project_bookforest_intj.user.service.impl;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.repository.UserRepository;
import com.bookforest.project_bookforest_intj.user.service.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterReqDto registerReqDto = userRepository.findByUsername(username);
        /*
        RegisterReqDto(aeId=testuser01, ccgU001=U01, userNm=테스트유저, userId=1
        , address=서울시 강남구, email=testuser01@test.com, enabled=1
        , password=$2a$10$//UpyL0A.3ss2a42N6wLd.d2xmyTfILfLw9n29sXn8i8vQ2y2mAnq
        , phone=010-1234-5678, username=gildong)
         */
        log.info("loadUserByUsername->registerReqDto : " + registerReqDto);

        //MVC에서는 Controller로 리턴하지 않고, CustomUser로 리턴함
        //CustomUser : 사용자 정의 유저 정보. extends User를 상속받고 있음
        //2) 스프링 시큐리티의 User 객체의 정보로 넣어줌 => 프링이가 이제부터 해당 유저를 관리
        //User : 스프링 시큐리에서 제공해주는 사용자 정보 클래스
		/*
		 tblUsersVO(우리) -> user(시큐리티)
		 -----------------
		 email        -> username
		 password        -> password
		 enabled       -> enabled
		 auth들                -> authorities
		 */
        return registerReqDto == null?null:new CustomUser(registerReqDto);

        //return new org.springframework.security.core.userdetails.User(registerReqDto.getAeId(), registerReqDto.getPassword(), new ArrayList<>());
    }
}
