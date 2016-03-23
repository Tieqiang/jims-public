-- Create table
create table APP_CONFIGER_PARAMETER
(
  ID              VARCHAR2(64) not null,
  APP_NAME        VARCHAR2(10),
  DEPT_CODE       VARCHAR2(8),
  EMP_NO          VARCHAR2(10),
  PARAMETER_NAME  VARCHAR2(100),
  PARAMETER_VALUE VARCHAR2(500),
  POSITION        VARCHAR2(20),
  HOSPITAL_ID     VARCHAR2(64)
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
comment on column APP_CONFIGER_PARAMETER.APP_NAME
  is '应用名';
comment on column APP_CONFIGER_PARAMETER.DEPT_CODE
  is '适用科室';
comment on column APP_CONFIGER_PARAMETER.EMP_NO
  is '适用人员';
comment on column APP_CONFIGER_PARAMETER.PARAMETER_NAME
  is '参数名称';
comment on column APP_CONFIGER_PARAMETER.PARAMETER_VALUE
  is '参数初始值';
comment on column APP_CONFIGER_PARAMETER.POSITION
  is '位置';
comment on column APP_CONFIGER_PARAMETER.HOSPITAL_ID
  is '所属医院Id';
-- Create/Recreate primary, unique and foreign key constraints
alter table APP_CONFIGER_PARAMETER
  add constraint PK_APP_CONFIGER_PARAMETER primary key (ID)
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
alter table APP_CONFIGER_PARAMETER
  add constraint UQ_APP_CONFIGER_PARAMETER unique (APP_NAME, DEPT_CODE, EMP_NO, PARAMETER_NAME, HOSPITAL_ID)
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
