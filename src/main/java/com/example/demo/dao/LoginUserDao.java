package com.example.demo.dao;

import com.example.demo.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuxueli 2019-05-04 16:44:59
 */
@Mapper
public interface LoginUserDao {

    List<LoginUser> pageList(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("username") String username,
                             @Param("role") int role);

    int pageListCount(@Param("offset") int offset,
                      @Param("pagesize") int pagesize,
                      @Param("username") String username,
                      @Param("role") int role);

    LoginUser loadByUserName(@Param("username") String username);

    int save(LoginUser xxlJobUser);

    int update(LoginUser xxlJobUser);

    int delete(@Param("id") int id);

}
