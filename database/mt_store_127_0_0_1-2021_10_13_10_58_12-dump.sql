-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: mt-store
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `slug` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `thumbnail` varchar(1023) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkp3pdiluqf6xrokl347xxfcph` (`created_by`),
  KEY `FKjpodyjubh0sfo4f0o6ha779en` (`modified_by`),
  CONSTRAINT `FKjpodyjubh0sfo4f0o6ha779en` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkp3pdiluqf6xrokl347xxfcph` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,'2021-09-18 15:10:53',0,'2021-09-18 15:10:53','','SAMSUNG','SAMSUNG','SAMSUNG','SAMSUNG','samsung','',1,1),(2,'2021-09-18 15:11:17',0,'2021-10-10 13:26:06','','Toshoba','Toshoba','Toshoba','Toshiba','toshiba','',1,1),(3,'2021-09-18 15:11:22',0,'2021-09-18 15:11:22','','LG','LG','LG','LG','lg','',1,1),(4,'2021-10-10 13:24:03',0,'2021-10-10 13:24:03','','Sharp','Sharp','Sharp','Sharp','sharp','',1,1),(5,'2021-10-10 13:24:27',0,'2021-10-10 13:24:27','','TCL','TCL','TCL','TCL','tcl','',1,1),(6,'2021-10-10 13:24:43',0,'2021-10-10 13:24:43','','Casper','Casper','Casper','Casper','casper','',1,1),(7,'2021-10-10 13:25:10',0,'2021-10-10 13:25:10','','AQUA','AQUA','AQUA','AQUA','aqua','',1,1),(8,'2021-10-10 13:25:24',0,'2021-10-10 13:25:24','','HITACHI','HITACHI','HITACHI','HITACHI','hitachi','',1,1),(9,'2021-10-10 13:25:44',0,'2021-10-10 13:25:44','','Electrolux','Electrolux','Electrolux','Electrolux','electrolux','',1,1),(10,'2021-10-10 13:53:12',0,'2021-10-10 13:53:12','','Happy Cook ','Happy Cook ','Happy Cook ','Happy Cook ','happy-cook','',1,1);
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `banner` varchar(1023) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `slug` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `category_parent` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnbi9umnlfmtbpd3kcs8o37ta3` (`created_by`),
  KEY `FKr4nnqkds2l6i2fhc7tsu60xs9` (`modified_by`),
  KEY `FKr1xsxlrwmvkn7l7tvhhhbfk6n` (`category_parent`),
  CONSTRAINT `FKnbi9umnlfmtbpd3kcs8o37ta3` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKr1xsxlrwmvkn7l7tvhhhbfk6n` FOREIGN KEY (`category_parent`) REFERENCES `category` (`id`),
  CONSTRAINT `FKr4nnqkds2l6i2fhc7tsu60xs9` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'2021-09-18 15:11:40',0,'2021-09-18 15:11:40','','','Ti Vi','Ti Vi','Ti Vi','Ti Vi','ti-vi',1,1,NULL),(2,'2021-09-18 15:11:56',0,'2021-09-18 15:11:56','','','T??? l???nh ','T??? l???nh ','T??? l???nh ','T??? l???nh ','tu-lanh',1,1,NULL),(3,'2021-09-18 15:12:09',0,'2021-09-18 15:12:09','','','M??y gi???t','M??y gi???t','M??y gi???t','M??y gi???t','may-giat',1,1,NULL),(4,'2021-09-18 15:12:16',0,'2021-09-18 15:12:16','','','??i???u H??a','??i???u H??a','??i???u H??a','??i???u H??a','dieu-hoa',1,1,NULL),(5,'2021-09-18 15:12:36',0,'2021-09-18 15:12:36','','','M??y l???c n?????c','M??y l???c n?????c','M??y l???c n?????c','M??y l???c n?????c','may-loc-nuoc',1,1,NULL),(6,'2021-10-10 13:53:54',0,'2021-10-10 13:53:54','','','????? gia d???ng','????? gia d???ng','????? gia d???ng','????? gia d???ng','do-gia-dung',1,1,NULL),(7,'2021-10-10 13:54:05',0,'2021-10-10 13:54:05','','','N???i c??m ??i???n','N???i c??m ??i???n','N???i c??m ??i???n','N???i c??m ??i???n','noi-com-dien',1,1,6);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_log`
--

DROP TABLE IF EXISTS `general_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_log` (
  `event_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_host` mediumtext NOT NULL,
  `thread_id` bigint unsigned NOT NULL,
  `server_id` int unsigned NOT NULL,
  `command_type` varchar(64) NOT NULL,
  `argument` mediumblob NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8mb3 COMMENT='General log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_log`
--

LOCK TABLES `general_log` WRITE;
/*!40000 ALTER TABLE `general_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `general_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `link` varchar(1023) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `uploaded_at` datetime DEFAULT NULL,
  `uploaded_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4pgw8w3qp244ujvcs4wowiv3x` (`link`),
  KEY `FK4s1nm09l7ywc77xm5d35ecxyo` (`uploaded_by`),
  CONSTRAINT `FK4s1nm09l7ywc77xm5d35ecxyo` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES ('054f1c91-8360-4be4-a413-d0332a222846','/media/static/054f1c91-8360-4be4-a413-d0332a222846.jpg','file',82194,'jpg','2021-10-13 03:39:05',1),('14856935-a390-4f08-bc9c-4b4211e17ea6','/media/static/14856935-a390-4f08-bc9c-4b4211e17ea6.jpg','file',7765,'jpg','2021-10-10 13:31:21',1),('21d157e5-97cc-46b7-9456-d46da8e7ee3e','/media/static/21d157e5-97cc-46b7-9456-d46da8e7ee3e.jpg','file',42051,'jpg','2021-09-25 14:58:25',1),('30b81a99-66c1-4965-8306-a41d09719291','/media/static/30b81a99-66c1-4965-8306-a41d09719291.jpg','file',85108,'jpg','2021-10-10 13:36:35',1),('38513c59-978d-4f8e-8761-4136c653202d','/media/static/38513c59-978d-4f8e-8761-4136c653202d.jpg','file',247309,'jpg','2021-10-10 13:48:50',1),('3a98befb-6ac7-4819-9e80-70b6e11cfc96','/media/static/3a98befb-6ac7-4819-9e80-70b6e11cfc96.jpg','file',65755,'jpg','2021-10-10 13:55:29',1),('44aca3c7-0364-444d-bad6-4bcc31a44b5d','/media/static/44aca3c7-0364-444d-bad6-4bcc31a44b5d.jpg','file',35890,'jpg','2021-09-25 14:58:07',1),('46c575b3-785e-4063-a3c1-774803a3e656','/media/static/46c575b3-785e-4063-a3c1-774803a3e656.jpg','file',39079,'jpg','2021-10-10 13:31:09',1),('4d9bd196-9f40-404e-bb56-2c70e711d020','/media/static/4d9bd196-9f40-404e-bb56-2c70e711d020.jpg','file',47612,'jpg','2021-09-25 14:58:15',1),('50c780af-eb65-4bbc-ba87-6476d1acf764','/media/static/50c780af-eb65-4bbc-ba87-6476d1acf764.jpg','file',19937,'jpg','2021-10-10 13:55:33',1),('5793a67f-450c-4fb8-9e26-72200c5f5f37','/media/static/5793a67f-450c-4fb8-9e26-72200c5f5f37.jpg','file',94429,'jpg','2021-10-10 13:48:57',1),('5e7f8273-b371-4c3b-bced-843a53e665a7','/media/static/5e7f8273-b371-4c3b-bced-843a53e665a7.jpg','file',21341,'jpg','2021-10-10 13:36:49',1),('5eed2964-112f-49f3-b5b4-17cb7a22a74f','/media/static/5eed2964-112f-49f3-b5b4-17cb7a22a74f.jpg','file',45398,'jpg','2021-09-25 14:58:38',1),('603ccf6a-301e-4686-81c1-db9f8b6822ad','/media/static/603ccf6a-301e-4686-81c1-db9f8b6822ad.jpg','file',256450,'jpg','2021-10-10 13:48:54',1),('61b9a706-1e45-4b56-bc97-8ad688792b96','/media/static/61b9a706-1e45-4b56-bc97-8ad688792b96.jpg','file',29726,'jpg','2021-09-25 14:58:00',1),('63f14776-4f2c-4c80-811e-3b79cc00c8ad','/media/static/63f14776-4f2c-4c80-811e-3b79cc00c8ad.jpg','file',13126,'jpg','2021-10-10 13:31:13',1),('6d041529-8578-4847-aa69-e45101567e71','/media/static/6d041529-8578-4847-aa69-e45101567e71.gif','file',70955,'gif','2021-10-13 03:38:27',1),('78161211-a641-4a9e-961c-8fe2441e82ca','/media/static/78161211-a641-4a9e-961c-8fe2441e82ca.jpg','file',38557,'jpg','2021-09-25 14:58:04',1),('8902e6a7-244a-4820-9de5-1c388feee47c','/media/static/8902e6a7-244a-4820-9de5-1c388feee47c.jpg','file',47612,'jpg','2021-09-25 14:58:09',1),('8c7470b3-59d1-44b6-86a4-b32ad63575f3','/media/static/8c7470b3-59d1-44b6-86a4-b32ad63575f3.jpg','file',87457,'jpg','2021-10-13 03:38:01',1),('9e027dc4-7ac3-44bf-9e92-b8a66a2492c0','/media/static/9e027dc4-7ac3-44bf-9e92-b8a66a2492c0.jpg','file',35694,'jpg','2021-10-10 13:55:36',1),('b6cea029-86bc-4ff4-8272-fdfa3294ca67','/media/static/b6cea029-86bc-4ff4-8272-fdfa3294ca67.jpg','file',37557,'jpg','2021-09-25 14:58:12',1),('babbb6fc-0f11-4a8b-b2a2-f72f32edb728','/media/static/babbb6fc-0f11-4a8b-b2a2-f72f32edb728.jpg','file',37140,'jpg','2021-10-13 03:38:07',1),('bcef2f96-5025-47ff-9e63-09603ddd5b4c','/media/static/bcef2f96-5025-47ff-9e63-09603ddd5b4c.jpg','file',119006,'jpg','2021-10-10 13:49:00',1),('be88cf9d-fc55-4a6b-8089-ce669465b158','/media/static/be88cf9d-fc55-4a6b-8089-ce669465b158.jpg','file',57913,'jpg','2021-09-25 14:58:29',1),('d303d165-1f40-48e6-ab7b-5f017b7ad5fd','/media/static/d303d165-1f40-48e6-ab7b-5f017b7ad5fd.jpg','file',13696,'jpg','2021-10-10 13:31:17',1),('d592a795-10b7-47a5-9c24-e50d1258bf01','/media/static/d592a795-10b7-47a5-9c24-e50d1258bf01.jpg','file',22565,'jpg','2021-10-10 13:36:42',1),('d63ac10b-a130-4a0a-a552-a33576ee4ff1','/media/static/d63ac10b-a130-4a0a-a552-a33576ee4ff1.jpg','file',42792,'jpg','2021-09-25 14:58:20',1),('e2756f81-fa9a-4b25-ba28-00d4b352aeac','/media/static/e2756f81-fa9a-4b25-ba28-00d4b352aeac.jpg','file',54818,'jpg','2021-10-10 13:36:39',1),('faf49690-8c59-4e4a-a063-887fc7312ddf','/media/static/faf49690-8c59-4e4a-a063-887fc7312ddf.jpg','file',22386,'jpg','2021-10-10 13:36:46',1),('fb9b4b86-1c61-4db7-abd8-f2cde052aa6c','/media/static/fb9b4b86-1c61-4db7-abd8-f2cde052aa6c.jpg','file',45272,'jpg','2021-09-25 14:58:33',1);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `price` bigint DEFAULT NULL,
  `quantity` int NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2bol7qso2upaxutq3gpfl87xl` (`created_by`),
  KEY `FKoccmtc1h0bs3508qhh5p9xhhl` (`modified_by`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`),
  CONSTRAINT `FK2bol7qso2upaxutq3gpfl87xl` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKoccmtc1h0bs3508qhh5p9xhhl` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (6,'2021-10-11 16:55:43',0,'2021-10-13 03:17:32',7990000,1,1,1,NULL,6,NULL),(7,'2021-10-11 16:55:50',0,'2021-10-13 03:17:32',4790000,1,1,1,NULL,3,NULL),(8,'2021-10-11 16:55:57',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(9,'2021-10-11 17:47:14',0,'2021-10-13 03:17:32',4790000,1,1,1,NULL,3,NULL),(10,'2021-10-12 14:15:48',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(11,'2021-10-12 14:41:12',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(12,'2021-10-12 14:41:19',0,'2021-10-13 03:17:32',199000999,1,1,1,NULL,1,NULL),(13,'2021-10-12 14:42:55',0,'2021-10-13 03:17:32',490000,1,1,1,NULL,7,NULL),(14,'2021-10-12 14:43:00',0,'2021-10-13 03:17:32',7990000,1,1,1,NULL,6,NULL),(15,'2021-10-12 14:43:41',0,'2021-10-13 03:17:32',7990000,1,1,1,NULL,6,NULL),(16,'2021-10-12 14:44:45',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(17,'2021-10-12 14:46:34',0,'2021-10-13 03:17:32',7990000,1,1,1,NULL,6,NULL),(18,'2021-10-12 14:46:43',0,'2021-10-13 03:17:32',4790000,1,1,1,NULL,3,NULL),(19,'2021-10-12 14:49:22',0,'2021-10-13 03:17:32',5000000,1,1,1,NULL,2,NULL),(20,'2021-10-12 14:52:18',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(21,'2021-10-12 14:52:27',0,'2021-10-13 03:17:32',490000,1,1,1,NULL,7,NULL),(22,'2021-10-12 14:54:16',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(23,'2021-10-12 14:54:49',0,'2021-10-13 03:17:32',490000,1,1,1,NULL,7,NULL),(24,'2021-10-12 18:15:46',0,'2021-10-13 03:17:32',21400000,1,1,1,NULL,4,NULL),(25,'2021-10-12 18:20:44',0,'2021-10-13 03:17:32',8690000,1,1,1,NULL,5,NULL),(26,'2021-10-12 18:27:31',0,'2021-10-13 03:17:32',199000999,1,1,1,NULL,1,NULL),(27,'2021-10-12 18:28:19',0,'2021-10-13 03:17:32',7990000,1,1,1,NULL,6,NULL),(28,'2021-10-12 19:02:40',0,'2021-10-13 03:17:32',199000999,1,1,1,NULL,1,NULL),(29,'2021-10-13 02:30:12',0,'2021-10-13 03:17:32',21400000,1,1,1,NULL,4,NULL),(30,'2021-10-13 02:32:21',0,'2021-10-13 03:17:32',NULL,1,1,1,NULL,4,NULL),(31,'2021-10-13 02:34:26',0,'2021-10-13 03:17:32',NULL,1,1,1,NULL,3,NULL),(32,'2021-10-13 02:34:50',0,'2021-10-13 03:17:32',NULL,1,1,1,NULL,6,NULL),(33,'2021-10-13 03:18:11',0,'2021-10-13 03:18:11',NULL,1,1,1,NULL,6,1),(34,'2021-10-13 03:45:53',0,'2021-10-13 03:45:53',NULL,1,1,1,NULL,3,1);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_buy_at_store` tinyint(1) NOT NULL DEFAULT '1',
  `note` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `receiver_address` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `receiver_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `shipping_fee` bigint DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `total_amount` bigint DEFAULT NULL,
  `total_discount` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `already_paid` tinyint(1) DEFAULT '0',
  `transaction_code_momo` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtjwuphstqm46uffgc7l1r27a9` (`created_by`),
  KEY `FKe0abpy849bl2ynw3468ksavvl` (`modified_by`),
  KEY `FKsjfs85qf6vmcurlx43cnc16gy` (`customer_id`),
  CONSTRAINT `FKe0abpy849bl2ynw3468ksavvl` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsjfs85qf6vmcurlx43cnc16gy` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtjwuphstqm46uffgc7l1r27a9` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `expiration` datetime NOT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `number_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `otp` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `total_request` int DEFAULT NULL,
  `transaction` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hq92gy5i1f3egdtv311tqqgk` (`created_by`),
  CONSTRAINT `FK9hq92gy5i1f3egdtv311tqqgk` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp`
--

LOCK TABLES `otp` WRITE;
/*!40000 ALTER TABLE `otp` DISABLE KEYS */;
INSERT INTO `otp` VALUES (9,'2021-09-23 07:44:42','Linhty1802@gmail.com','2021-09-23 07:49:42',0,NULL,'$2a$10$W7FVz1bqYxC25ofZm9IMbey6qmRY1TxHlMmZKFLp9n3cu0Yu1j8mO',0,'FORGOT_PASSWORD',NULL),(10,'2021-09-23 07:46:52','Linhty1802@gmail.com','2021-09-23 07:51:52',1,NULL,'$2a$10$qGwoxwaRowTxQZYLqTJxeOVlnvfL6Ceylo41nmUJMxk7L8lv1KtU2',1,'FORGOT_PASSWORD',NULL),(11,'2021-09-23 07:49:04','Linhty1802@gmail.com','2021-09-23 07:54:04',0,NULL,'$2a$10$qPfmAF49IraTZ8jCvmLu5u9W66Q9I7I94WWC5KKCjAR.dttGThCAS',26,'FORGOT_PASSWORD',NULL),(12,'2021-09-23 07:51:02','Linhty1802@gmail.com','2021-09-23 07:56:02',1,NULL,'$2a$10$NpHdltlH02p6rmwoLzyZbuwSA7bjnES.1JcTtFVKNdzPLcBapiT8G',2,'FORGOT_PASSWORD',NULL),(13,'2021-09-23 07:52:43','LINHTY1802@gmail.com','2021-09-23 07:57:43',0,NULL,'$2a$10$J2orn61Olb7mr06x4lWOiOgxR/Tlo4BI6K9QuLxLUpg12OTTGC7nC',0,'FORGOT_PASSWORD',NULL);
/*!40000 ALTER TABLE `otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `slug` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `thumbnail` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3b6cq7u0x3fdxeh4sq95mia29` (`created_by`),
  KEY `FKl2q2idcap1gt3qhh87ebirpnc` (`modified_by`),
  CONSTRAINT `FK3b6cq7u0x3fdxeh4sq95mia29` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKl2q2idcap1gt3qhh87ebirpnc` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `cover_image` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `dimension_height` int DEFAULT NULL,
  `dimension_length` int DEFAULT NULL,
  `dimension_weight` int DEFAULT NULL,
  `dimension_width` int DEFAULT NULL,
  `is_available` tinyint(1) NOT NULL DEFAULT '1',
  `meta_description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_keyword` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `meta_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `pre_order` int DEFAULT NULL,
  `price_first` bigint DEFAULT NULL,
  `price_final` bigint NOT NULL,
  `quantity` int NOT NULL,
  `salient_features` json DEFAULT NULL,
  `sku` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `slug` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `warranty_period` int DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `brand_id` bigint NOT NULL,
  `product_images` json DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_h3w5r1mx6d0e5c6um32dgyjej` (`code`),
  UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`name`),
  UNIQUE KEY `UK_88yb4l9100epddqsrdvxerhq9` (`slug`),
  KEY `FKstb290bdq1jf21dnnc91ap27p` (`created_by`),
  KEY `FKkgp7jev4m8l8e7a7t5wrv793x` (`modified_by`),
  KEY `FKs6cydsualtsrprvlf2bb3lcam` (`brand_id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKkgp7jev4m8l8e7a7t5wrv793x` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKs6cydsualtsrprvlf2bb3lcam` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `FKstb290bdq1jf21dnnc91ap27p` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'2021-09-25 14:59:19',0,'2021-10-13 03:46:21','058ca20e-30b0-4662-a4ab-215df75f38b4','/media/static/054f1c91-8360-4be4-a413-d0332a222846.jpg','<div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">S???c n??t - ch??n th???c h??n v???i ch???t l?????ng 8K</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">Android Tivi Sony 8K 85 inch KD-85Z8H??</span>s??? h???u m??n ???nh r???ng 8K c??ng c??ng ngh??? TV Full Array LED cho s???c ????? m??u h??i ho??, s???c n??t v?? ch??n th???c h??n. B???ng c??ch??chi???u s??ng ri??ng t???ng v??ng ????n LED, c??ng ngh??? Full Array LED ??em ?????n cho TV Sony ????? t????ng ph???n ch??n th???c h??n nh??? t??ng ????? t????ng ph???n, t??ng c?????ng th??m nh??? 8K X-tended Dynamic Range??? PRO ??i???u ch???nh ????? s??ng cho v??ng s??ng r???c r??? h??n v?? v??ng t???i s??u th???m h??n.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://adm.nguyenkim.com/images/companies/_1/Content/dien-tu/tivi/sony/android-tivi-sony-8k-85-inch-kd-85z80h-1.jpg\" caption=\"false\" alt=\"Android Tivi Sony 8K 85 inch KD-85Z8H ch???t l?????ng 8K\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"NkPdp_productFeature\" style=\"margin: 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><div class=\"productFeature_content\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">H??? th???ng loa ???????c l???p v??o khung vi???n cho tr???i nghi???m ?????m ch??m h??n</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\">??i???u ?????c bi???t c???a TV Sony ch??nh l?? thi???t k??? h??? th???ng??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">loa Acoustic Multi-Audio??? ???????c g???n v??o khung vi???n cho ??m thanh ph??t ra t??? m???i ph??a, h??i ho?? v??? m???t h??nh ???nh l???n ??m thanh, mang l???i tr???i nghi???m ch??n th???c v?? ?????m ch??m</span>. S??? k???t h???p gi???a ??m thanh v??m??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">Dolby Atmos???</span>??c??ng ????? r???ng m??n h??nh ?????n 85 inch v?? nh???ng c??ng ngh??? h??nh ???nh b???c nh???t th??? tr?????ng, Android Tivi Sony 8K 85 inch KD-85Z8H ????ch th???c s??? ??em c??? r???p chi???u phim v??o ng??i nh?? c???a b???n, cho b???n tho??? s???c t???n h?????ng th??? gi???i gi???i tr?? ?????y m??u s???c.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://adm.nguyenkim.com/images/companies/_1/Content/dien-tu/tivi/sony/android-tivi-sony-8k-85-inch-kd-85z80h-2.jpg\" caption=\"false\" alt=\"Android Tivi Sony 8K 85 inch KD-85Z8H h??? th???ng loa ???????c l???p v??o khung vi???n\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"NkPdp_productFeature\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; clear: both; width: 1200px;\"><div class=\"productFeature_content\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">T??? ?????ng ??i???u ch???nh theo m??i tr?????ng</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">C??ng ngh??? t??? ?????ng nh???n di???n v?? t???i ??u h??a h??nh ???nh v?? ??m th??nh theo m??i tr?????ng xung quanh c???a Sony s??? mang ?????n cho b???n nh???ng tr???i nghi???m ch???t l?????ng nh???t</span>. Kh??? n??ng c???m bi???n ??nh s??ng v?? t???i ??u ho?? m??u s???c tu??? v??o ????? s??ng c???a m??i tr?????ng mang ?????n n??t ri??ng c???a chi???c Android TV??Sony 8K 85 inch KD-85Z8H.</article></div></div></div></div></div></div></div>',NULL,NULL,NULL,NULL,1,'Android Tivi Sony 8K 85 inch KD-85Z8H','Android Tivi Sony 8K 85 inch KD-85Z8H','Android Tivi Sony 8K 85 inch KD-85Z8H','Android Tivi Sony 8K 85 inch KD-85Z8H',NULL,2090000,1990009,8,'[\" M??n h??nh r???ng 85 inch ch???t l?????ng 8K cho h??nh ???nh tuy???t ?????p\", \" C??ng ngh??? Full Array LED c???a Sony ??i???u ch???nh ??nh s??ng r???c r???\", \" T??i t???o m??u ch??n th???c h??n v???i c??ng ngh??? hi???n th??? TRILUMINOS\", \" C??ng ngh??? S-Force Front Surround t???n h?????ng ??m thanh ??a chi???u\", \" Netflix tr??n remote gi??p th?????ng th???c nh???ng b??? phim h???p d???n\", \"??i???u khi???n t??? xa c?? ????n n???n nh??n ???????c r?? ngay trong b??ng t???i\", \" T??ch h???p micro tr??n remote ??i???u khi???n b???ng gi???ng n??i th??ng minh\"]','','android-tivi-sony-8k-85-inch-kd-85z8h',NULL,1,1,1,'[\"/media/static/6d041529-8578-4847-aa69-e45101567e71.gif\", \"/media/static/babbb6fc-0f11-4a8b-b2a2-f72f32edb728.jpg\", \"/media/static/054f1c91-8360-4be4-a413-d0332a222846.jpg\"]',1),(2,'2021-09-25 16:00:17',0,'2021-10-13 03:42:55','a19b9527-fdb2-4e03-89e3-99bb93b80a0a','/media/static/30b81a99-66c1-4965-8306-a41d09719291.jpg','<div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">T??? l???nh ng??n ???? tr??n c?? thi???t k??? nh??? g???n sang tr???ng</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG)</span>??-??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/?sort_by=position&sort_order=desc&features_hash=82-10311\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh ng??n ???? tr??n\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh ng??n ???? tr??n</a></span>??c?? thi???t k??? sang tr???ng, ???????ng n??t tinh t??? gi??p??l??m n???i b???t kh??ng gian b???p c???a b???n. Nh??? thi???t k??? nh??? g???n, b???n d??? d??ng b??? tr?? t??? l???nh ??? nhi???u v??? tr?? trong nh??,??t??? ph??ng kh??ch ?????n ph??ng b???p. V???i dung t??ch 180 l??t, chi???c t??? l???nh l?? s??? l???a ch???n ph?? h???p cho gia ????nh c?? kho???ng 2 -3 th??nh vi??n.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-thiet-ke-nho-gon.jpg\" caption=\"false\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Thi???t k??? nh??? g???n\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">C??ng ngh??? Inverter gi??p t??? l???nh v???n h??nh ??m ??i, ti???t ki???m ??i???n n??ng</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG)</span>??trang b?????c??ng ngh??? bi???n t???n Inverter gi??p t??? l???nh v???n h??nh ??m ??i, b???n b??? v?? ti???t ki???m ??i???n hi???u qu???. C??c b?? n???i tr??? t??? nay s??? gi???m b???t n???i lo v??? h??a ????n ti???n ??i???n m???i th??ng khi s??? d???ng chi???c??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/?features_hash=177-6726-181129-181336-181131-190936-185756-163002-168197-197598-174625-185530-174161\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh Inverter\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">t??? l???nh Inverter</a></span>??n??y.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-tiet-kiem-dien-nang.jpg\" caption=\"false\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - ti???t ki???m\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">H??? th???ng kh?? l???nh v??ng cung b???o qu???n th???c ph???m to??n di???n</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\"><p style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">C??ng ngh??? l??m l???nh th???c ph???m b???ng lu???ng kh?? v??ng cung gi??p h??i l???nh ???????c l??u chuy???n ?????n m???i ng??c ng??ch trong??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">t??? l???nh</a></span>???????m b???o th???c ph???m ???????c l??m l???nh ?????ng ?????u, gi??? ???????c ????? t????i ngon.??</p></article></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - H??? th???ng kh??ng kh?? l???nh v??ng cung\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-He-thong-khong-khi-lanh-vong-cung.jpg\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div></div><div class=\"featureContent featureContent_2col feature_img-txt\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - C??ng ngh??? Ag+ Bio\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-Cong-nghe-Ag%2B-Bio.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; width: 420px;\">C??ng ngh??? Ag Bio?? kh??ng khu???n kh??? m??i t???i ??u</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px;\">H??? th???ng m??ng l???c Ag Bio tr??n??<a href=\"https://www.nguyenkim.com/tu-lanh-toshiba/\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"T??? l???nh Toshiba\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh Toshiba</a>??gi??p??kh??ng khu???n v?? kh??? m??i t???i ??a, lo???i b??? nh???ng m??i kh?? ch???u nh?? th???t v?? c??. ?????ng th???i??b??? l???c sinh h???c g??c Aldehyde kh??? c???c nhanh c??c nh??m th???c ph???m l??n men hi???u qu???.</article></div></div></div><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">Ti???t ki???m th???i gian ch??? bi???n v???i ng??n c???p ????ng m???m</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh-toshiba/?features_hash=177-6726-181131-185756-168197-185530\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"T??? l???nh Toshiba Inverter\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh Toshiba Inverter</a></span>??trang b??? ng??n c???p ????ng m???m??Ultra Cooling Zone gi??p l??u tr??? th???c ph???m t????i d??ng trong ng??y. B???n c?? th??? l???y th???c ph???m t????i s???ng ra ch??? bi???n ngay kh??ng c???n t???n th???i gian ch??? r?? ????ng m?? v???n ?????m b???o ???????c ch???t l?????ng m??n ??n.</article></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Ng??n c???p ????ng m???m\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-ngan-cap-dong-mem.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div></div><div class=\"featureContent featureContent_2col feature_img-txt\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Ng??n ?????ng rau c??? qu??? l???n\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-Ngan-dung-rau-cu-qua-lon.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; width: 420px;\">Ng??n ?????ng rau c??? qu??? l???n</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px;\">T??? l???nh Toshiba GR-B22VU (UKG)??thi???t k??? ng??n ?????ng rau c??? l???n cho ph??p b???n d??? tr??? ???????c nhi???u rau c???. Ng??n ch???a c?? kh??? n??ng gi??? ???m t???t??gi??p rau c??? gi??? ???????c ????? t????i ngon l??u h??n.</article></div></div></div><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">Khay k??nh ch???u l???c an to??n b???n b???</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\">T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) thi???t k??? c??c khay k??nh ch???u l???c t???t, an to??n b???n b??? ????? b???n y??n t??m tr??? th???c ph???m.??</article></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - V???n h??nh ??m ??i\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-van-hanh-em-ai.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div></div><div class=\"featureContent featureContent_1col feature_txt\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"></span></h3><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">Kh???c ph???c t??nh tr???ng t??? l???nh h??? g??y t???n ??i???n</span></h3><article class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><p style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: left;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">C?? r???t nhi???u l?? do khi???n t??? l???nh c???a gia ????nh b???n b??? h??? g??y t???n ??i???n nh??: ?????t qu?? nhi???u ????? tr??n th??nh t??? l???nh, t??? l???nh kh??ng c??n b???ng, b???n l??? kh??ng th???ng,...</span></p></article></div></div></div>',NULL,NULL,NULL,NULL,1,'T??? l???nh Sharp Inverter 287 l??t SJ-X316E-SL','T??? l???nh Sharp Inverter 287 l??t SJ-X316E-SL','T??? l???nh Sharp Inverter 287 l??t SJ-X316E-SL','T??? l???nh Sharp Inverter 287 l??t SJ-X316E-SL',NULL,1000505,500000,3,'[\"Ng??n c???p ????ng m???m b???o qu???n th???c ph???m kh??ng c???n r?? ????ng\"]','','tu-lanh-sharp-inverter-287-lit-sj-x316e-sl',NULL,1,1,1,'[\"/media/static/46c575b3-785e-4063-a3c1-774803a3e656.jpg\", \"/media/static/d592a795-10b7-47a5-9c24-e50d1258bf01.jpg\"]',2),(3,'2021-10-10 13:31:42',0,'2021-10-13 03:46:40','48ecf6e7-c156-44d9-a3ea-fa8d5df90f5a','/media/static/14856935-a390-4f08-bc9c-4b4211e17ea6.jpg','<div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">T??? l???nh ng??n ???? tr??n c?? thi???t k??? nh??? g???n sang tr???ng</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG)</span>??-??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/?sort_by=position&sort_order=desc&features_hash=82-10311\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh ng??n ???? tr??n\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh ng??n ???? tr??n</a></span>??c?? thi???t k??? sang tr???ng, ???????ng n??t tinh t??? gi??p??l??m n???i b???t kh??ng gian b???p c???a b???n. Nh??? thi???t k??? nh??? g???n, b???n d??? d??ng b??? tr?? t??? l???nh ??? nhi???u v??? tr?? trong nh??,??t??? ph??ng kh??ch ?????n ph??ng b???p. V???i dung t??ch 180 l??t, chi???c t??? l???nh l?? s??? l???a ch???n ph?? h???p cho gia ????nh c?? kho???ng 2 -3 th??nh vi??n.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-thiet-ke-nho-gon.jpg\" caption=\"false\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Thi???t k??? nh??? g???n\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"featureContent featureContent_1col featureContent_1_image\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; width: 1200px;\"><h3 class=\"featureContent_title\" style=\"margin-right: auto; margin-bottom: 0px; margin-left: auto; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: center; width: 840px;\">C??ng ngh??? Inverter gi??p t??? l???nh v???n h??nh ??m ??i, ti???t ki???m ??i???n n??ng</h3><p class=\"featureContent_caption\" style=\"margin: 20px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); text-align: center; width: 840px;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG)</span>??trang b?????c??ng ngh??? bi???n t???n Inverter gi??p t??? l???nh v???n h??nh ??m ??i, b???n b??? v?? ti???t ki???m ??i???n hi???u qu???. C??c b?? n???i tr??? t??? nay s??? gi???m b???t n???i lo v??? h??a ????n ti???n ??i???n m???i th??ng khi s??? d???ng chi???c??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/?features_hash=177-6726-181129-181336-181131-190936-185756-163002-168197-197598-174625-185530-174161\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh Inverter\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">t??? l???nh Inverter</a></span>??n??y.</p><img loading=\"lazy\" class=\"imagelazyload\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-tiet-kiem-dien-nang.jpg\" caption=\"false\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - ti???t ki???m\" style=\"margin: 20px 0px 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 1200px;\"></div><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">H??? th???ng kh?? l???nh v??ng cung b???o qu???n th???c ph???m to??n di???n</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\"><p style=\"margin-right: 0px; margin-bottom: 0px; margin-left: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\">C??ng ngh??? l??m l???nh th???c ph???m b???ng lu???ng kh?? v??ng cung gi??p h??i l???nh ???????c l??u chuy???n ?????n m???i ng??c ng??ch trong??<span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh/\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"t??? l???nh\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">t??? l???nh</a></span>???????m b???o th???c ph???m ???????c l??m l???nh ?????ng ?????u, gi??? ???????c ????? t????i ngon.??</p></article></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - H??? th???ng kh??ng kh?? l???nh v??ng cung\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-He-thong-khong-khi-lanh-vong-cung.jpg\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div></div><div class=\"featureContent featureContent_2col feature_img-txt\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - C??ng ngh??? Ag+ Bio\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-Cong-nghe-Ag%2B-Bio.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; width: 420px;\">C??ng ngh??? Ag Bio?? kh??ng khu???n kh??? m??i t???i ??u</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px;\">H??? th???ng m??ng l???c Ag Bio tr??n??<a href=\"https://www.nguyenkim.com/tu-lanh-toshiba/\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"T??? l???nh Toshiba\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh Toshiba</a>??gi??p??kh??ng khu???n v?? kh??? m??i t???i ??a, lo???i b??? nh???ng m??i kh?? ch???u nh?? th???t v?? c??. ?????ng th???i??b??? l???c sinh h???c g??c Aldehyde kh??? c???c nhanh c??c nh??m th???c ph???m l??n men hi???u qu???.</article></div></div></div><div class=\"featureContent featureContent_2col feature_txt-img\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; text-align: left; width: 420px;\">Ti???t ki???m th???i gian ch??? bi???n v???i ng??n c???p ????ng m???m</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px; text-align: left;\"><span style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><a href=\"https://www.nguyenkim.com/tu-lanh-toshiba/?features_hash=177-6726-181131-185756-168197-185530\" target=\"_blank\" rel=\"noopener noreferrer\" title=\"T??? l???nh Toshiba Inverter\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; color: rgb(237, 51, 36) !important; transition: all 0.2s ease 0s !important;\">T??? l???nh Toshiba Inverter</a></span>??trang b??? ng??n c???p ????ng m???m??Ultra Cooling Zone gi??p l??u tr??? th???c ph???m t????i d??ng trong ng??y. B???n c?? th??? l???y th???c ph???m t????i s???ng ra ch??? bi???n ngay kh??ng c???n t???n th???i gian ch??? r?? ????ng m?? v???n ?????m b???o ???????c ch???t l?????ng m??n ??n.</article></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Ng??n c???p ????ng m???m\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-ngan-cap-dong-mem.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div></div><div class=\"featureContent featureContent_2col feature_img-txt\" style=\"margin: 40px 0px 0px; padding: 0px; border: 0px; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-stretch: inherit; line-height: inherit; font-family: Roboto, sans-serif; vertical-align: baseline; clear: both; display: flex; align-items: center;\"><div class=\"featureContent_col-1\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; text-align: right;\"><img loading=\"lazy\" class=\"img_feature imagelazyload\" alt=\"T??? l???nh Toshiba Inverter 180 l??t GR-B22VU (UKG) - Ng??n ?????ng rau c??? qu??? l???n\" src=\"https://cdn.nguyenkimmall.com/images/companies/_1/Content/dien-lanh/tu-lanh/toshiba/tu-lanh-toshiba-inverter-180-lit-gr-b22vu-ukg-Ngan-dung-rau-cu-qua-lon.png\" caption=\"false\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"></div></div><div class=\"featureContent_col-2\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline; width: 600px;\"><div class=\"featureContent_col_wrap\" style=\"margin: 0px; padding: 0px; font: inherit; border: 0px; vertical-align: baseline;\"><h3 class=\"featureContent_title\" style=\"margin: 30px auto 0px; padding: 0px; font-size: 36px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; vertical-align: baseline; width: 420px;\">Ng??n ?????ng rau c??? qu??? l???n</h3><article class=\"featureContent_caption\" style=\"margin: 30px auto 0px; padding: 0px; border: 0px; font-style: inherit; font-variant: inherit; font-weight: inherit; font-stretch: inherit; line-height: 1.5; font-family: inherit; vertical-align: baseline; color: rgb(25, 25, 25); width: 420px;\">T??? l???nh Toshiba GR-B22VU (UKG)??thi???t k??? ng??n ?????ng rau c??? l???n cho ph??p b???n d??? tr??? ???????c nhi???u rau c???. Ng??n ch???a c?? kh??? n??ng gi??? ???m t???t??gi??p rau c??? gi??? ???????c ????? t????i ngon l??u h??n.</article></div></div></div>',NULL,NULL,NULL,NULL,1,'T??? l???nh Casper Inverter 218 l??t RT-230PB','T??? l???nh Casper Inverter 218 l??t RT-230PB','T??? l???nh Casper Inverter 218 l??t RT-230PB','T??? l???nh Casper Inverter 218 l??t RT-230PB',NULL,699000,479000,20,'[\"Ng??n c???p ????ng m???m b???o qu???n th???c ph???m kh??ng c???n r?? ????ng\"]','','tu-lanh-casper-inverter-218-lit-rt-230pb',NULL,1,1,6,'[\"/media/static/d303d165-1f40-48e6-ab7b-5f017b7ad5fd.jpg\", \"/media/static/63f14776-4f2c-4c80-811e-3b79cc00c8ad.jpg\", \"/media/static/46c575b3-785e-4063-a3c1-774803a3e656.jpg\"]',2),(4,'2021-10-10 13:37:09',0,'2021-10-13 03:46:49','8a258377-407e-4fa8-a11d-309c06d11f85','/media/static/5e7f8273-b371-4c3b-bced-843a53e665a7.jpg','',NULL,NULL,NULL,NULL,1,'T??? l???nh Casper Inverter 645 l??t RM-680VBW','T??? l???nh Casper Inverter 645 l??t RM-680VBW','T??? l???nh Casper Inverter 645 l??t RM-680VBW','T??? l???nh Casper Inverter 645 l??t RM-680VBW',NULL,3299000,2140000,3,'[\"\"]','','tu-lanh-casper-inverter-645-lit-rm-680vbw',NULL,1,1,6,'[\"/media/static/d592a795-10b7-47a5-9c24-e50d1258bf01.jpg\", \"/media/static/e2756f81-fa9a-4b25-ba28-00d4b352aeac.jpg\", \"/media/static/30b81a99-66c1-4965-8306-a41d09719291.jpg\"]',2),(5,'2021-10-10 13:49:17',0,'2021-10-13 03:46:55','cc128171-5c85-4c8a-bfab-0948baeb5c14','/media/static/603ccf6a-301e-4686-81c1-db9f8b6822ad.jpg','',NULL,NULL,NULL,NULL,1,'M??y gi???t Electrolux Inverter 9 kg EWF9025BQWA','M??y gi???t Electrolux Inverter 9 kg EWF9025BQWA','M??y gi???t Electrolux Inverter 9 kg EWF9025BQWA','M??y gi???t Electrolux Inverter 9 kg EWF9025BQWA',NULL,1199000,869000,3,'[\"\"]','','may-giat-electrolux-inverter-9-kg-ewf9025bqwa',NULL,1,1,9,'[\"/media/static/bcef2f96-5025-47ff-9e63-09603ddd5b4c.jpg\", \"/media/static/5793a67f-450c-4fb8-9e26-72200c5f5f37.jpg\", \"/media/static/603ccf6a-301e-4686-81c1-db9f8b6822ad.jpg\", \"/media/static/38513c59-978d-4f8e-8761-4136c653202d.jpg\"]',3),(6,'2021-10-10 13:50:38',0,'2021-10-13 03:47:03','c07ceb0f-8974-44b2-9bed-bcf291028f91','/media/static/38513c59-978d-4f8e-8761-4136c653202d.jpg','',NULL,NULL,NULL,NULL,1,'M??y gi???t Electrolux Inverter 8 kg EWF8025BQWA','M??y gi???t Electrolux Inverter 8 kg EWF8025BQWA','M??y gi???t Electrolux Inverter 8 kg EWF8025BQWA','M??y gi???t Electrolux Inverter 8 kg EWF8025BQWA',NULL,999000,799000,9,'[\"\"]','','may-giat-electrolux-inverter-8-kg-ewf8025bqwa',NULL,1,1,9,'[\"/media/static/4d9bd196-9f40-404e-bb56-2c70e711d020.jpg\", \"/media/static/5793a67f-450c-4fb8-9e26-72200c5f5f37.jpg\", \"/media/static/5eed2964-112f-49f3-b5b4-17cb7a22a74f.jpg\", \"/media/static/bcef2f96-5025-47ff-9e63-09603ddd5b4c.jpg\"]',3),(7,'2021-10-10 13:55:49',0,'2021-10-13 03:24:00','eb22e946-4f98-43ab-afb7-edcd1680f0e3','/media/static/9e027dc4-7ac3-44bf-9e92-b8a66a2492c0.jpg','',NULL,NULL,NULL,NULL,1,'N???i c??m ??i???n Happy Cook 0.6 l??t HC-060 Xanh','N???i c??m ??i???n Happy Cook 0.6 l??t HC-060 Xanh','N???i c??m ??i???n Happy Cook 0.6 l??t HC-060 Xanh','N???i c??m ??i???n Happy Cook 0.6 l??t HC-060 Xanh',NULL,549996,490000,12,'[\"N???i c??m ??i???n Happy Cook 0.6 l??t HC-060 Xanh\"]','','noi-com-dien-happy-cook-06-lit-hc-060-xanh',NULL,1,1,10,'[\"/media/static/50c780af-eb65-4bbc-ba87-6476d1acf764.jpg\", \"/media/static/3a98befb-6ac7-4819-9e80-70b6e11cfc96.jpg\"]',7);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_specifications`
--

DROP TABLE IF EXISTS `product_specifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_specifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `specification_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkgg2cl4fgmq99948ytgic1kib` (`created_by`),
  KEY `FKsxc8auyny9srgd8flwqlusxyo` (`modified_by`),
  KEY `FKh8b0x1853mrmmmv4xqjibuead` (`product_id`),
  KEY `FK2uif28l7kenf28wox5akb4w56` (`specification_id`),
  CONSTRAINT `FK2uif28l7kenf28wox5akb4w56` FOREIGN KEY (`specification_id`) REFERENCES `specifications` (`id`),
  CONSTRAINT `FKh8b0x1853mrmmmv4xqjibuead` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKkgg2cl4fgmq99948ytgic1kib` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKsxc8auyny9srgd8flwqlusxyo` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_specifications`
--

LOCK TABLES `product_specifications` WRITE;
/*!40000 ALTER TABLE `product_specifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_specifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `same_product`
--

DROP TABLE IF EXISTS `same_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `same_product` (
  `product1_id` bigint NOT NULL,
  `product2_id` bigint NOT NULL,
  PRIMARY KEY (`product1_id`,`product2_id`),
  KEY `FKd78807ii886ovfql54w8fba2m` (`product2_id`),
  CONSTRAINT `FKcvjdfv9u0nmt146s9fr49ljim` FOREIGN KEY (`product1_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKd78807ii886ovfql54w8fba2m` FOREIGN KEY (`product2_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `same_product`
--

LOCK TABLES `same_product` WRITE;
/*!40000 ALTER TABLE `same_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `same_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slow_log`
--

DROP TABLE IF EXISTS `slow_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slow_log` (
  `start_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_host` mediumtext NOT NULL,
  `query_time` time(6) NOT NULL,
  `lock_time` time(6) NOT NULL,
  `rows_sent` int NOT NULL,
  `rows_examined` int NOT NULL,
  `db` varchar(512) NOT NULL,
  `last_insert_id` int NOT NULL,
  `insert_id` int NOT NULL,
  `server_id` int unsigned NOT NULL,
  `sql_text` mediumblob NOT NULL,
  `thread_id` bigint unsigned NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8mb3 COMMENT='Slow log';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slow_log`
--

LOCK TABLES `slow_log` WRITE;
/*!40000 ALTER TABLE `slow_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `slow_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specifications`
--

DROP TABLE IF EXISTS `specifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(2047) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg01sveds9byc48t55m7fl6ppx` (`created_by`),
  KEY `FKcxrgrga4wtwwmt2vheyobfw21` (`modified_by`),
  CONSTRAINT `FKcxrgrga4wtwwmt2vheyobfw21` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKg01sveds9byc48t55m7fl6ppx` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specifications`
--

LOCK TABLES `specifications` WRITE;
/*!40000 ALTER TABLE `specifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `specifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address` varchar(511) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `full_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `gender` int NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `roles` json NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_du5v5sr43g5bfnji4vb8hg5s3` (`phone`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKibk1e3kaxy5sfyeekp8hbhnim` (`created_by`),
  KEY `FK3aqdj6u2t0by2dj24m141utm` (`modified_by`),
  CONSTRAINT `FK3aqdj6u2t0by2dj24m141utm` FOREIGN KEY (`modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKibk1e3kaxy5sfyeekp8hbhnim` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2021-09-18 10:21:35',0,'2021-09-23 07:51:29','Thanh Hoa','linhty1802@gmail.com','TuanNQ',0,'$2a$10$2lsjoUG3G8xtW2FB/IOLXe7w42soSn7uuXjYwBHSoRWD2.S7lPXSq','0919234567','[\"ADMIN\", \"USER\"]',NULL,NULL),(3,'2021-09-18 11:07:17',0,'2021-10-13 03:19:23','HA NOi','Linhsk3@gmail.com','?????nh Th??? Ph????ng Dung',1,'$2a$10$ArPBDuyouJVCv2S0MmQM6.Z2M/RPiFaK6Bd74P.ZWRTo2dY6ALoO6','0837207158','[\"USER\"]',NULL,NULL),(4,'2021-09-18 12:01:22',0,'2021-10-13 03:20:10','HANOI','linhsk7@gmail.com','B??i Quang Ninh',1,'$2a$10$GpW0ZSy4RXZ69NHMrlIiVORpJ2zVEZXywkLfMn0ZVWjtbkNwIKV6i','0123456789','[\"ADMIN\", \"USER\"]',1,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-13 10:58:12
