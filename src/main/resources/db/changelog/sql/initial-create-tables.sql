CREATE TABLE `icommerce`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password_hash` VARCHAR(100) NOT NULL,
  `display_name` VARCHAR(50),
  `mobile` VARCHAR(15),
  `email` VARCHAR(50),
  `address` VARCHAR(200),
  PRIMARY KEY (`id`) );


CREATE TABLE `icommerce`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `title` VARCHAR(100),
  `description` TINYTEXT,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_brand_name` (`name` ASC));


CREATE TABLE `icommerce`.`product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `title` VARCHAR(200),
  `description` TINYTEXT,
  `brand_id` INT,
  `price` FLOAT NOT NULL DEFAULT 0,
  `quantity` INT,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  `created_by` VARCHAR(100),
  `updated_by` VARCHAR(100),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_brand`
    FOREIGN KEY (`brand_id`)
    REFERENCES `icommerce`.`brand`(`id`));


CREATE TABLE `icommerce`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_parent_id` INT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_category_name` (`name` ASC),
  INDEX `idx_category_parent` (`category_parent_id` ASC),
  CONSTRAINT `fk_category_parent`
    FOREIGN KEY (`category_parent_id`)
    REFERENCES `icommerce`.`category` (`id`));


CREATE TABLE `icommerce`.`product_category` (
  `product_id` BIGINT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `category_id`),
  INDEX `idx_product_category_product` (`product_id` ASC),
  INDEX `idx_product_category_category` (`category_id` ASC),
  CONSTRAINT `fk_product_category_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `icommerce`.`product` (`id`),
  CONSTRAINT `fk_product_category_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `icommerce`.`category` (`id`));


CREATE TABLE `icommerce`.`shipping_address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `mobile` VARCHAR(15) NOT NULL,
  `address` TINYTEXT,
  PRIMARY KEY (`id`),
  INDEX `idx_shipping_address_user` (`user_id` ASC),
  CONSTRAINT `fk_shipping_address_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `icommerce`.`user` (`id`));


CREATE TABLE `icommerce`.`cart` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL ,
  `status` VARCHAR(20) NOT NULL,
  `shipping_address_id` INT,
  `amount` DOUBLE,
  `discount` DOUBLE,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  `created_by` VARCHAR(100),
  `updated_by` VARCHAR(100),
  PRIMARY KEY (`id`),
  INDEX `idx_cart_user` (`user_id` ASC),
  INDEX `idx_cart_shipping_address` (`shipping_address_id` ASC),
  CONSTRAINT `fk_cart_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `icommerce`.`user` (`id`),
  CONSTRAINT `fk_cart_shipping_address`
    FOREIGN KEY (`shipping_address_id`)
    REFERENCES `icommerce`.`shipping_address` (`id`));


CREATE TABLE `icommerce`.`cart_products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT NOT NULL,
  `cart_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_cart_products_product` (`product_id` ASC),
  INDEX `idx_cart_products_card` (`cart_id` ASC),
  CONSTRAINT `fk_cart_products_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `icommerce`.`product` (`id`),
  CONSTRAINT `fk_cart_products_card`
    FOREIGN KEY (`cart_id`)
    REFERENCES `icommerce`.`cart` (`id`));


CREATE TABLE `icommerce`.`purchase_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL ,
  `status` VARCHAR(20) NOT NULL,
  `cart_id` BIGINT NOT NULL ,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  `created_by` VARCHAR(100),
  `updated_by` VARCHAR(100),
  PRIMARY KEY (`id`),
  INDEX `idx_order_user` (`user_id` ASC),
  INDEX `idx_order_cart` (`cart_id` ASC),
  CONSTRAINT `fk_order_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `icommerce`.`user` (`id`),
  CONSTRAINT `fk_order_cart`
    FOREIGN KEY (`cart_id`)
    REFERENCES `icommerce`.`cart` (`id`));


CREATE TABLE `icommerce`.`order_shipping_progress` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `current_address` TINYTEXT,
  `created_at` TIMESTAMP,
  `updated_at` TIMESTAMP,
  `created_by` VARCHAR(100),
  `updated_by` VARCHAR(100),
  PRIMARY KEY (`id`),
INDEX `idx_order_shipping_progress_order` (`order_id` ASC),
CONSTRAINT `fk_order_shipping_progress_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `icommerce`.`purchase_order` (`id`));


CREATE TABLE `icommerce`.`colour` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `colour_hex_value` VARCHAR(10),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idx_colour_name` (`name` ASC));


CREATE TABLE `icommerce`.`product_colour` (
  `product_id` BIGINT NOT NULL,
  `colour_id` INT NOT NULL,
  PRIMARY KEY (`product_id`, `colour_id`),
  INDEX `idx_product_colour_product` (`product_id` ASC),
  INDEX `idx_product_colour_colour` (`colour_id` ASC),
  CONSTRAINT `fk_product_colour_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `icommerce`.`product` (`id`),
  CONSTRAINT `fk_product_colour_colour`
    FOREIGN KEY (`colour_id`)
    REFERENCES `icommerce`.`colour` (`id`));