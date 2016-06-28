-- Create table
create table SOURCE_IMAGE
(
  id              VARCHAR2(64) not null,
  image_name      VARCHAR2(64),
  image_local_url VARCHAR2(64),
  del_flag        VARCHAR2(2),
  image_size      VARCHAR2(64),
  image_group     VARCHAR2(64),
  image_wx_url    VARCHAR2(200),
  media_id        VARCHAR2(200)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table SOURCE_IMAGE
  is '图片素材表';
-- Add comments to the columns
comment on column SOURCE_IMAGE.id
  is '主键';
comment on column SOURCE_IMAGE.image_name
  is '图片名称';
comment on column SOURCE_IMAGE.image_local_url
  is '图片路径';
comment on column SOURCE_IMAGE.del_flag
  is '删除标志';
comment on column SOURCE_IMAGE.image_size
  is '图片大小';
comment on column SOURCE_IMAGE.image_group
  is '所属分组';
comment on column SOURCE_IMAGE.image_wx_url
  is ' ';
comment on column SOURCE_IMAGE.media_id
  is '媒体Id';
-- Create/Recreate primary, unique and foreign key constraints
alter table SOURCE_IMAGE
  add constraint SOURCE_IMAGE_PRIMARY_KEY_ID primary key (ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
