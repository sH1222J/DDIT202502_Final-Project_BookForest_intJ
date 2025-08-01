package com.bookforest.project_bookforest_intj.user.repository;



import com.bookforest.project_bookforest_intj.user.dto.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository  {
    public RegisterReqDto findByUsername(String username);
    String findUsernameByNameAndEmail(@Param("userNm") String userNm, @Param("email") String email);
    UserVO findByUsernameOrEmail(String usernameOrEmail);
    void updatePassword(@Param("username") String username, @Param("password") String password);
}

