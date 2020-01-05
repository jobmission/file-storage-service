
# 创建数据库
````
CREATE DATABASE IF NOT EXISTS file_storage_service DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
create user 'user_dev'@'localhost' identified by 'pass_dev';
grant all privileges on file_storage_service.* to 'user_dev'@'localhost';
````

# 采用OAuth2授权,需要添加Authorization header
````
Authorization:Bearer a.b.c
````
# 公开文件地址，可以用nginx等提高下载性能
````
上传地址： /upload/public
下载地址： /download/public?filename=123.jpg
````
# 私有文件地址
````
上传地址： /upload/protected
下载地址： /download/protected?filename=456.jpg
````
### TODO LIST
* 更新文件以及删除无用的文件
