-- Create table
create table SOURCE_IMAGE_FONT
(
  id                 VARCHAR2(64) not null,
  title              VARCHAR2(200),
  thumb_media_id     VARCHAR2(64),
  author             VARCHAR2(200),
  digest             VARCHAR2(200),
  show_cover_pic     VARCHAR2(4),
  content            BLOB,
  content_source_url VARCHAR2(200),
  del_flag           VARCHAR2(4),
  local_url          VARCHAR2(100),
  wx_url             VARCHAR2(200),
  media_id           VARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table SOURCE_IMAGE_FONT
  is '图文消息表';
-- Add comments to the columns
comment on column SOURCE_IMAGE_FONT.id
  is '主键';
comment on column SOURCE_IMAGE_FONT.title
  is '标题';
comment on column SOURCE_IMAGE_FONT.thumb_media_id
  is '图文消息的封面图片素材id（必须是永久mediaID）';
comment on column SOURCE_IMAGE_FONT.author
  is '作者';
comment on column SOURCE_IMAGE_FONT.digest
  is '单图文的摘要';
comment on column SOURCE_IMAGE_FONT.show_cover_pic
  is '是否显示封面，0为false，即不显示，1为true，即显示';
comment on column SOURCE_IMAGE_FONT.content
  is '图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS';
comment on column SOURCE_IMAGE_FONT.content_source_url
  is '图文消息的原文地址，即点击“阅读原文”后的URL';
comment on column SOURCE_IMAGE_FONT.del_flag
  is '删除标识';
comment on column SOURCE_IMAGE_FONT.local_url
  is '本服务器的保存地址';
comment on column SOURCE_IMAGE_FONT.wx_url
  is '微信服务器的保存地址';
comment on column SOURCE_IMAGE_FONT.media_id
  is '微信服务器返回的media_id';
-- Create/Recreate primary, unique and foreign key constraints
alter table SOURCE_IMAGE_FONT
  add constraint IMAGE_FONT_PRIMARY_KEY_ID primary key (ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
