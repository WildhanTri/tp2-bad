/*
 Navicat Premium Data Transfer

 Source Server         : my-lepi-service
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : tp2-bad

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 17/04/2023 02:00:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pasien
-- ----------------------------
DROP TABLE IF EXISTS `pasien`;
CREATE TABLE `pasien`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nama_pasien` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `nik` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tanggal_lahir` date NULL DEFAULT NULL,
  `alamat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pasien
-- ----------------------------
INSERT INTO `pasien` VALUES (1, 'Jono', '902139831000001', '1999-07-13', 'Depok');
INSERT INTO `pasien` VALUES (2, 'Fadil', '478309123923001', '1997-04-03', 'Bogor');
INSERT INTO `pasien` VALUES (3, 'James', '743979839200001', '1998-06-08', 'Jakarta');
INSERT INTO `pasien` VALUES (4, 'Budi', '324890238400001', '2000-10-08', 'Tangerang');
INSERT INTO `pasien` VALUES (5, 'Robert', '398743738000002', '1999-08-18', 'Bekasi');

SET FOREIGN_KEY_CHECKS = 1;
