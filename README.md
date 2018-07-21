
#mysql 禁用group full query
set global sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

#创建API数据库
CREATE DATABASE IF NOT EXISTS file_storage_service DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
grant all privileges on file_storage_service.* to file_storage_service@localhost identified by 'file_storage_service';


### TODO LIST
````

`````