-- ============================================================================
-- users TABLE
-- ============================================================================
CREATE TABLE users
(
    id         INTEGER GENERATED ALWAYS AS IDENTITY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE,
    timestamps TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_users_id PRIMARY KEY (id),

    CONSTRAINT uq_users_email UNIQUE (email, is_deleted)

);

-- ============================================================================
-- product TABLE
-- ============================================================================
CREATE TABLE product
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY,
    name        VARCHAR(255)                  NOT NULL UNIQUE,
    description VARCHAR(255)                  NOT NULL,
    price       NUMERIC(9) CHECK (price >= 0) NOT NULL,
    quantity    NUMERIC(9)                    NOT NULL,
    is_deleted  BOOLEAN                       NOT NULL DEFAULT FALSE,
    timestamps  TIMESTAMP                              DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_product_id PRIMARY KEY (id)
);

-- ============================================================================
-- item TABLE
-- ============================================================================

CREATE TABLE item
(
    id               INTEGER GENERATED ALWAYS AS IDENTITY,
    quantity         INTEGER,
    unit_price       NUMERIC(19, 2),
    discount_applied NUMERIC(19, 2),
    total_price      NUMERIC(19, 2),
    status           VARCHAR(255) NOT NULL,
    fk_product_id    BIGINT,
    fk_user_id       BIGINT,
    fk_order_id      BIGINT    DEFAULT NULL,
    timestamps       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_item_id PRIMARY KEY (id),

    CONSTRAINT fk_item_product_id
        FOREIGN KEY (fk_product_id)
            REFERENCES product (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_item_user_id
        FOREIGN KEY (fk_user_id)
            REFERENCES users (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

-- ============================================================================
-- order TABLE
-- ============================================================================

CREATE TABLE orders
(
    id                     INTEGER GENERATED ALWAYS AS IDENTITY,
    total_discount_applied NUMERIC(19, 2),
    total_price            NUMERIC(19, 2),
    status                 VARCHAR(255) NOT NULL,
    fk_user_id             BIGINT,
    timestamps             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_orders_id PRIMARY KEY (id),

    CONSTRAINT fk_orders_user_id
        FOREIGN KEY (fk_user_id)
            REFERENCES users (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);



ALTER TABLE item
    ADD CONSTRAINT fk_item_order_id FOREIGN KEY (fk_order_id) REFERENCES "orders" (id);
