package com.campus.backend.mapper;

import com.campus.backend.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT id, name, sort, create_time AS createTime FROM category ORDER BY sort ASC")
    List<Category> findAll();

    @Insert("INSERT INTO category (name, sort) VALUES (#{name}, #{sort})")
    int insert(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteById(Long id);
}