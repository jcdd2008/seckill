--创建数据库
CREATE  DATABASE  SECKILL;
-- 使用数据库
use seckill;
--创建表接构
CREATE  TABLE  seckill(
seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number INT NOT NULL COMMENT '库存数量',
start_time TIMESTAMP  NOT NULL COMMENT '秒杀开始时间',
end_time TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='描述库存表';
--初始化数据
INSERT INTO seckill(NAME,number,start_time,end_time)
VALUES
('1000秒杀iPhone6s',100,'2016-06-15 00:00:00','2016-6-20 00:00:00'),
('500秒杀ipad 2',200,'2016-06-15 00:00:00','2016-6-20 00:00:00'),
('300秒杀小米4',300,'2016-06-15 00:00:00','2016-6-20 00:00:00'),
('200秒杀小米3',400,'2016-06-15 00:00:00','2016-6-20 00:00:00');

--秒杀成功明细表
CREATE TABLE success_killed(
seckill_id BIGINT NOT NULL COMMENT '秒杀商品id',
user_phone BIGINT NOT NULL COMMENT '用户手机号',
state TINYINT NOT NULL DEFAULT -1 COMMENT '状态提示:-1无效 0：成功 1：已付款',
create_time TIMESTAMP NOT NULL COMMENT '创建时间',
PRIMARY KEY (seckill_id,user_phone),
KEY idx_create_time(create_time)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';