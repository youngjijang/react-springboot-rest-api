package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Order;
import com.example.coffeeshop.model.*;
import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderJdbcRepositoryTest {

    @Configuration
    @ComponentScan
    static class Config {
        @Bean
        public DataSource dataSource() {

            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-shop_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class) // datasource 만들 구현체 타입 지정
                    .build();
            return dataSource;
        }
    }

    @Autowired
    OrderJdbcRepository orderJdbcRepository;

    @Autowired
    ProductJdbcRepository productJdbcRepository;

    EmbeddedMysql embeddedMysql;

    Product product;

    Order order;

    OrderItem orderItem;


    @BeforeAll
    void setUp() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-shop_mgmt", classPathScript("schema.sql"))
                .start();

        ProductCategory productCategory = productJdbcRepository.createProductCategory("원두");
        product = new Product(UUID.randomUUID(), productCategory, "콜롬비아 원두", 40000, 50, "맛있다.");
        orderItem = new OrderItem(product.getProductId(), product.getPrice(), 10, LocalDateTime.now());
        order = new Order(UUID.randomUUID(), new Email("test@test"), "테스트동 테스트호", new ArrayList<>());
        order.addOrderItems(orderItem);


        productJdbcRepository.createProduct(product);
    }

    @AfterAll
    void tearDown() {
        embeddedMysql.stop();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void createOrder() {
        orderJdbcRepository.createOrder(order);
        var order = orderJdbcRepository.findById(this.order.getOrderId());
        assertThat(order.isPresent(), is(true));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    void findById() {
        var order = orderJdbcRepository.findById(this.order.getOrderId());
        assertThat(order.isPresent(), is(true));
        assertThat(order.get(), samePropertyValuesAs(this.order));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void findByEmail() {
        var order = orderJdbcRepository.findByEmail(this.order.getEmail());
        assertThat(order.isEmpty(), is(false));
        assertThat(order.get(0), samePropertyValuesAs(this.order));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void findAcceptOrderByEmailAndAddress() {
        var order = orderJdbcRepository.findAcceptOrderByEmailAndAddress(this.order.getEmail(), this.order.getAddress());
        assertThat(order.isPresent(), is(true));
        assertThat(order.get(), samePropertyValuesAs(this.order));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void insertOrderItem() {
        var newOrderItem = new OrderItem(product.getProductId(), product.getPrice(), 2, LocalDateTime.now());
        orderJdbcRepository.insertOrderItem(order.getOrderId(), newOrderItem);
        var order = orderJdbcRepository.findById(this.order.getOrderId());
        assertThat(order.isPresent(), is(true));
        assertThat(order.get().getOrderItems().size(), is(2));
        assertThat(order.get().getOrderItems().contains(newOrderItem), is(true));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void updateAddress() {
        order.changeAddress("우리동");
        orderJdbcRepository.updateAddress(order);

        var order = orderJdbcRepository.findById(this.order.getOrderId());
        assertThat(order.isPresent(), is(true));
        assertThat(order.get().getAddress(), is("우리동"));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    void deleteOrder() {
        orderJdbcRepository.deleteOrder(order.getOrderId());
        var order = orderJdbcRepository.findById(this.order.getOrderId());
        assertThat(order.isEmpty(), is(true));
    }
}