CREATE TABLE currencies (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(5),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    enabled BIT DEFAULT 0
);

CREATE TABLE brands (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    enabled BIT DEFAULT 0
);

CREATE TABLE products (
      id BIGINT PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      description TEXT,
      created_at TIMESTAMP NOT NULL,
      updated_at TIMESTAMP,
      enabled BIT DEFAULT 0
);

CREATE TABLE price_lists (
     id INT PRIMARY KEY,
     name VARCHAR(50),
     description TEXT,
     created_at TIMESTAMP NOT NULL,
     updated_at TIMESTAMP,
     enabled BIT DEFAULT 0
);

CREATE TABLE prices (
    id IDENTITY PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price_list INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    priority INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    currency_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    enabled BIT DEFAULT 0,

    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES brands(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_price_list FOREIGN KEY (price_list) REFERENCES price_lists(id),
    CONSTRAINT fk_currency FOREIGN KEY (currency_id) REFERENCES currencies(id)
);