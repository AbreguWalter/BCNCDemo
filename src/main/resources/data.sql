
-- Currencies
INSERT INTO currencies (id, name, symbol, created_at, enabled) VALUES
    (1, 'Euro', 'EUR', CURRENT_TIMESTAMP, 1);

-- Brands
INSERT INTO brands (id, name, created_at, enabled) VALUES
    (1, 'ZARA', CURRENT_TIMESTAMP, 1);

-- Products
INSERT INTO products (id, name, description, created_at, enabled) VALUES
    (35455, 'Product 35455', 'ZARA Product Description', CURRENT_TIMESTAMP, 1);

-- Price lists
INSERT INTO price_lists (id, name, description, created_at, enabled) VALUES
     (1, 'Base price list', 'Default pricing', CURRENT_TIMESTAMP, 1),
     (2, 'Afternoon promo', 'Short-term discount', CURRENT_TIMESTAMP, 1),
     (3, 'Morning promo', 'Morning discount', CURRENT_TIMESTAMP, 1),
     (4, 'Premium pricing', 'High priority pricing', CURRENT_TIMESTAMP, 1);

INSERT INTO prices (brand_id, product_id, price_list, currency_id, start_date, end_date, priority, price, created_at, enabled) VALUES
    (1, 35455, 1, 1, '2020-06-14T00:00:00', '2020-12-31T23:59:59', 0, 35.50, CURRENT_TIMESTAMP, 1),
    (1, 35455, 2, 1, '2020-06-14T15:00:00', '2020-06-14T18:30:00', 1, 25.45, CURRENT_TIMESTAMP, 1),
    (1, 35455, 3, 1, '2020-06-15T00:00:00', '2020-06-15T11:00:00', 1, 30.50, CURRENT_TIMESTAMP, 1),
    (1, 35455, 4, 1, '2020-06-15T16:00:00', '2020-12-31T23:59:59', 1, 38.95, CURRENT_TIMESTAMP, 1);