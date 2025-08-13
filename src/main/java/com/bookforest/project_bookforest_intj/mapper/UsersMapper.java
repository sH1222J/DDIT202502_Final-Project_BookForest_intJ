package com.bookforest.project_bookforest_intj.mapper;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import org.apache.ibatis.annotations.Mapper;

//자바빈 등록
@Mapper
public interface UsersMapper {

    //USERS 테이블에 insert(회원가입)
    public int registerUser(RegisterReqDto registerReqDto);

    //회원목록
    //public List<UserVO>

    //회원수
}
