CREATE DATABASE resume 

DROP TABLE IF EXISTS tb_resume;
CREATE TABLE `tb_resume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_resume
-- ----------------------------
INSERT INTO tb_resume VALUES ('1', '西直门','李四','15678903452');
INSERT INTO tb_resume VALUES ('2', '中关村','王思','13456754321');
INSERT INTO tb_resume VALUES ('3', '六里桥','谢如','17890765643');
INSERT INTO tb_resume VALUES ('4', '苹果园','段誉','18567898765');
INSERT INTO tb_resume VALUES ('5', '永丰南','赵启','19233455688');