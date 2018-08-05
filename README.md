
# mysql 禁用group full query
set global sql_mode='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

#创建API数据库
CREATE DATABASE IF NOT EXISTS file_storage_service DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
grant all privileges on file_storage_service.* to file_storage_service@localhost identified by 'file_storage_service';

## 文件权限采用OAuth2授权,需要header里添加token
* 公开文件地址，可以用nginx等提高访问性能
>> 上传地址： /upload/public
>> 下载地址： /public/{filename}
* 私有文件地址
>> 上传地址： /upload/protected
>> 下载地址： /protected/{filename}

## TODO LIST
````
更新文件以及删除无用的文件
`````