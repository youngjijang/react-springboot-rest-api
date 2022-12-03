package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.Product;
import com.example.coffeeshop.model.ProductCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

import static com.example.coffeeshop.util.JdbcUtils.toUUID;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public ProductCategory createProductCategory(String category) {
        String sql = "INSERT INTO product_category(category) VALUES(:category)";
        String findSql = "SELECT * FROM product_category WHERE category = :category ";
        var update = jdbcTemplate.update(sql, Collections.singletonMap("category", category));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return jdbcTemplate.queryForObject(findSql, Collections.singletonMap("category", category), categoryRowMapper);
    }

    @Override
    public List<ProductCategory> findAllCategory() {
        String sql = "SELECT * FROM product_category";
        List<ProductCategory> categories = jdbcTemplate.query(sql, categoryRowMapper);
        return categories;
    }

    @Override
    public ProductCategory findCategoryByString(String category) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        String sql = "INSERT INTO products(product_id, name, category_code , price, total_amount, description)" +
                "  VALUES (UUID_TO_BIN(:productId),:name,:category,:price ,:totalAmount , :description)";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return product;
    }

    @Override
    public void deleteProduct(UUID productId) {
        String sql = "DELETE FROM products WHERE product_id = UUID_TO_BIN(:productId)";
        jdbcTemplate.update(sql, Collections.singletonMap("productId", productId.toString().getBytes()));
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        String sql = "SELECT p.product_id,p.name,p.price, p.total_amount,p.description, pc.code,pc.category FROM products p JOIN product_category pc on pc.code = p.category_code WHERE p.product_id = UUID_TO_BIN(:productId) ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, Collections.singletonMap("productId", productId.toString().getBytes()), productRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findByCategory(ProductCategory category) {
        String sql = "SELECT p.product_id,p.name,p.price, p.total_amount,p.description, pc.code,pc.category FROM products p JOIN product_category pc on pc.code = p.category_code WHERE pc.code = :code ";
        return jdbcTemplate.query(sql, Collections.singletonMap("code", category.code()), productRowMapper);
    }

    @Override
    public void updateTotalAmount(Product product) {
        String sql = "UPDATE products SET total_amount = :totalAmount WHERE product_id = UUID_TO_BIN(:productId)";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
    }

    @Override
    public void updateDescription(Product product) {
        String sql = "UPDATE products SET description = :description WHERE product_id = UUID_TO_BIN(:productId)";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.product_id,p.name,p.price, p.total_amount,p.description, pc.code,pc.category FROM products p JOIN product_category pc on pc.code = p.category_code";
        return jdbcTemplate.query(sql, productRowMapper);
    }


    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var productName = resultSet.getString("name");
        var category = new ProductCategory(resultSet.getInt("code"), resultSet.getString("category"));
        var totalAmount = resultSet.getInt("total_amount");
        var price = resultSet.getLong("price");
        var description = resultSet.getString("description");
        return new Product(productId, category, productName, price, totalAmount, description);
    };

    private Map<String, Object> toParamMap(Product product) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("productId", product.getProductId().toString().getBytes());
        paramMap.put("name", product.getName());
        paramMap.put("category", product.getCategory().code());
        paramMap.put("totalAmount", product.getTotalAmount());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        return paramMap;
    }

    private static final RowMapper<ProductCategory> categoryRowMapper = (resultSet, i) -> {
        var code = resultSet.getInt("code");
        var category = resultSet.getString("category");
        return new ProductCategory(code, category);
    };
}
