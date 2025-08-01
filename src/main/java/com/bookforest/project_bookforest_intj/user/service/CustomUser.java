package com.bookforest.project_bookforest_intj.user.service;

import java.util.Collection;
import java.util.stream.Collectors;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


//사용자가 유저를 정의함
//tblUsersVO(select결과)정보를 User(스프링 시큐리티에서 정의된 유저) 객체 정보에 연계하여 넣어줌
//CustomUser의 객체 = principal
public class CustomUser extends User {

	/* User 클래스의 프로퍼티
	private String password;
	private final String username;
	private final Set<GrantedAuthority> authorities;
	...
	private final boolean enabled;	
	 */
	private RegisterReqDto registerReqDto;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	//return tblUsersVO == null?null:new CustomUser(tblUsersVO);

	/*
	RegisterReqDto(aeId=testuser01, ccgU001=U01, userNm=테스트유저, userId=1
	, address=서울시 강남구, email=testuser01@test.com, enabled=1
	, password=$2a$10$//UpyL0A.3ss2a42N6wLd.d2xmyTfILfLw9n29sXn8i8vQ2y2mAnq
	, phone=010-1234-5678, username=gildong)
	*/
	public CustomUser(RegisterReqDto registerReqDto) {
		//사용자가 정의한 (select한) TblUsersVO 타입의 객체 tblUsersVO를
		//스프링 시큐리티에서 제공해주고 있는 UsersDetails 타입으로 변환
		//회원정보를 보내줄테니 이제부터 프링이 너가 관리해줘
		super(registerReqDto.getAeId(), registerReqDto.getPassword(),
				registerReqDto.getAuthVOList().stream()
												  .map(auth->new SimpleGrantedAuthority(auth.getCcgU001()))
												  .collect(Collectors.toList()));
		this.registerReqDto = registerReqDto;
	}

	public RegisterReqDto getRegisterReqDto() {
		return registerReqDto;
	}

	public void setRegisterReqDto(RegisterReqDto registerReqDto) {
		this.registerReqDto = registerReqDto;
	}
}










