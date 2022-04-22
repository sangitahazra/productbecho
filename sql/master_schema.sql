-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.28 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for product_becho
CREATE DATABASE IF NOT EXISTS `product_becho` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `product_becho`;

-- Dumping structure for table product_becho.abstract_order
CREATE TABLE IF NOT EXISTS `abstract_order` (
  `pk` int NOT NULL,
  `total_amount` double DEFAULT NULL,
  `address_pk` int NOT NULL,
  `abstract_order_entry_pk` int NOT NULL,
  `user_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_abstract_order_address` (`address_pk`),
  KEY `FK_abstract_order_abstract_order_entry` (`abstract_order_entry_pk`),
  KEY `FK_abstract_order_user` (`user_pk`),
  CONSTRAINT `FK_abstract_order_abstract_order_entry` FOREIGN KEY (`abstract_order_entry_pk`) REFERENCES `abstract_order_entry` (`pk`),
  CONSTRAINT `FK_abstract_order_address` FOREIGN KEY (`address_pk`) REFERENCES `address` (`pk`),
  CONSTRAINT `FK_abstract_order_user` FOREIGN KEY (`user_pk`) REFERENCES `user` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.abstract_order: ~0 rows (approximately)
DELETE FROM `abstract_order`;

-- Dumping structure for table product_becho.abstract_order_entry
CREATE TABLE IF NOT EXISTS `abstract_order_entry` (
  `pk` int NOT NULL,
  `quantity` int NOT NULL,
  `vp_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_abstract_order_entry_variant_product` (`vp_pk`),
  CONSTRAINT `FK_abstract_order_entry_variant_product` FOREIGN KEY (`vp_pk`) REFERENCES `variant_product` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.abstract_order_entry: ~0 rows (approximately)
DELETE FROM `abstract_order_entry`;

-- Dumping structure for table product_becho.address
CREATE TABLE IF NOT EXISTS `address` (
  `pk` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `line1` varchar(50) NOT NULL,
  `line2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `zip` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.address: ~0 rows (approximately)
DELETE FROM `address`;

-- Dumping structure for table product_becho.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `pk` int NOT NULL,
  `code` varchar(50) NOT NULL,
  `abstract_order_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_cart_abstract_order` (`abstract_order_pk`),
  CONSTRAINT `FK_cart_abstract_order` FOREIGN KEY (`abstract_order_pk`) REFERENCES `abstract_order` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.cart: ~0 rows (approximately)
DELETE FROM `cart`;

-- Dumping structure for table product_becho.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.hibernate_sequence: ~0 rows (approximately)
DELETE FROM `hibernate_sequence`;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(32);

-- Dumping structure for table product_becho.order
CREATE TABLE IF NOT EXISTS `order` (
  `pk` int NOT NULL,
  `code` varchar(50) NOT NULL,
  `abstract_order_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_order_abstract_order` (`abstract_order_pk`),
  CONSTRAINT `FK_order_abstract_order` FOREIGN KEY (`abstract_order_pk`) REFERENCES `abstract_order` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.order: ~0 rows (approximately)
DELETE FROM `order`;

-- Dumping structure for table product_becho.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `pk` int NOT NULL,
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `vp_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_stock_variant_product` (`vp_pk`),
  CONSTRAINT `FK_stock_variant_product` FOREIGN KEY (`vp_pk`) REFERENCES `variant_product` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.stock: ~0 rows (approximately)
DELETE FROM `stock`;

-- Dumping structure for table product_becho.user
CREATE TABLE IF NOT EXISTS `user` (
  `pk` int NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `role` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `id` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.user: ~2 rows (approximately)
DELETE FROM `user`;
INSERT INTO `user` (`pk`, `username`, `role`, `password`, `phone`) VALUES
	(1, 'abhirup', 'ADMIN', '1234', NULL);

-- Dumping structure for table product_becho.variant_product
CREATE TABLE IF NOT EXISTS `variant_product` (
  `pk` int NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `image` longblob,
  `name` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.variant_product: ~0 rows (approximately)
DELETE FROM `variant_product`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
