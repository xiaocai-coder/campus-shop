package com.campus.backend.mapper;

import com.campus.backend.entity.Order;
import com.campus.backend.entity.OrderItem;
import com.campus.backend.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("""
            SELECT
              id,
              category_id AS categoryId,
              name,
              price,
              stock,
              icon,
              description,
              status,
              create_time AS createTime,
              update_time AS updateTime
            FROM product
            WHERE id = #{id}
            """)
    Product findProductById(Long id);

    @Insert("""
            INSERT INTO orders (user_id, total_quantity, total_price, status)
            VALUES (#{userId}, #{totalQuantity}, #{totalPrice}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(Order order);

    @Insert("""
            INSERT INTO order_item
            (order_id, product_id, product_name, product_price, quantity, subtotal)
            VALUES
            (#{orderId}, #{productId}, #{productName}, #{productPrice}, #{quantity}, #{subtotal})
            """)
    int insertOrderItem(OrderItem orderItem);

    @Select("""
            SELECT
              id,
              user_id AS userId,
              total_quantity AS totalQuantity,
              total_price AS totalPrice,
              status,
              create_time AS createTime
            FROM orders
            WHERE user_id = #{userId}
            ORDER BY id DESC
            """)
    List<Order> findOrdersByUserId(Long userId);

    @Select("""
            SELECT
              id,
              order_id AS orderId,
              product_id AS productId,
              product_name AS productName,
              product_price AS productPrice,
              quantity,
              subtotal
            FROM order_item
            WHERE order_id = #{orderId}
            """)
    List<OrderItem> findItemsByOrderId(Long orderId);

    @Select("""
            SELECT
              id,
              user_id AS userId,
              total_quantity AS totalQuantity,
              total_price AS totalPrice,
              status,
              create_time AS createTime
            FROM orders
            ORDER BY id DESC
            """)
    List<Order> findAllOrders();

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateOrderStatus(Long id, String status);
}