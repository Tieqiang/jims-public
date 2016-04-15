-- Create table
create table BASE_DICT
(
  ID         VARCHAR2(64) not null,
  BASE_CODE  VARCHAR2(20),
  BASE_NAME  VARCHAR2(80),
  INPUT_CODE VARCHAR2(40),
  BASE_TYPE  VARCHAR2(40)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns
comment on column BASE_DICT.ID
  is '主键';
comment on column BASE_DICT.BASE_CODE
  is '编码';
comment on column BASE_DICT.BASE_NAME
  is '编码名称';
comment on column BASE_DICT.INPUT_CODE
  is '拼音码';
comment on column BASE_DICT.BASE_TYPE
  is '字典名称';
-- Create/Recreate primary, unique and foreign key constraints
alter table BASE_DICT
  add constraint BASE_DICT_PK primary key (ID)
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
alter table BASE_DICT
  add constraint BASE_DICT_AK unique (BASE_CODE, BASE_NAME)
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
