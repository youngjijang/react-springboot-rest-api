package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;
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
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

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
    ProductJdbcRepository productJdbcRepository;

    EmbeddedMysql embeddedMysql;

    ProductCategory productCategory;

    Product product;


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
    }

    @AfterAll
    void tearDown() {
        embeddedMysql.stop();
    }


    @Test
    @Order(1)
    void createProductCategory() {
        productCategory = productJdbcRepository.createProductCategory("원두");
        assertThat(productCategory,notNullValue());
    }

    @Test
    @Order(2)
    void findAllCategory() {
        var categories = productJdbcRepository.findAllCategory();
        assertThat(categories.isEmpty(),is(false));
        assertThat(categories.get(0),samePropertyValuesAs(productCategory));
    }

    @Test
    @Order(3)
    void findCategoryByString() {
    }

    @Test
    @Order(4)
    void createProduct() {
        product = new Product(UUID.randomUUID(), productCategory, "콜롬비아 원두", 40000, 50, "맛있다.");
        productJdbcRepository.createProduct(product);

        var findProduct = productJdbcRepository.findById(product.getProductId());
        assertThat(findProduct.isPresent(), is(true));
    }


    @Test
    @Order(5)
    void findById() {
        var findProduct = productJdbcRepository.findById(product.getProductId());
        assertThat(findProduct.isPresent(), is(true));
        assertThat(findProduct.get(),samePropertyValuesAs(product));
    }

    @Test
    @Order(6)
    void findByCategory() {
        var products = productJdbcRepository.findByCategory(productCategory);
        assertThat(products.size(), is(1));
        assertThat(products.contains(product),is(true));
    }

    @Test
    @Order(7)
    void updateTotalAmount() {
        product.increaseTotalAmount(1);
        productJdbcRepository.updateTotalAmount(product);

        var findProduct = productJdbcRepository.findById(product.getProductId());
        assertThat(findProduct.isPresent(), is(true));
        assertThat(findProduct.get().getTotalAmount(),is(51));
    }

    @Test
    @Order(8)
    void updateDescription() {
        product.changeDescription("고급 콩");
        productJdbcRepository.updateDescription(product);

        var findProduct = productJdbcRepository.findById(product.getProductId());
        assertThat(findProduct.isPresent(), is(true));
        assertThat(findProduct.get().getDescription(),is("고급 콩"));
    }

    @Test
    @Order(9)
    void deleteProduct() {
        productJdbcRepository.deleteProduct(product.getProductId());
        var findProduct = productJdbcRepository.findById(product.getProductId());
        assertThat(findProduct.isEmpty(), is(true));
    }

}