-- Create table
create table MODUL_DICT
(
  ID          VARCHAR2(64) not null,
  MODULE_NAME VARCHAR2(100),
  INPUT_CODE  VARCHAR2(50),
  HOSPITAL_ID VARCHAR2(64),
  MODULE_LOAD VARCHAR2(200)
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
comment on table MODUL_DICT
  is '模块字典表';
-- Add comments to the columns
comment on column MODUL_DICT.MODULE_NAME
  is '模块名称';
comment on column MODUL_DICT.INPUT_CODE
  is '输入码';
comment on column MODUL_DICT.HOSPITAL_ID
  is '医院编码';
comment on column MODUL_DICT.MODULE_LOAD
  is '默认首页';
-- Create/Recreate primary, unique and foreign key constraints
alter table MODUL_DICT
  add constraint PK_MODUL_DICT primary key (ID)
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
