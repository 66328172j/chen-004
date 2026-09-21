-- spot 自然资源卫片执法图斑核查整改与销号管理 -- schema (chen-004)
-- 列名与基线实体契约（@TableName/@TableField）逐列对齐，改列必须同步实体。
-- 库：chen_004

CREATE TABLE IF NOT EXISTS t_spot_case_bill (
  id bigint NOT NULL COMMENT '主键',
  bill_no varchar(64) DEFAULT NULL COMMENT '处置方案报批单号',
  node_no int DEFAULT NULL COMMENT '当前加签层 0..3',
  sign_mode int DEFAULT NULL COMMENT '同层加签方式 0任一人 1名单点齐',
  need_count int DEFAULT NULL COMMENT '本层应加签人数',
  sign_count int DEFAULT NULL COMMENT '本层已加签人数',
  status int DEFAULT NULL COMMENT '报批进展 0核议中 1已备案 2已打回',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='违法用地处置方案逐级报批单';

CREATE TABLE IF NOT EXISTS t_spot_district_qual (
  id bigint NOT NULL COMMENT '主键',
  site_no varchar(64) DEFAULT NULL COMMENT '辖区编码',
  site_name varchar(128) DEFAULT NULL COMMENT '县（市、区）自然资源主管部门',
  site_type varchar(32) DEFAULT NULL COMMENT '主导地类',
  road_name varchar(128) DEFAULT NULL COMMENT '管辖归属(市—县—乡镇)',
  th1_max decimal(12,2) DEFAULT NULL COMMENT '本辖区核定一档面积上限(亩)',
  th2_max decimal(12,2) DEFAULT NULL COMMENT '二档面积上限(亩)',
  th3_max decimal(12,2) DEFAULT NULL COMMENT '三档面积上限(亩)',
  status int DEFAULT NULL COMMENT '档案状况 0在用 1已撤销',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图斑坐落辖区管辖档案';

CREATE TABLE IF NOT EXISTS t_spot_due_task (
  id bigint NOT NULL COMMENT '主键',
  item_no varchar(64) DEFAULT NULL COMMENT '挂牌督办任务编号',
  due_at datetime DEFAULT NULL COMMENT '整改期限到期时刻',
  amount decimal(12,2) DEFAULT NULL COMMENT '单条提前提醒自然日数',
  status int DEFAULT NULL COMMENT '条目状况 0待挂牌 1已挂牌 2挂不上',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='整改期限挂牌督办任务条目';

CREATE TABLE IF NOT EXISTS t_spot_grade_line (
  id bigint NOT NULL COMMENT '主键',
  rule_code varchar(64) DEFAULT NULL COMMENT '分档线代号',
  rule_name varchar(128) DEFAULT NULL COMMENT '分档线名称',
  th1_max decimal(12,2) DEFAULT NULL COMMENT '一般档违法占用比例上限(%)',
  th2_max decimal(12,2) DEFAULT NULL COMMENT '较重档违法占用比例上限(%)',
  th3_max decimal(12,2) DEFAULT NULL COMMENT '严重档违法占用比例上限(%)',
  eff_start datetime DEFAULT NULL COMMENT '启用时刻',
  eff_end datetime DEFAULT NULL COMMENT '交棒时刻(不含)',
  priority int DEFAULT NULL COMMENT '取用顺位(数值越大越优先)',
  status int DEFAULT NULL COMMENT '分档线状况 0在用 1已让位',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='违法占用耕地面积分档线';

CREATE TABLE IF NOT EXISTS t_spot_recv_row (
  id bigint NOT NULL COMMENT '主键',
  batch_no varchar(64) DEFAULT NULL COMMENT '卫片图斑包号',
  row_no int DEFAULT NULL COMMENT '原包内行次',
  item_code varchar(64) DEFAULT NULL COMMENT '图斑编号',
  qty decimal(12,2) DEFAULT NULL COMMENT '图斑汇交面积(亩)',
  status int DEFAULT NULL COMMENT '行进展 0待核收 1已入库 2挂驳回',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='季度卫片图斑包接收明细';

CREATE TABLE IF NOT EXISTS t_spot_spot_bill (
  id bigint NOT NULL COMMENT '主键',
  bill_no varchar(64) DEFAULT NULL COMMENT '图斑建档单号',
  site_id int DEFAULT NULL COMMENT '坐落辖区管辖档案',
  site_no varchar(64) DEFAULT NULL COMMENT '辖区编码',
  qty decimal(12,2) DEFAULT NULL COMMENT '图斑实测占地面积(亩)',
  fine_amt decimal(12,2) DEFAULT NULL COMMENT '其中违法占用耕地面积(亩)',
  grade_level int DEFAULT NULL COMMENT '违法占用规模档',
  status int DEFAULT NULL COMMENT '进展 0待核对 1已核对 2已归档',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='卫片图斑建档单';

CREATE TABLE IF NOT EXISTS t_spot_verify_order (
  id bigint NOT NULL COMMENT '主键',
  biz_no varchar(64) DEFAULT NULL COMMENT '核查处置单号',
  stage int DEFAULT NULL COMMENT '当前关口 0..5',
  status int DEFAULT NULL COMMENT '单子进展 0在途 1已销号 2退回核查',
  content varchar(255) DEFAULT NULL COMMENT '处置记事',
  last_action varchar(64) DEFAULT NULL COMMENT '最近一次挪动动作',
  del_flag int DEFAULT '0' COMMENT '删除标记 0正常 1删除',
  create_by varchar(64) DEFAULT NULL COMMENT '创建者',
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新者',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  remark varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图斑核查处置单';

-- 初始档案数据（验收测试依赖 id=1 启用 / id=2 停用）
-- 验收测试依赖 t_spot_district_qual 两条种子档案：id=0 在用、id=1 已撤销（F3 坑1/坑5 的联动判定项）。
-- 面积上限取 5/20/60 亩，正压在 F3 坑2 的折算断言上（等于上限归高一档）。
INSERT IGNORE INTO t_spot_district_qual (id, site_no, site_name, site_type, road_name, th1_max, th2_max, th3_max, status, del_flag, create_by, create_time)
VALUES (0, 'XZ00', '临江市自然资源和规划局城东分局', '水田', '临江市—城东区—新港镇', 5.00, 20.00, 60.00, 0, 0, 'seed', NOW()),
       (1, 'XZ01', '旧港渔业乡管辖档案（已撤并）', '未利用地', '临江市—城西区—旧港乡', 5.00, 20.00, 60.00, 1, 0, 'seed', NOW());

