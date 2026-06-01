package com.campus.backend.mapper;

import com.campus.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("""
            SELECT
              id,
              username,
              password,
              nickname,
              role,
              create_time AS createTime
            FROM user
            WHERE username = #{username}
            """)
    User findByUsername(String username);
}