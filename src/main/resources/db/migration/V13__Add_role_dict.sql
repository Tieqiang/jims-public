-- Create table
create table ROLE_DICT
(
  ID        VARCHAR2(64) not null,
  ROLE_NAME VARCHAR2(128)
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
-- Add comments to the table
comment on table ROLE_DICT
  is '角色字典表';
-- Add comments to the columns
comment on column ROLE_DICT.ROLE_NAME
  is '角色名称';
-- Create/Recreate primary, unique and foreign key constraints
alter table ROLE_DICT
  add constraint PK_ROLE_DICT primary key (ID)
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
