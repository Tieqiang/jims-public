-- Create table
create table APP_CONFIGER_BASEINFO
(
  ID              VARCHAR2(64) not null,
  APP_NAME        VARCHAR2(16),
  PARAMETER_NO    NUMBER(6),
  PARAMETER_NAME  VARCHAR2(100),
  PARAINIT_VALUE  VARCHAR2(500),
  PARAMETER_SCOPE VARCHAR2(500),
  EXPLANATION     VARCHAR2(500)
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
comment on column APP_CONFIGER_BASEINFO.APP_NAME
  is '应用名';
comment on column APP_CONFIGER_BASEINFO.PARAMETER_NO
  is '参数序号';
comment on column APP_CONFIGER_BASEINFO.PARAMETER_NAME
  is '参数名称';
comment on column APP_CONFIGER_BASEINFO.PARAINIT_VALUE
  is '参数初始值';
comment on column APP_CONFIGER_BASEINFO.PARAMETER_SCOPE
  is '取值范围';
comment on column APP_CONFIGER_BASEINFO.EXPLANATION
  is '说明';
-- Create/Recreate primary, unique and foreign key constraints
alter table APP_CONFIGER_BASEINFO
  add constraint PK_APP_CONFIGER_BASEINFO primary key (ID)
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
alter table APP_CONFIGER_BASEINFO
  add constraint UQ_APP_CONFIGER_BASEINFO unique (APP_NAME, PARAMETER_NAME, PARAMETER_NO)
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
