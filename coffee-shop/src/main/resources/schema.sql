CREATE TABLE product_category
(
    code     int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category varchar(20)
);

CREATE TABLE products
(
    product_id   binary(16) PRIMARY KEY,
    name         varchar(20) NOT NULL,
    category_code    int(20) NOT NULL,
    price        bigint      NOT NULL,
    total_amount int         NOT NULL,
    description  varchar(50),
    CONSTRAINT fk_category_code FOREIGN KEY (category_code) REFERENCES product_category (code) ON UPDATE CASCADE
);


CREATE TABLE orders
(
    order_id     binary(16) PRIMARY KEY,
    email        VARCHAR(50)  NOT NULL,
    address      VARCHAR(200) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    create_at    datetime(6) NOT NULL,
    update_at    datetime(6) DEFAULT NULL,
    index (email)
);

CREATE TABLE order_items
(
    seq        bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   binary(16) NOT NULL,
    product_id binary(16) NOT NULL,
    price      bigint NOT NULL,
    quantity   int    NOT NULL,
    create_at  datetime(6) NOT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES products (product_id)
)
