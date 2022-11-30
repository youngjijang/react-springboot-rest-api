package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Email;
import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.OrderItem;
import com.example.coffeeshop.model.OrderStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static com.example.coffeeshop.util.JdbcUtils.toLocalDateTime;
import static com.example.coffeeshop.util.JdbcUtils.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Order createOrder(Order order) {
        String orderSql = "INSERT INTO orders(order_id, email, address, postcode, order_status, create_at, update_at) " +
                "VALUES (UUID_TO_BIN(:orderId), :email, :address, :postcode, :orderStatus, :createAt, :updateAt)";
        String orderItemSql = "INSERT INTO order_item(order_id, product_id, category, price, quantity, create_at, update_at) " +
                "VALUES (UUID_TO_BIN(:orderId), UUID_TO_BIN(:productId), :category, :price, :quantity, :createAt, :updateAt)";

        jdbcTemplate.update(orderSql, toOrderParamMap(order));
        order.getOrderItems()
                .forEach(item -> jdbcTemplate.update(orderItemSql,toOrderItemParamMap(order.getOrderId(), item)));
        return order;
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        String orderSql = "select * from orders WHERE order_id = UUID_TO_BIN(:orderId)";
        String orderItem = "select * from order_item WHERE order_id = :orderId";
        try {
            var order = jdbcTemplate.queryForObject(orderSql, Collections.singletonMap("orderId", orderId.toString().getBytes()),orderRowMapper);
            var orderItems = jdbcTemplate.query(orderItem,
                    Collections.singletonMap("orderId",orderId.toString().getBytes()),orderItemRowMapper);
            orderItems.forEach(item -> order.getOrderItems().add(item));
            return Optional.ofNullable(order);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> findByEmail(Email email) {
        String orderSql = "select * from orders WHERE email = :email";
        String orderItem = "select * from order_item WHERE order_id = :orderId";
        try {
            var order = jdbcTemplate.queryForObject(orderSql, Collections.singletonMap("email",email.address()),orderRowMapper);
            var orderItems = jdbcTemplate.query(orderItem,
                    Collections.singletonMap("orderId",order.getOrderId().toString().getBytes()),orderItemRowMapper);
            orderItems.forEach(item -> order.getOrderItems().add(item));
            return Optional.ofNullable(order);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> findByEmailAndAddress(Email email, String address) {
        String orderSql = "select * from orders WHERE email = :email AND address = :address";
        String orderItem = "select * from order_item WHERE order_id = :orderId";
        try {
            var order = jdbcTemplate.queryForObject(orderSql, Map.of("email",email.address(),"address",address),orderRowMapper);
            var orderItems = jdbcTemplate.query(orderItem,
                    Collections.singletonMap("orderId",order.getOrderId().toString().getBytes()),orderItemRowMapper);
            orderItems.forEach(item -> order.getOrderItems().add(item));
            return Optional.ofNullable(order);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void insertOrderItem(UUID orderId, OrderItem orderItem) {

    }

    @Override
    public void updateOrderItem(UUID orderId, OrderItem orderItem) {

    }

    @Override
    public void updateAddress(Order order) {

    }

    @Override
    public void deleteOrder(Order order) {


    }

    private Map<String, Object> toOrderParamMap(Order order) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", order.getOrderId().toString().getBytes());
        paramMap.put("email", order.getEmail().address());
        paramMap.put("address", order.getAddress());
        paramMap.put("orderStatus", order.getOrderStatus().toString());
        paramMap.put("createAt", order.getCreatedAt());
        paramMap.put("updateAt", order.getUpdateAt());
        return paramMap;
    }

    private Map<String, Object> toOrderItemParamMap(UUID orderId, OrderItem item) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("orderId", orderId.toString().getBytes());
        paramMap.put("productId", item.productId().toString().getBytes());
        paramMap.put("price", item.price());
        paramMap.put("quantity", item.quantity());
        return paramMap;
    }

    private static final RowMapper<OrderItem> orderItemRowMapper = (resultSet, i) ->{
        var productId = toUUID(resultSet.getBytes("product_id"));
        var price = resultSet.getLong("price");
        var quantity = resultSet.getInt("description");
        return new OrderItem(productId,  price, quantity);
    };;

    private static final RowMapper<Order> orderRowMapper =(resultSet, i) -> {
        var orderId = toUUID(resultSet.getBytes("order_id"));
        var email = new Email(resultSet.getString("email"));
        var address = resultSet.getString("address");
        var orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        var createAt = toLocalDateTime(resultSet.getTimestamp("create_at"));
        var updateAt = toLocalDateTime(resultSet.getTimestamp("update_at"));
        return new Order(orderId, email, address, new ArrayList<>(),orderStatus, createAt, updateAt);
    };


}
