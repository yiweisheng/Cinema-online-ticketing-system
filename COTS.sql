/*
SQLyog Ultimate v12.5.0 (64 bit)
MySQL - 5.7.26 : Database - cots
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cots` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `cots`;

/*Table structure for table `table_class` */

DROP TABLE IF EXISTS `table_class`;

CREATE TABLE `table_class` (
  `Class_ID` int(3) NOT NULL DEFAULT '1' COMMENT '会员等级ID',
  `Class_Name` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '会员等级名称',
  `Class_Discount` float NOT NULL COMMENT '会员打折比例',
  PRIMARY KEY (`Class_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_class` */

insert  into `table_class`(`Class_ID`,`Class_Name`,`Class_Discount`) values 
(1,'普通会员',1),
(2,'高级会员',0.85),
(3,'特级会员',0.7),
(4,'尊贵会员',0.6),
(5,'管理员',0.5);

/*Table structure for table `table_customer` */

DROP TABLE IF EXISTS `table_customer`;

CREATE TABLE `table_customer` (
  `Customer_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `Customer_Name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '会员名',
  `Customer_Email` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '会员邮箱',
  `Customer_Mobile` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '会员电话',
  `Customer_PassWord` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '111' COMMENT '会员密码',
  `Class_ID` int(3) NOT NULL DEFAULT '1' COMMENT '会员等级',
  `Customer_CreationDate` datetime DEFAULT NULL COMMENT '创建时间',
  `Customer_ModifyDate` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`Customer_ID`),
  KEY `Class_ID` (`Class_ID`),
  CONSTRAINT `table_customer_ibfk_1` FOREIGN KEY (`Class_ID`) REFERENCES `table_class` (`Class_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_customer` */

insert  into `table_customer`(`Customer_ID`,`Customer_Name`,`Customer_Email`,`Customer_Mobile`,`Customer_PassWord`,`Class_ID`,`Customer_CreationDate`,`Customer_ModifyDate`) values 
(1,'易玮晟','2242995391@qq.com','17779284362','1111',5,'2021-01-16 16:57:17','2021-02-01 17:13:41'),
(2,'易大大','123456@168.com','17779284362','111',1,'2021-01-16 16:57:17','2021-02-01 17:11:47'),
(3,'易大大2','123456@222.com','17779284111','111',2,'2021-01-16 16:57:17','2021-01-16 16:57:17'),
(4,'易大大3','123456@333.com','17779284222','111',3,'2021-01-16 16:57:17','2021-01-16 16:57:17'),
(5,'易大大4',NULL,'17779284333','111',4,'2021-01-16 16:57:17','2021-01-16 16:57:17'),
(6,'易大大5','123456@qqq.com','17779856666','111',1,'2021-01-17 11:04:46','2021-01-17 11:04:49'),
(8,'易大大7',NULL,'17779856666','111',2,'2021-01-17 11:04:46','2021-01-17 11:04:46'),
(9,'易安世','2928875888888@168.com','17779288888','1111111',1,'2021-01-18 10:50:02','2021-01-18 18:03:27'),
(11,'易大大a','29288758@168.co','17779288888','1999.zxc',1,'2021-01-27 09:32:25','2021-01-27 09:32:25'),
(20,'易大大b','2242995391@qq.c','17779998888','1234567',1,'2021-01-27 16:14:18','2021-01-27 16:14:18'),
(21,'admin',NULL,'18000258820','111',1,'2021-01-27 18:06:55','2021-01-27 18:06:58');

/*Table structure for table `table_hall` */

DROP TABLE IF EXISTS `table_hall`;

CREATE TABLE `table_hall` (
  `Hall_ID` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '影厅ID',
  `Hall_Name` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '影厅名' COMMENT '影厅名',
  `Hall_Seats` int(4) NOT NULL COMMENT '影厅座位数',
  `Hall_Description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '影厅描述',
  PRIMARY KEY (`Hall_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_hall` */

insert  into `table_hall`(`Hall_ID`,`Hall_Name`,`Hall_Seats`,`Hall_Description`) values 
('001','1号影厅',30,'1号影厅'),
('002','2号影厅',40,'2号影厅'),
('003','3号影厅',90,'3号影厅'),
('004','4号影厅',100,'');

/*Table structure for table `table_movie` */

DROP TABLE IF EXISTS `table_movie`;

CREATE TABLE `table_movie` (
  `Movie_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '影片ID',
  `Movie_Name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '影片名',
  `Movie_Region` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT '中国' COMMENT '影片产地',
  `Movie_Type` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT '喜剧' COMMENT '影片类型',
  `Movie_MainActor` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '无' COMMENT '主要演员',
  `Movie_Director` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '无' COMMENT '导演',
  `Movie_Duration` time NOT NULL DEFAULT '00:00:00' COMMENT '时长',
  `Movie_Description` varchar(250) COLLATE utf8_bin NOT NULL DEFAULT '无' COMMENT '概述',
  `Movie_ImageSrc` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '../DBimages/movie/error.jpg' COMMENT '封面',
  PRIMARY KEY (`Movie_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_movie` */

insert  into `table_movie`(`Movie_ID`,`Movie_Name`,`Movie_Region`,`Movie_Type`,`Movie_MainActor`,`Movie_Director`,`Movie_Duration`,`Movie_Description`,`Movie_ImageSrc`) values 
(1,'无人区','中国','喜剧','无','无','00:00:00','小有名气但利欲熏心的律师潘肖（徐峥 饰），凭借扎实的法律知识和巧舌如簧的庭辩技巧，成功帮一名西北盗猎团伙老大（多布杰 饰）洗脱罪名。老大承诺十天后付清余款，潘肖则要求对方用一辆红色轿车抵押。在此之...','../DBimages/movie/0001.jpg'),
(2,'疯狂的赛车 (2009)','中国','喜剧','无','无','01:30:22','在一次全国自行车锦标赛上，倒霉的职业车手耿浩（黄渤 饰）以0.01秒的微弱差距败给撞到狗屎运的对手，屈居亚军，随后他又遭奸商李法拉（九孔 饰）算计，因药检呈阳性而被终身禁赛，他的教练（马少骅 饰）因此气倒。 多年后，耿浩靠开车运送水产为生，养活中风的教练，而当年的骗子李法拉则依靠其推销的补品“肾白银”大发其财。李嫌弃彪悍魁梧的老婆，因此雇用两个业余杀手（王双宝 & 巴多 饰）杀妻，结果杀手反被老婆买通；','../DBimages/movie/0002.jpg'),
(3,'武林外传','中国','连续剧','	闫妮 姚晨 倪虹洁 沙溢 喻恩泰 姜超 王莎莎 范明 肖剑 于娟','尚敬','00:00:00','　该剧故事发生在明代一个叫七侠镇的地方，是关中一个不起眼的小镇。一个叫郭芙蓉的黄毛丫头初入江湖，欠下钱财，被困在“能人辈出”的同福客栈。故事从这里开始，依次引出佟湘玉、白展堂、吕秀才、李大嘴、莫小贝，以及邢捕头、燕小六、钱掌柜这几个性格各异、风趣动人的年轻主人公，引出了一连串戏谑生动、引人入胜的故事。一群性情各异、既可怜又可爱的年轻人聚在一起，在同福客栈里经历了江湖上的各种风险和传奇，遍尝人间冷暖，体会亲情爱情，见证成长过程中的酸甜苦辣……','../DBimages/movie/0003.jpg'),
(4,'加油!金顺','韩国','韩剧','韩惠珍 姜智焕 李世恩 李敏基','无','00:00:00','金顺从小失去父母，是奶奶照顾她长大，她的梦想是成为一名美容师。一个偶然的机会，金顺遇到了母亲开美容院的宰熙，金顺利用宰熙实现了自己在美容院上班的理想。宰熙是一名大夫，其实他就是当年为金顺的丈夫看病，结果因为误诊夺去患者生命的实习医生。经过纷繁的纠葛，他们相爱了。金顺和在美容院工作的伙伴恩珠关系还不错，金顺和恩珠同时爱着宰熙，善良的金顺怎么会轻易割舍自己的爱？','../DBimages/movie/0004.jpg'),
(5,'紫罗兰永恒花园','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/2b05a7a7-93cd-43f3-b6b7-f7d7ed31df13/QQ图片20201224231422.jpg'),
(8,'人生海海','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/error.jpg'),
(9,'罗生门','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/error.jpg'),
(10,'叶问1','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/6af5640e-1f1a-4ba1-b430-0b3ef7292b10/901832.jpg'),
(11,'叶问2','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/a5ba920b-3c57-4a63-a501-d77ae75c0400/1005830.jpg'),
(16,'叶问3','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/3eb5f5b3-98c3-4ffa-ad19-36e956af4430/1045048.jpg'),
(17,'叶问','中国','喜剧','无','无','00:00:00','无','../DBimages/movie/81257881-d530-4712-9416-af17ac51317b/1005531.jpg');

/*Table structure for table `table_order` */

DROP TABLE IF EXISTS `table_order`;

CREATE TABLE `table_order` (
  `Order_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `Customer_ID` bigint(20) NOT NULL COMMENT '会员ID',
  `Schedule_ID` bigint(20) NOT NULL COMMENT '时间表ID',
  `Seat_ID` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '座位ID',
  `Order_IsActive` int(1) NOT NULL DEFAULT '1' COMMENT '订单是否可用',
  `Order_SecretKey` varchar(6) COLLATE utf8_bin NOT NULL COMMENT '用于存放密钥',
  `Order_Cipher` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用于存放加密后的密文',
  `Order_AdjustedPrice` float NOT NULL DEFAULT '0' COMMENT '会员打折之后的价格',
  `Order_BuyDate` datetime NOT NULL COMMENT '购买时间',
  PRIMARY KEY (`Order_ID`),
  KEY `Customer_ID` (`Customer_ID`),
  KEY `Schedule_ID` (`Schedule_ID`),
  KEY `Seat_ID` (`Seat_ID`),
  CONSTRAINT `table_order_ibfk_3` FOREIGN KEY (`Seat_ID`) REFERENCES `table_seat` (`Seat_ID`),
  CONSTRAINT `table_order_ibfk_4` FOREIGN KEY (`Customer_ID`) REFERENCES `table_customer` (`Customer_ID`),
  CONSTRAINT `table_order_ibfk_5` FOREIGN KEY (`Schedule_ID`) REFERENCES `table_schedule` (`Schedule_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_order` */

insert  into `table_order`(`Order_ID`,`Customer_ID`,`Schedule_ID`,`Seat_ID`,`Order_IsActive`,`Order_SecretKey`,`Order_Cipher`,`Order_AdjustedPrice`,`Order_BuyDate`) values 
(1,2,3,'0030101',1,'','NULL',33.5,'2020-12-16 10:22:22'),
(2,4,1,'0010101',1,'','NULL',17.64,'2020-12-16 10:23:43'),
(3,3,2,'0010101',1,'','NULL',33.915,'2020-12-16 10:25:55'),
(4,3,5,'0020103',1,'','NULL',50,'2021-01-29 17:01:16'),
(5,5,11,'0020101',0,'','NULL',0,'2021-01-17 17:22:57'),
(51,1,1,'0010201',0,'WfThLV','6FeusJIG9ezpvQpkiumGDQ==',19.95,'2021-01-31 18:58:29'),
(52,1,1,'0010203',0,'BTUrDj','6XsSoSbsBNsMFhDObuQtkQ==',19.95,'2021-01-31 18:58:29'),
(53,1,1,'0010304',1,'LxKYYu','D04U0rc3YEC0Vy1bFnP6/g==',19.95,'2021-01-31 18:58:29'),
(54,2,1,'0010103',0,'0iP4xU','v4i6kmg06EsU8x/hrARSBA==',39.9,'2021-02-01 16:29:30'),
(55,2,1,'0010308',1,'VjEMyM','S/ZyDK/5W9XppgccCyEwow==',39.9,'2021-02-10 10:23:31');

/*Table structure for table `table_schedule` */

DROP TABLE IF EXISTS `table_schedule`;

CREATE TABLE `table_schedule` (
  `Schedule_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '时间表ID',
  `Movie_ID` bigint(20) NOT NULL COMMENT '电影ID',
  `Hall_ID` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '影厅ID',
  `Schedule_IsShow` int(11) NOT NULL DEFAULT '1' COMMENT '排片是否在映',
  `Schedule_price` float NOT NULL DEFAULT '0' COMMENT '时间表上票价',
  `Schedule_BeginDateTime` datetime NOT NULL COMMENT '电影放映时间',
  PRIMARY KEY (`Schedule_ID`),
  KEY `Hall_ID` (`Hall_ID`),
  KEY `Movie_ID` (`Movie_ID`),
  CONSTRAINT `table_schedule_ibfk_1` FOREIGN KEY (`Hall_ID`) REFERENCES `table_hall` (`Hall_ID`),
  CONSTRAINT `table_schedule_ibfk_2` FOREIGN KEY (`Movie_ID`) REFERENCES `table_movie` (`Movie_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_schedule` */

insert  into `table_schedule`(`Schedule_ID`,`Movie_ID`,`Hall_ID`,`Schedule_IsShow`,`Schedule_price`,`Schedule_BeginDateTime`) values 
(1,1,'001',1,39.9,'2020-12-16 10:00:00'),
(2,1,'001',1,0,'1970-01-01 00:00:00'),
(3,3,'003',1,45.9,'2020-12-17 10:30:00'),
(4,4,'004',0,22.5,'2021-01-16 11:44:47'),
(5,8,'002',1,30,'2021-01-17 10:16:30'),
(6,4,'003',1,3,'1970-01-01 00:00:00'),
(7,9,'002',0,20,'2021-01-17 10:19:09'),
(11,1,'002',1,20,'2021-01-15 12:10:37'),
(12,9,'002',1,22.5,'2021-01-26 15:46:42');

/*Table structure for table `table_seat` */

DROP TABLE IF EXISTS `table_seat`;

CREATE TABLE `table_seat` (
  `Seat_ID` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '座位ID',
  `Hall_ID` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '影厅ID',
  `Seat_Row` int(3) NOT NULL COMMENT '座位所在行数',
  `Seat_Column` int(3) NOT NULL COMMENT '座位所在列数',
  `Seat_IsActive` int(1) DEFAULT '1' COMMENT '座位是否可用',
  `Seat_IsCorridor` int(11) NOT NULL DEFAULT '0' COMMENT '是否为过道',
  PRIMARY KEY (`Seat_ID`),
  KEY `Hall_ID` (`Hall_ID`),
  CONSTRAINT `table_seat_ibfk_1` FOREIGN KEY (`Hall_ID`) REFERENCES `table_hall` (`Hall_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `table_seat` */

insert  into `table_seat`(`Seat_ID`,`Hall_ID`,`Seat_Row`,`Seat_Column`,`Seat_IsActive`,`Seat_IsCorridor`) values 
('0010101','001',1,1,1,0),
('0010102','001',1,2,1,0),
('0010103','001',1,3,0,0),
('0010104','001',1,4,1,0),
('0010105','001',1,5,1,1),
('0010106','001',1,6,1,0),
('0010107','001',1,7,1,0),
('0010108','001',1,8,1,0),
('0010109','001',1,9,1,0),
('0010110','001',1,10,1,0),
('0010201','001',2,1,0,0),
('0010202','001',2,2,1,0),
('0010203','001',2,3,0,0),
('0010204','001',2,4,1,0),
('0010205','001',2,5,1,1),
('0010206','001',2,6,1,0),
('0010207','001',2,7,1,0),
('0010208','001',2,8,1,0),
('0010209','001',2,9,1,0),
('0010210','001',2,10,1,0),
('0010301','001',3,1,1,0),
('0010302','001',3,2,1,0),
('0010303','001',3,3,1,0),
('0010304','001',3,4,0,0),
('0010305','001',3,5,1,1),
('0010306','001',3,6,1,0),
('0010307','001',3,7,1,0),
('0010308','001',3,8,0,0),
('0010309','001',3,9,1,0),
('0010310','001',3,10,1,0),
('0020101','002',1,1,1,0),
('0020102','002',1,2,1,0),
('0020103','002',1,3,1,0),
('0020201','002',2,1,1,0),
('0030101','003',1,1,1,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
