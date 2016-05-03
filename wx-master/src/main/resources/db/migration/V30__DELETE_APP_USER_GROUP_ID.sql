--删除app_user 表 group_id 外键
alter table APP_USER drop constraint FK_GROUP_ID;
