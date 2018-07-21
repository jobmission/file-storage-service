### 建表
CREATE TABLE file_record_entity
(
  id            BIGINT(20) PRIMARY KEY                    NOT NULL AUTO_INCREMENT,
  user_id       BIGINT(11)                                NOT NULL,
  shared     TINYINT(1)                                         DEFAULT 0
  COMMENT '文件是否访问保护',
  file_name     VARCHAR(10)                               NOT NULL
  COMMENT '相对路径',
  record_status INT(11) DEFAULT '0'                       NOT NULL,
  remarks       VARCHAR(500) COMMENT '备注',
  version       INT(11) DEFAULT '0'                       NOT NULL,
  date_created  DATETIME DEFAULT CURRENT_TIMESTAMP        NOT NULL,
  last_modified DATETIME                                           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX file_record_entity_user_id (user_id),
  INDEX file_record_entity_file_name (file_name)
);

