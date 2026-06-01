package com.campus.backend.mapper;

import com.campus.backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("""
            SELECT
              p.id,
              p.category_id AS categoryId,
              c.name AS categoryName,
              p.name,
              p.price,
              p.stock,
              p.icon,
              p.description,
              p.status,
              p.create_time AS createTime,
              p.update_time AS updateTime
            FROM product p
            LEFT JOIN category c ON p.category_id = c.id
            ORDER BY p.id DESC
            """)
    List<Product> findAll();

    @Insert("""
        INSERT INTO product
        (category_id, name, price, stock, icon, description, status)
        VALUES
        (#{categoryId}, #{name}, #{price}, #{stock}, #{icon}, #{description}, #{status})
        """)
    int insert(Product product);

    @Update("""
        UPDATE product
        SET
          category_id = #{categoryId},
          name = #{name},
          price = #{price},
          stock = #{stock},
          icon = #{icon},
          description = #{description},
          status = #{status}
        WHERE id = #{id}
        """)
    int update(Product product);

    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE product SET status = #{status} WHERE id = #{id}")
    int updateStatus(Long id, String status);
}

