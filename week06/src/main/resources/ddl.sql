CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_account` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `user_name` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_pic` varchar(512) DEFAULT NULL COMMENT '用户头像',
  `password` varchar(32) DEFAULT NULL COMMENT '加密密码',
  `def_addr` json DEFAULT NULL COMMENT '默认地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_user_account` (`user_account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
CREATE TABLE `item` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `item_code` varchar(32) DEFAULT NULL COMMENT '商品编码',
  `item_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `item_main_pic` varchar(512) DEFAULT NULL COMMENT '商品主图',
  `fee` bigint(20) DEFAULT NULL COMMENT '商品价格,单位分',
  `discounts_fee` bigint(20) DEFAULT NULL COMMENT '商品优惠金额,单位分',
  `category_1` varchar(32) DEFAULT NULL COMMENT '一级类目',
  `category_2` varchar(32) DEFAULT NULL COMMENT '二级类目',
  `category_3` varchar(32) DEFAULT NULL COMMENT '三级类目',
  `other_attributes` json DEFAULT NULL COMMENT '其它属性',
  `item_detail` varchar(4096) NOT NULL DEFAULT '' COMMENT '商品详情',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_item_code` (`item_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
CREATE TABLE `main_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_num` varchar(64) NOT NULL COMMENT '订单号',
  `buyer_id` bigint(11) NOT NULL COMMENT '购买者user_id',
  `sub_order_ids` varchar(2048) NOT NULL COMMENT '子订单id集合，用,分割',
  `order_name` varchar(64) NOT NULL DEFAULT '' COMMENT '订单名称',
  `order_main_pic` varchar(512) DEFAULT NULL COMMENT '订单主图',
  `total_fee` bigint(20) DEFAULT NULL COMMENT '总金额,单位分',
  `discounts_fee` bigint(20) DEFAULT NULL COMMENT '优惠金额,单位分',
  `actually_fee` bigint(20) DEFAULT NULL COMMENT '实付金额,单位分',
  `order_status` tinyint(2) DEFAULT NULL COMMENT '订单状态',
  `pay_status` tinyint(2) DEFAULT NULL COMMENT '支付状态',
  `logistics_status` tinyint(2) DEFAULT NULL COMMENT '物流状态',
  `shipping_addr` json DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`),
  KEY `index_order_num` (`order_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主订单表';
CREATE TABLE `sub_order` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '子订单id',
  `order_num` varchar(64) NOT NULL COMMENT '子订单号',
  `main_order_num` varchar(64) NOT NULL COMMENT '主订单号',
  `buyer_id` bigint(11) NOT NULL COMMENT '购买者user_id',
  `item_code` varchar(32) NOT NULL COMMENT '商品编码',
  `item_num` int(11) NOT NULL COMMENT '商品数量',
  `item_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `item_main_pic` varchar(512) DEFAULT NULL COMMENT '商品主图',
  `item_weight` bigint(20) NOT NULL COMMENT '商品重量，单位g',
  `item_snapshoot` varchar(4096) NOT NULL DEFAULT '' COMMENT '商品快照',
  `item_total_fee` bigint(20) DEFAULT NULL COMMENT '商品总价格,单位分',
  `discounts_fee` bigint(20) DEFAULT NULL COMMENT '优惠金额,单位分',
  `actually_fee` bigint(20) DEFAULT NULL COMMENT '实付金额,单位分',
  `order_status` tinyint(2) DEFAULT NULL COMMENT '子订单状态',
  `pay_status` tinyint(2) DEFAULT NULL COMMENT '支付状态',
  `logistics_status` tinyint(2) DEFAULT NULL COMMENT '物流状态',
  `logistics_num` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `shipping_addr` json DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`),
  KEY `index_order_num` (`order_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子订单表';