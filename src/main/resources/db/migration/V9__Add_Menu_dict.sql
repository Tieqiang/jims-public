-- Create table
create table MENU_DICT
(
  ID         VARCHAR2(64) not null,
  PARENT_ID  VARCHAR2(64),
  PARENT_IDS VARCHAR2(300),
  MENU_NAME  VARCHAR2(50),
  HREF       VARCHAR2(128),
  ICON       VARCHAR2(16),
  SHOW_FLAG  VARCHAR2(1),
  POSITION   NUMBER
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table MENU_DICT
  is '菜单字典表';
-- Add comments to the columns
comment on column MENU_DICT.ID
  is '主键';
comment on column MENU_DICT.PARENT_ID
  is '父菜单';
comment on column MENU_DICT.PARENT_IDS
  is '所有父菜单';
comment on column MENU_DICT.MENU_NAME
  is '菜单名称';
comment on column MENU_DICT.HREF
  is '路径
';
comment on column MENU_DICT.ICON
  is '头标';
comment on column MENU_DICT.SHOW_FLAG
  is '是否显示';
comment on column MENU_DICT.POSITION
  is '排序';
-- Create/Recreate primary, unique and foreign key constraints
alter table MENU_DICT
  add constraint PK_MENU_DICT primary key (ID)
  using index
  tablespace JIMS_DATA
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
