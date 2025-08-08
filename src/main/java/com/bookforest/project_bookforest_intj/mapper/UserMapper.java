package com.bookforest.project_bookforest_intj.mapper;

import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int registerUser(RegisterReqDto registerReqDto);

    UserVO findByUserId(String userId);

}