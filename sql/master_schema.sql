-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.26 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
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
  `address_pk` int DEFAULT NULL,
  `user_pk` int DEFAULT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_abstract_order_address` (`address_pk`),
  KEY `FK_abstract_order_user` (`user_pk`),
  CONSTRAINT `FK_abstract_order_address` FOREIGN KEY (`address_pk`) REFERENCES `address` (`pk`),
  CONSTRAINT `FK_abstract_order_user` FOREIGN KEY (`user_pk`) REFERENCES `user` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.abstract_order: ~0 rows (approximately)
/*!40000 ALTER TABLE `abstract_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `abstract_order` ENABLE KEYS */;

-- Dumping structure for table product_becho.abstract_order_entry
CREATE TABLE IF NOT EXISTS `abstract_order_entry` (
  `pk` int NOT NULL,
  `quantity` int NOT NULL,
  `vp_pk` int NOT NULL,
  `abstract_order_pk` int NOT NULL,
  PRIMARY KEY (`pk`),
  KEY `FK_abstract_order_entry_variant_product` (`vp_pk`),
  KEY `FK_abstract_order_entry_abstract_order` (`abstract_order_pk`),
  CONSTRAINT `FK_abstract_order_entry_abstract_order` FOREIGN KEY (`abstract_order_pk`) REFERENCES `abstract_order` (`pk`),
  CONSTRAINT `FK_abstract_order_entry_variant_product` FOREIGN KEY (`vp_pk`) REFERENCES `variant_product` (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.abstract_order_entry: ~0 rows (approximately)
/*!40000 ALTER TABLE `abstract_order_entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `abstract_order_entry` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;

-- Dumping structure for table product_becho.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table product_becho.hibernate_sequence: ~0 rows (approximately)
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES
	(67);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` (`pk`, `warehouse_code`, `quantity`, `vp_pk`) VALUES
	(36, 'RNC', 25, 35);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`pk`, `username`, `role`, `password`, `phone`) VALUES
	(1, 'abhirup', 'ADMIN', '1234', NULL),
	(32, 'iamabhirup7@gmail.com', 'GUEST', NULL, '31312121');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

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
/*!40000 ALTER TABLE `variant_product` DISABLE KEYS */;
INSERT INTO `variant_product` (`pk`, `code`, `description`, `image`, `name`, `price`) VALUES
	(35, 'MICROTEKINVERTER', 'One inverter to charge everything', _binary 0xffd8ffe000104a46494600010100000100010000ffdb008400090607100f0f0f10100f10100f0f100f100f0f101010100f0f0f1511161815151513181d2820181a251b1515213121252a2b2e2f2e171f3338333839342d2e2d010a0a0a0e0d0e150f0f1a2b1d151d372b2b372d2d2d2d2d2d2b2f2e372d302d2b2d2b2d2d2b2b382b2b2b372e2d302d2d2b2b2d37352d2b2b2b372d372d2b302bffc000110800e100e103012200021101031101ffc4001c0001000203010101000000000000000000000105020304070608ffc400471000020102020507090406090500000000000102031104210512314151133261718191b10722425272a1c1d1f0061482b323436274c2f11524335383a2b2e1e22544637392ffc40017010101010100000000000000000000000000010203ffc4001b11010101010100030000000000000000000001111202213141ffda000c03010002110311003f00f71000020900402401009004024800480000000000000000000000000000000000000001048000000000000020900000000000000000000000000000000000000000041241200000000000000000000000000000000000000000000000000000000041241200000000000000000010e4b8a317563eb2ef40660d6ebc3d68f7a3178987ac80dc0d0f194fd6f733178ea7c5f7303a41c8f4843f6bb8c5e91870977203b41c2f492f565ee33a5a420f6de2fa767781d6088c93cd66b8a24000000000000000008397158e54e4a2d66d5d3bd96dd87514da7179f0f65f88a3ade907ea2ef21e3a7c23ef2b6855b64f671de8ea5de8ce8dcf193e8ee23ef3538fb9186a9361a25d69faccc5ce5eb3ef26c4d80c2ef8bef1abd26cb748d5441ab506a1b6c83406ad443511b2c2c06bd52354cec1a28c354c754d9621a035b462e26db18d80c21271e6b6babe47552d2325ce4a5d2b2673b4434345ad2c6c25becf84b23a0a07133a55270e6b6970dabb99745e02b69e916b9f0ed8fc99d747174e7cd92bf0793ee6346f00140000414da794b94a4d5b5356a292b79dad78ead9f0e777a2e8aad36b3a7f8be04a2ba08df4a6d7c8d7146c51323aa9c93d86470ce9caf1945d9c5e7d29dae7651a97767b765c05c93a05fa7dec0d167c1f732755f06456c5460e09dfcf9c60acb7be3d0746aafa480d167f4d0517d1de69d35a4a9e0f0f5311575b93a51d69d95da8adaecb69f152f2bba3b72af27c15397c5164b47de6a3fab8d4fab1e735bcb1e0d64b0f886f2c9a8279ecdace3a9e59a8fa383abf89c17848bcd1ea5a9f597cc6afd5d1e4557cb3cbd1c12e8d6a8d78267155f2c98b7cdc2518f5d472fe11cd1ed3aabeaff223557d5cf0aade573493e6c70f1ff0dbf068e1ade53b4acbf5f087b14daf193439a3f41eaafa5fee1c7a3dc8fcd75fedce959f3b1f5ba92a515ee89c35fed16367cfc5d77fe249780e07e9e9d58476ca2bae5147256d2f85873b11423d75a1f33f2fd4c655973aad597b5526fc59651d1985e4e13a98e86b495294a9c21ca4e3ad7d78bb3769472dbc7b0bc8fd0d86fb4782ab563469e2a8d4ab3beac212949bb2bbd9d08b39767ff2be2787f932a7835a5307c84ab4aaea6225539451504b92792c93bddaf79ee33d866cc0a494af676b24f62e2fe42747f6a4460de72f661fea99ba641aa8d6946514a4da724ad277dacb629e9e7521ed22e0d4000140add30b99d72f816470e955947adf8128ae844d8908a3348c8d53a8a36be57dfb861aaeb4e4acd6a5451bb5652f362eeba33b76335e3e839c5db6a4f2e391d3059fe2881da44810c82b349f3a87ef14bfd45a5caad2af3a3fbc50fcc459dc83e73ca3c252d158d8c53949d192518a72937c125b4f0cc3e9f54e14e9f9e9c29c29ce2b918dec929277a6e59acb6f0e07e97657e3f43e1710ad5b0d46afb74e32f7b46e559f0fce3a4f4ebad4e54d72918b518d9d4d68da328b4adbf9aad7bdac8a43f44637c99e89abff006ce9bffc552a535dc9d8a6c4f91cc0cb9988c553e8d6a535ef8dcd4f50b5e338bc154a4a94aa475556a6aad3cd3d6a6db4a596cd8f6f018ec0ce8727afabfa582a91d592979af8db63e83d731de47e352da98d70d5568de86b5a376f553d7595dbcba4e29f91aa8daffa852567756c128bddb6d533d9bcbd44797bc34633846752293fed1c6f27495f34d7add06e868cbd77465569c6cafcae72a4a3b6eed9f36ed249b6ecad9e5ea2fc8f4a56553483925b1470f187f133a709e4770d0e763312efb5475209f721d41e4ba33054a6eaaaf2ad4d462d41d3a2e6e556f94249f36fd26ac1e1e2e1575e32e51c12a16ab4e1155b958296ba966d6a6bf0cedd9eed84f25ba2a19ca8ceabdfcad59c93ecb9f43a3fecee0b0f6e470b429db7c69c6fde4ea0fcefa27ec8e3f16ff4385aad5edaf35a905f8a47dde84f23b376963312a0b6ba7415e5d4e6f2ee47b0104bea8a5d01f65305805fd5e84633b59d5979f55f5c9fc0b7a8f23335d5d8644e076c9bd968dffccfe27c5e33faec1d69d5a917384674dd3ad529f25192bc75145a595d676bbdfc0fb4c17a7d715fe54fe27cee27ec75394e56c4e26187949ca58684a9a866ef28a9eaebc60db7927bf2b1af364fb4b1d5f6271756be1b0b52b3bd47aea526ace7a929454fb5453ed3ebca6d1f4a309d38462a31826a314aca2946c9245c88a000a0726918f9b1f6be0ceb38f49a93a6f56dacaee37d97b3b5fb451c71899a461464ecafb6cafd7bcda606b9c92b5f2be44c567db1f135e329b9472ced7cb7bc8dbbdf678b03662aaea53a93cbcc84e59ecca2de7dc557d9ed2f53154d4e74e34db8b6e119aa9aaf59ab6bc5da5967745869157a15971a5516d4bd07bde4bb4f9dfb0f522e94756d6d49594792507fa57e75a14e0aff00856fbdf69167eadb4afeabf78c3fe6c4b32af4b6ca5fbc61bf3a05a3610062e6b8addbf8ec315523c5676b67c7677946c060aa2f0dcf7ec1aeba777a32dfd8064c8235ba1eee1f3315536766f8efbfc883364182a97b6ccedb1dfd16f807276ff8c9fa37faee28cc1836fa77f0e2b8beb22fd3bf8af59819b20c3e5fb4fd164b5d1ee5c571025c9715f5fc99aab3c8c9bcb6fbd707c0c2b6c7dbc78f481b301b27ed2fcb81b2a186039b2f6bf822be067302304bf4abaa45a15b805fa4fc2fc51646a0000a06bad0d64cd800a09274e7ab2e6b7e6cba78337d1ab1939453bb834a593c9b49aebc99618bc32a9169ada54d2bd39ea4f7e509facb727d3b7accd83b0c5efecf1664998cbe5e2c831d216e4aa26d2d6a7512c949b7a8f645f3ba8f95fb1b5572549c1a506e4941475528bab2564afb17c0fa8d25808578a8cb27196bc25b756766af6d8d59b567c4e5d1ba2151504a34a10a6bcc8518ca31be79e6dd979ced15c7a8cbaf99e7377e51a5b653fde30df9f02caa2e6f5bf572f35f1f87ccaed2dcd87fefc37e7c0b19abeae57b3e09dbcd7c7615c9ae1bb6fa1fddaf45f0f87611096cf3b753f4d677bf059dfde65056b6d5cc5fab5b9e597c3b0884b679dba9fa51df7e0b7fbc8222f6677ca1b2737bdfd74ef09747a9e8cf8be3f4b7ee328bd99df287a527bdf47f3de4456cfc1ba7c5f1fae3b8088c7665eafa296f7c5ff211ddd51caf05b9f0fae1bc9847665eafa296cbf4ff0022545e5b564bd55e8be1d8518a7b33be4bd26fd17c17d6d21adb971daa4fd15c5fd75dc9725be4bb67fb36f1f998caa473f396fdd296e4860c9acf66f7ba3eb2e3f5ee09f4efe2b8be060eaadca4faa1d37de8728f7426fadc52dfd2065b5767ed3f47a497b7b7a3d631d59bf423f8a6dfc0c951a9c60baa2fe2ca31be5b776e6fd5e84615f63eddcf8b37ac3bdf3976597c09fbbc77ddf5b6fc408c1af35fb52f97c0999b29acbb65e2cd73033c073dfb3f145815fa3b9d2ea5e2581a80002800001cb8dc246a45a68ea005072938a953bae512f32524da9aea5bd2dc766dee5e26ec7e0d545c1acd35b53e28af589d4b42a6527e6a767ab2e0efb15f819b058d8868cec44919570e2a872968ded69426bae13525e0744a3276ba85d66af77676b65decacc769dc2d0a94e153114d54a93508538bd7a9293d8942377db6b1d5fd249f369d497e1515ef772e0dea125b3515ac95a2f24b66f1a953d74baa273fdeeabd9452e994fe0905f7997f771ea8b7e2cb83a39296f9bee8fc88fbbbdf39f7dbc0d4b075dedad25ecc60be04ff0044c9f3aa547d0ea4eddd7b0c466f0d15b5cbf149fccd727878ed952ed945b338e83a7be29f5ab9be1a2a9af45770c1c7f7ec3ad924fd98ca5e083d254f746a4baa0d78d8b28e060b719ac347817054ff0048bdd46a76ea2f88fbed57b2877d4ff8972a8c7813a8b8219052f2f887b29d35d6e4fe41fde9ece497e093fe22ef5512320f27c362bed54eb53854c3d1a54dd482ab5211c3b8469eb2d792bcdb795ed95cfbbfb8e21feba5d9182f817a0a292382c4256e5a5db1837e0650c055bf9d524fb97822e401a30d47551bc00000000000000073e270919ab3573a001533d14dbfed2a5b82a934bc4d75340539c651a8b5e324e32536e4a516acd3bf417400f93d0fe4eb45e12ac6b51c2a5562f5a32955af5752566af1539b49e6f3b1f511a115b8d800c5417032000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000fffd9, 'Microtek Inverter', 2000);
/*!40000 ALTER TABLE `variant_product` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
