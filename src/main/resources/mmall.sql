/*用户表*/
CREATE TABLE `mmall_user`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` VARCHAR(50)  NOT NULL COMMENT  '用户名',
  `password`  VARCHAR(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` VARCHAR(50) DEFAULT NULL,
  `phone`  VARCHAR(20)  DEFAULT NULL,
  `question` VARCHAR(100) DEFAULT NULL COMMENT '找回密码的问题',
  `answer` VARCHAR(100)  DEFAULT NULL COMMENT  '找回密码答案',
  `role`  INT(4) NOT NULL COMMENT '角色0-管理员，1-普通用户',
  `create_time` datetime NOT NULL COMMENT   '创建时间',
  `update_time` datetime NOT NULL  COMMENT  '最后一次更新时间',
  PRIMARY KEY (`id`),
  /*设置username 为唯一索引的好处是可以不用在业务逻辑中进行判重，直接在表中进行*/
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
)ENGINE=InnoDB  AUTO_INCREMENT=21 DEFAULT charset=utf8

/*分类表*/
/*分类表是可以递归的，递归就需要结束条件，当parent_id=0时判断为递归结束*/
CREATE TABLE `mmall_category`(
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `parent_id` INT(11) DEFAULT NULL COMMENT '父类别id当ID=0时，说明是根节点，一级类别',
  `name`  VARCHAR(50) DEFAULT NULL COMMENT '类别名称',
  `status`  tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常，2-已废弃',
  `sort_order` int(4)  DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=100032 DEFAULT charset=utf8

/*产品表*/
CREATE TABLE `mmall_product`(
  `id` int(11) NOT NULL AUTO_INCREMENT comment '商品id',
  `category_id` int(11) NOT NULL comment '分类id,对应mmall_category表的主键',
  `name` VARCHAR(100) NOT NULL comment '商品名称',
  `subtitle`  VARCHAR (200) DEFAULT NULL comment '商品副标题',
  `main_image` VARCHAR(500) DEFAULT NULL comment '产品主图，url相对地址',
  `sub_images`  text comment '图片地址，json格式，扩展用',
  `detail`  text  comment '商品详情',
  `price`   decimal (20,2) NOT NULL comment '价格，单位-元保留两位小数',
  `stock`   int(11) NOT NULL comment '库存数量',
  `status`  int(6) DEFAULT '1' comment '商品状态，1-在售，2-下架，3-删除',
  `create_time` datetime DEFAULT NULL comment '创建时间',
  `update_time` datetime DEFAULT NULL comment '更新时间',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT charset=utf8

/*购物车表*/
CREATE TABLE `mmall_cart`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL ,
  `product_id` int(11) DEFAULT NULL comment '商品id',
  `quantity` int(11) DEFAULT NULL comment '数量',
  `checked` int(1) DEFAULT NULL comment '是否选择，1=已勾选，0=未勾选',
  `create_time` datetime DEFAULT NULL comment '创建时间',
  `update_time` datetime DEFAULT NULL  comment '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT charset=utf8


/*支付信息表*/
CREATE TABLE  `mmall_pay_info`(
  `id`  int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL comment '用户id',
  `order_no`  bigint(20) DEFAULT NULL comment '订单号',
  `pay_platform` int(10) DEFAULT NULL comment '支付平台：1-支付宝，2-微信',
  `platform_number` VARCHAR (200) DEFAULT NULL comment '支付宝支付流水号',
  `platform_status` VARCHAR (20)  DEFAULT NULL comment '支付宝支付状态',
  `create_time` datetime DEFAULT NULL comment '创建时间',
  `update_time` datetime DEFAULT NULL comment '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT charset=utf8

/*订单表*/
CREATE TABLE `mmall_order`(
  `id` int(11) NOT NULL AUTO_INCREMENT comment '订单id',
  `order_no` bigint(20) DEFAULT NULL comment '订单号',
  `user_id`  int(11) DEFAULT NULL comment '用户id',
  `shipping_id` int(11) DEFAULT NULL,
  `payment` decimal(20,2) DEFAULT NULL comment '实际付款金额，单位是元，保留两位小数',
  `payment_type` int(4) DEFAULT NULL comment '支付类型，1-在线支付',
  `postage` int(10) DEFAULT NULL  comment '运费，单位是元',
  `status` int(10) DEFAULT NULL comment '订单状态：0-已取消，10-未付款，20-已付款，40-已发货，50-交易成功，60-交易关闭',
  `payment_time` datetime DEFAULT NULL comment '支付时间',
  `send_time` datetime DEFAULT NULL comment '发货时间',
  `end_time` datetime DEFAULT NULL comment '交易完成时间',
  `close_time` datetime DEFAULT NULL comment '交易关闭时间',
  `create_time` datetime DEFAULT NULL comment '创建时间',
  `update_time` datetime DEFAULT NULL comment '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT charset=utf8

/*订单明细表*/
CREATE TABLE `mmall_order_item`(
  `id` int(11) NOT NULL AUTO_INCREMENT comment '订单子表id',
  `user_id` int(11) DEFAULT NULL ,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL comment '商品id',
  `product_name` VARCHAR(100) DEFAULT NULL comment '商品名称',
  `product_image` VARCHAR (500) DEFAULT NULL comment '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL comment '生成订单时的商品价格，单位是元，保留两位小数',
  `quantity` int(10) DEFAULT NULL  comment '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL comment '商品总价，单位是元，保留两位小数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_index_user_id` (`order_no`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT =113 DEFAULT charset=utf8

/*收货地址*/
CREATE TABLE `mmall_shipping`(
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL comment '用户id',
  `receiver_name` VARCHAR (20) DEFAULT NULL comment '收货姓名',
  `receiver_phone` VARCHAR (20) DEFAULT NULL comment '收货固定电话',
  `receiver_mobile` VARCHAR (20) DEFAULT NULL comment '收货移动电话',
  `receiver_province` VARCHAR (20) DEFAULT NULL comment '省份',
  `receiver_city` VARCHAR (20) DEFAULT NULL comment '城市',
  `receiver_district` VARCHAR (20) DEFAULT NULL comment '区/县',
  `receiver_address` VARCHAR (200) DEFAULT NULL comment '详细地址',
  `receiver_zip` VARCHAR (6) DEFAULT NULL comment '邮编',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT charset=utf8