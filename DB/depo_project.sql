/*
 Navicat Premium Data Transfer

 Source Server         : local-Mysql
 Source Server Type    : MySQL
 Source Server Version : 100420
 Source Host           : localhost:3306
 Source Schema         : depo_project

 Target Server Type    : MySQL
 Target Server Version : 100420
 File Encoding         : 65001

 Date: 04/09/2021 11:12:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `aid` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fullName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'uzun.aydinn@gmail.com', 'Aydın Uzun', '81dc9bdb52d04dc20036dbd8313ed055', 'e846fdaf-2e4f-45a6-8cb7-311321082da2');
INSERT INTO `admin` VALUES (2, 'ismet@mail.com', 'İsmet Bilmem', '202cb962ac59075b964b07152d234b70', 'e329f5ae-89ce-4e76-8873-b34f0aac038c');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cu_id` int NOT NULL AUTO_INCREMENT,
  `cu_address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_code` bigint NULL DEFAULT NULL,
  `cu_company_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_email` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_isActive` bit(1) NOT NULL,
  `cu_mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_status` int NOT NULL,
  `cu_surname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_tax_administration` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cu_tax_number` int NOT NULL,
  PRIMARY KEY (`cu_id`) USING BTREE,
  UNIQUE INDEX `UK_1j6q0dsu86iwkwetb32pwbpqd`(`cu_code`) USING BTREE,
  FULLTEXT INDEX `cu_full_text`(`cu_name`, `cu_surname`, `cu_company_title`)
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, 'HACILAR ORG.SAN.BÖL.2.C N 47', 468941539, 'Multi Plaza Şirket', 'uzunaydin@mail.com', b'1', '12213', 'Aydın', 'birmulinay', '2122303281', 2, 'Uzun', '', 11212);
INSERT INTO `customer` VALUES (3, 'Oğuzhan Sokak', 557100684, 'Bilmen Şirket', 'dudele@mail.com', b'0', '5443339944', 'Ali2', 'bilmelik123', '2128824324354', 1, 'bilmem2', '', 43555432);
INSERT INTO `customer` VALUES (4, 'Pendik Caddesi', 557307188, 'AliBilmisler Grup', 'ali3bilmem3@mail.com', b'1', '5413329144', 'Ali', 'ali3bilmem3', '2624449353', 1, 'Balbars', '', 304952343);
INSERT INTO `customer` VALUES (5, 'Bursa Merkez', 557478296, 'Dünya Kütüphane Sirketi', 'enesbaspinar@mail.com', b'1', '4449955', 'Enes', 'kutuphaneLib', '2128826624356', 2, 'Baspinar', '', 443355341);
INSERT INTO `customer` VALUES (7, 'ErzincanMerkez', 561517947, 'Ticarethane', 'evrencell@mail.com', b'1', '13212', 'Ertuğrul', 'evren', '1123', 1, 'Evrensel', 'TicaretDairesi', 123123);
INSERT INTO `customer` VALUES (8, 'ddddd', 561517940, '1212', 'sss22s@mail.com', b'0', '132122', '12', '123', '1123', 1, '132', 'ddd', 1223123);
INSERT INTO `customer` VALUES (9, 'Didem sokak', 829293861, 'Td Mobil', 'ibrahim_soylu@mail.com', b'1', '5344445980', 'İbrahim', 'enes123', '2124446644', 2, 'Soylu', '1111', 1122);
INSERT INTO `customer` VALUES (10, 'İstekli Caddesi', 557307180, 'kartallar', 'tuba_kartal@mail.com', b'1', '5413329144', 'Tuba', 'ali3bilmem3', '2624449353', 1, 'Kartal', '', 304952343);
INSERT INTO `customer` VALUES (11, 'İstekli Caddesi', 557307181, 'AliBal Grup', 'ali_balbars@mail.com', b'1', '5413529900', 'Nesrin', 'alibal123', '2624449353', 1, 'Ulusoy', 'İst Vergi', 304952343);
INSERT INTO `customer` VALUES (12, 'Kadıköy Mahallesi', 862910790, 'Göz Mobil', 'gozzi@mail.com', b'1', '4839548394', 'Gözde', 'mehmet123', '3849282', 2, 'Neyzi', '8329', 738243);
INSERT INTO `customer` VALUES (13, 'Kenanlar caddesi', 77151374, '123123', 'faruk@mail.com', b'1', '123213', 'Faruk', '123123', '4441122', 1, 'Arslan', '123132', 12312312);
INSERT INTO `customer` VALUES (14, 'HACILAR ORG.SAN.BÖL.2.C N 47', 742019184, 'Taşlık Plaza Şirket', 'tasdemir@gmail.com', b'1', '5415734734', 'Yasmina', 'tasmina', '', 1, 'Tasdemir', 'Tasmin dairesi', 34384238);

-- ----------------------------
-- Table structure for depoorder
-- ----------------------------
DROP TABLE IF EXISTS `depoorder`;
CREATE TABLE `depoorder`  (
  `or_id` int NOT NULL,
  `or_quantity` int NOT NULL,
  `order_status` int NOT NULL,
  `fk_cuId` int NULL DEFAULT NULL,
  `fk_prId` int NULL DEFAULT NULL,
  `fk_reId` int NULL DEFAULT NULL,
  PRIMARY KEY (`or_id`) USING BTREE,
  INDEX `FKdf2ij8hkgiftqk3xatw5tl3ud`(`fk_cuId`) USING BTREE,
  INDEX `FK9uva139cbkxyvjnk2lyk9aj4m`(`fk_prId`) USING BTREE,
  INDEX `FKfjhtyp81pc730t3fcw9gucup7`(`fk_reId`) USING BTREE,
  CONSTRAINT `FK9uva139cbkxyvjnk2lyk9aj4m` FOREIGN KEY (`fk_prId`) REFERENCES `product` (`pr_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKdf2ij8hkgiftqk3xatw5tl3ud` FOREIGN KEY (`fk_cuId`) REFERENCES `customer` (`cu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfjhtyp81pc730t3fcw9gucup7` FOREIGN KEY (`fk_reId`) REFERENCES `receipt` (`re_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depoorder
-- ----------------------------
INSERT INTO `depoorder` VALUES (118, 20, 1, 1, 1, 30);
INSERT INTO `depoorder` VALUES (119, 5, 1, 1, 3, 30);
INSERT INTO `depoorder` VALUES (120, 35, 1, 4, 2, 31);
INSERT INTO `depoorder` VALUES (121, 40, 1, 5, 1, 32);
INSERT INTO `depoorder` VALUES (122, 5, 1, 5, 3, 32);
INSERT INTO `depoorder` VALUES (124, 4, 0, 9, 3, 34);
INSERT INTO `depoorder` VALUES (125, 40, 0, 9, 2, 34);
INSERT INTO `depoorder` VALUES (126, 35, 0, 12, 1, 35);
INSERT INTO `depoorder` VALUES (127, 25, 0, 12, 2, 35);
INSERT INTO `depoorder` VALUES (128, 2, 0, 12, 3, 35);
INSERT INTO `depoorder` VALUES (129, 4, 0, 13, 3, 36);
INSERT INTO `depoorder` VALUES (130, 20, 0, 13, 1, 36);
INSERT INTO `depoorder` VALUES (131, 14, 1, 14, 2, 37);
INSERT INTO `depoorder` VALUES (132, 20, 1, 14, 2, 38);
INSERT INTO `depoorder` VALUES (133, 25, 1, 14, 1, 38);
INSERT INTO `depoorder` VALUES (134, 100, 0, 1, 1, 39);
INSERT INTO `depoorder` VALUES (135, 100, 0, 1, 2, 39);
INSERT INTO `depoorder` VALUES (136, 100, 0, 1, 3, 39);
INSERT INTO `depoorder` VALUES (141, 20, 1, 7, 1, 40);
INSERT INTO `depoorder` VALUES (142, 5, 1, 7, 3, 41);
INSERT INTO `depoorder` VALUES (143, 20, 1, 10, 2, 42);
INSERT INTO `depoorder` VALUES (144, 25, 0, 10, 2, 43);
INSERT INTO `depoorder` VALUES (149, 25, 1, 4, 1, 44);
INSERT INTO `depoorder` VALUES (150, 5, 1, 4, 3, 45);
INSERT INTO `depoorder` VALUES (151, 4, 1, 5, 3, 46);
INSERT INTO `depoorder` VALUES (152, 10, 1, 7, 1, 47);
INSERT INTO `depoorder` VALUES (153, 22, 1, 7, 2, 48);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (154);

-- ----------------------------
-- Table structure for paymentin
-- ----------------------------
DROP TABLE IF EXISTS `paymentin`;
CREATE TABLE `paymentin`  (
  `pin_id` int NOT NULL,
  `pin_date` date NULL DEFAULT NULL,
  `pin_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pin_price` int NOT NULL,
  `fk_cuId` int NULL DEFAULT NULL,
  `receipt_number` int NOT NULL,
  PRIMARY KEY (`pin_id`) USING BTREE,
  INDEX `FKrhb55ilibchlhsiegposjnki3`(`fk_cuId`) USING BTREE,
  CONSTRAINT `FKrhb55ilibchlhsiegposjnki3` FOREIGN KEY (`fk_cuId`) REFERENCES `customer` (`cu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paymentin
-- ----------------------------
INSERT INTO `paymentin` VALUES (137, '2021-09-04', 'Ön ödeme', 100, 1, 742182012);
INSERT INTO `paymentin` VALUES (138, '2021-09-01', '1. Taksit', 1000, 1, 742182012);
INSERT INTO `paymentin` VALUES (139, '2021-09-04', 'suların parası', 300, 4, 742203704);
INSERT INTO `paymentin` VALUES (140, '2021-08-12', 'temmuz ayı faturası', 287, 14, 742297067);
INSERT INTO `paymentin` VALUES (145, '2021-09-04', 'pamuk için ödeme', 60, 7, 742551125);
INSERT INTO `paymentin` VALUES (146, '2021-09-04', 'televizyon için ödemenin ilk kısmı', 7500, 1, 742182012);
INSERT INTO `paymentin` VALUES (147, '2021-08-09', 'fatura ödeme', 5500, 7, 742558373);
INSERT INTO `paymentin` VALUES (148, '2021-09-04', 'ödeme bir kısmı', 243, 14, 742307827);

-- ----------------------------
-- Table structure for paymentout
-- ----------------------------
DROP TABLE IF EXISTS `paymentout`;
CREATE TABLE `paymentout`  (
  `pout_id` int NOT NULL AUTO_INCREMENT,
  `pout_date` date NULL DEFAULT NULL,
  `pout_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pout_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pout_paymentType` int NOT NULL,
  `pout_price` int NOT NULL,
  PRIMARY KEY (`pout_id`) USING BTREE,
  FULLTEXT INDEX `payout_full_text`(`pout_name`, `pout_detail`)
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paymentout
-- ----------------------------
INSERT INTO `paymentout` VALUES (16, '2021-09-02', 'Temmuz Ayı Su faturası', 'Su faturası', 1, 400);
INSERT INTO `paymentout` VALUES (17, '2021-09-01', 'Ağustos ayı Su faturası', 'Su faturası', 1, 650);
INSERT INTO `paymentout` VALUES (18, '2021-08-03', 'Temizlik malzemeleri ilk ödeme', 'Temizlik Malzemesi', 2, 300);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pr_id` int NOT NULL AUTO_INCREMENT,
  `pr_buyPrice` int NOT NULL,
  `pr_code` bigint NOT NULL,
  `pr_detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pr_kdv` int NOT NULL,
  `pr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pr_quantity` int NOT NULL,
  `pr_sellPrice` int NOT NULL,
  `pr_unitType` int NOT NULL,
  PRIMARY KEY (`pr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 2, 863018811, '5L Abant Su', 1, 'Su', 20, 3, 0);
INSERT INTO `product` VALUES (2, 14, 863037546, 'İpek Pamuk', 2, 'Pamuk', 78, 19, 0);
INSERT INTO `product` VALUES (3, 2500, 85411771, 'Vestel Televizyon', 2, 'Televizyon', 76, 6000, 0);

-- ----------------------------
-- Table structure for receipt
-- ----------------------------
DROP TABLE IF EXISTS `receipt`;
CREATE TABLE `receipt`  (
  `re_id` int NOT NULL AUTO_INCREMENT,
  `receipt_number` int NOT NULL,
  `totalPaidPrice` int NOT NULL,
  `totalPrice` int NOT NULL,
  PRIMARY KEY (`re_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receipt
-- ----------------------------
INSERT INTO `receipt` VALUES (30, 742182012, 8600, 32460);
INSERT INTO `receipt` VALUES (31, 742203704, 300, 718);
INSERT INTO `receipt` VALUES (32, 742215362, 0, 32521);
INSERT INTO `receipt` VALUES (33, 742237706, 0, 0);
INSERT INTO `receipt` VALUES (34, 742246952, 0, 0);
INSERT INTO `receipt` VALUES (35, 742262484, 0, 0);
INSERT INTO `receipt` VALUES (36, 742281879, 0, 0);
INSERT INTO `receipt` VALUES (37, 742297067, 287, 287);
INSERT INTO `receipt` VALUES (38, 742307827, 243, 485);
INSERT INTO `receipt` VALUES (39, 742324617, 0, 0);
INSERT INTO `receipt` VALUES (40, 742551125, 60, 60);
INSERT INTO `receipt` VALUES (41, 742558373, 5500, 32400);
INSERT INTO `receipt` VALUES (42, 742569490, 0, 410);
INSERT INTO `receipt` VALUES (43, 742576365, 0, 0);
INSERT INTO `receipt` VALUES (44, 742999510, 0, 75);
INSERT INTO `receipt` VALUES (45, 743007875, 0, 32400);
INSERT INTO `receipt` VALUES (46, 743017927, 0, 25920);
INSERT INTO `receipt` VALUES (47, 743026689, 0, 30);
INSERT INTO `receipt` VALUES (48, 743033679, 0, 451);

-- ----------------------------
-- View structure for v_dashboard
-- ----------------------------
DROP VIEW IF EXISTS `v_dashboard`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_dashboard` AS Select t.customerNumber, t1.orderNumber, t2.productTypeNumber, t2.productNumber, t2.totalBuyPrice, t2.totalSellPrice
From (SELECT COUNT(*) as customerNumber from customer Where cu_isActive = true) as t,
			(SELECT COUNT(*) as orderNumber from depoorder Where order_status = 1) as t1,
			(SELECT COUNT(*) as productTypeNumber, SUM(pr_quantity) as productNumber, SUM(pr_buyPrice) as totalBuyPrice, SUM(pr_sellPrice) as totalSellPrice  from product) as t2 ;

-- ----------------------------
-- View structure for v_payment
-- ----------------------------
DROP VIEW IF EXISTS `v_payment`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_payment` AS Select t.totalPayInPrice, t1.totalPayOutPrice, t2.todayPayInPrice, t3.todayPayOutPrice
From (SELECT SUM(pin_price) as totalPayInPrice from paymentin) as t,
			(SELECT SUM(pout_price) as totalPayOutPrice  from paymentout) as t1,
			(SELECT SUM(pin_price) as todayPayInPrice from paymentin Where DATE(NOW()) =  pin_date) as t2,
			(SELECT SUM(pout_price) as todayPayOutPrice from paymentout Where DATE(NOW()) =  pout_date) as t3 ;

-- ----------------------------
-- Procedure structure for customerPayIn
-- ----------------------------
DROP PROCEDURE IF EXISTS `customerPayIn`;
delimiter ;;
CREATE PROCEDURE `customerPayIn`(IN `c_code` bigint,IN `date_in` varchar(20),IN `date_out` varchar(20))
BEGIN
Select pin.pin_id, cs.cu_name, cs.cu_surname, pin.pin_price, pin.pin_date From paymentin as pin
INNER JOIN customer as cs ON pin.fk_cuId = cs.cu_id
WHERE cs.cu_code = c_code AND pin.pin_date BETWEEN CAST(date_in AS DATE) and CAST(date_out AS DATE);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for fullTextSearch
-- ----------------------------
DROP PROCEDURE IF EXISTS `fullTextSearch`;
delimiter ;;
CREATE PROCEDURE `fullTextSearch`(IN `searchData` varchar(50))
BEGIN
	
	SELECT * FROM customer as cu
	WHERE cu_isActive=true AND MATCH(cu.cu_name, cu.cu_surname, cu.cu_company_title)  /* fulltext yapilan kısmı giricez */
	AGAINST (searchData IN BOOLEAN MODE)
	ORDER BY cu.cu_id DESC;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for getSafeDate
-- ----------------------------
DROP PROCEDURE IF EXISTS `getSafeDate`;
delimiter ;;
CREATE PROCEDURE `getSafeDate`(IN `dateStart` varchar(20),IN `dateFinish` varchar(20))
BEGIN

Select pin.pin_id, cs.cu_name, cs.cu_surname, pin.receipt_number, pin.pin_price, pin.pin_date From paymentin as pin
INNER JOIN customer as cs ON pin.fk_cuId = cs.cu_id
WHERE pin.pin_date BETWEEN CAST(dateStart AS DATE) and CAST(dateFinish AS DATE);

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for paymentInSearch
-- ----------------------------
DROP PROCEDURE IF EXISTS `paymentInSearch`;
delimiter ;;
CREATE PROCEDURE `paymentInSearch`(IN `searchData` varchar(20))
BEGIN
	#Routine body goes here...
		#Routine body goes here...
	SELECT pin_id, cs.cu_name, cs.cu_surname, receipt_number, pin_price FROM paymentin as pin 
	INNER JOIN customer as cs ON pin.fk_cuId = cs.cu_id
	WHERE 
	cs.cu_name LIKE CONCAT('%',searchData,'%') OR
	cs.cu_surname LIKE CONCAT('%',searchData,'%') OR
	pin.receipt_number LIKE CONCAT('%',searchData,'%');

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for payOutFullTextIndex
-- ----------------------------
DROP PROCEDURE IF EXISTS `payOutFullTextIndex`;
delimiter ;;
CREATE PROCEDURE `payOutFullTextIndex`(IN `searchData` varchar(50))
BEGIN
	SELECT * FROM paymentout as pout
	WHERE MATCH(pout_name, pout_detail)
	AGAINST (searchData IN BOOLEAN MODE)
	ORDER BY pout.pout_id DESC;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
