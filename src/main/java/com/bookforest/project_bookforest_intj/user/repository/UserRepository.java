package com.bookforest.project_bookforest_intj.user.repository;



import com.bookforest.project_bookforest_intj.user.vo.RegisterReqDto;
import com.bookforest.project_bookforest_intj.user.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository  {
    public RegisterReqDto findByUsername(String username);
    String findUsernameByNameAndEmail(@Param("userNm") String userNm, @Param("email") String email);
    UserVO findByUsernameOrEmail(String usernameOrEmail);
    void updatePassword(@Param("username") String username, @Param("password") String password);
    void updateMustChangePassword(@Param("username") String username, @Param("mustChangePassword") boolean mustChangePassword);
    UserVO findByNameAndUsernameAndEmail(@Param("name") String name, @Param("username") String username, @Param("email") String email);
}

