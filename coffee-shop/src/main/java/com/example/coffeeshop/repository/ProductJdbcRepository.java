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
    public Product createProduct(Product product) {
        String sql = "INSERT INTO products(product_id, name, category_code , price, total_amount, description)" +
                "  VALUES (UUID_TO_BIN(:productId),:name,:category,:price ,:totalAmount , :description)";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return product;
    }

    @Override
    public void deleteProduct(UUID productId) {
        String sql = "DELETE FROM products WHERE product_id = UUID_TO_BIN(:productId))";
        jdbcTemplate.update(sql, Collections.singletonMap("productId", productId));
    }

    @Override
    public Optional<Product> findById(UUID productId) {
        String sql = "SELECT p.product_id,p.name,p.price, p.total_amount,p.description, pc.code,pc.category FROM products p JOIN product_category pc on pc.code = p.category_code WHERE p.product_id = UUID_TO_BIN(:productId) ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, Collections.singletonMap("productId", productId), productRowMapper));
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
        String sql = "UPDATE products SET total_amount = :totalAmount WHERE product_id = :UUID_TO_BIN(:productId))";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
    }

    @Override
    public void updateDescription(Product product) {
        String sql = "UPDATE products SET description = :description WHERE product_id = :UUID_TO_BIN(:productId))";
        var update = jdbcTemplate.update(sql, toParamMap(product));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
    }


    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        var productId = toUUID(resultSet.getBytes("product_id"));
        var productName = resultSet.getString("product_name");
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
        paramMap.put("category", product.getCategory().category());
        paramMap.put("totalAmount", product.getTotalAmount());
        paramMap.put("price", product.getPrice());
        paramMap.put("description", product.getDescription());
        return paramMap;
    }
}
