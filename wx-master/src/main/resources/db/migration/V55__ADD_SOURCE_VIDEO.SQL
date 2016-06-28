-- Create table
create table SOURCE_VIDEO
(
  id              VARCHAR2(64) not null,
  title           VARCHAR2(200),
  group_id        VARCHAR2(64),
  label           VARCHAR2(64),
  description     BLOB,
  media_id        VARCHAR2(200),
  video_local_url VARCHAR2(200),
  video_wx_url    VARCHAR2(200),
  video_size      VARCHAR2(64),
  del_flag        VARCHAR2(2)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table SOURCE_VIDEO
  is '视频素材表';
-- Add comments to the columns
comment on column SOURCE_VIDEO.id
  is '主键';
comment on column SOURCE_VIDEO.title
  is '视频标题';
comment on column SOURCE_VIDEO.group_id
  is '所属分类Id';
comment on column SOURCE_VIDEO.label
  is '标签';
comment on column SOURCE_VIDEO.description
  is '简介';
comment on column SOURCE_VIDEO.media_id
  is '媒体Id';
comment on column SOURCE_VIDEO.video_local_url
  is '媒体文件本地存放路径';
comment on column SOURCE_VIDEO.video_wx_url
  is '媒体文件微信服务器端路径';
comment on column SOURCE_VIDEO.video_size
  is '文件大小';
comment on column SOURCE_VIDEO.del_flag
  is '删除标识';
