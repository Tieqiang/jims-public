-- Create table
create table STAFF_DICT
(
  ID           VARCHAR2(64) not null,
  LOGIN_NAME   VARCHAR2(12),
  PASSWORD     VARCHAR2(100),
  DEPT_ID      VARCHAR2(64),
  JOB          VARCHAR2(12),
  TITLE        VARCHAR2(50),
  HOSPITAL_ID  VARCHAR2(64),
  EMP_NO       VARCHAR2(20),
  SEX          VARCHAR2(4),
  NATION       VARCHAR2(50),
  CITIZENSHIP  VARCHAR2(50),
  DIPLOMA      VARCHAR2(50),
  PERSONAL_ID  VARCHAR2(18),
  WORK_PHONE   VARCHAR2(20),
  STOP_FLAG    NUMBER,
  NAME         VARCHAR2(64),
  INPUT_CODE   VARCHAR2(32),
  ACCT_DEPT_ID VARCHAR2(64),
  ID_NO        VARCHAR2(20)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 128
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table STAFF_DICT
  is '用户字典表
';
-- Add comments to the columns
comment on column STAFF_DICT.LOGIN_NAME
  is '登陆名';
comment on column STAFF_DICT.PASSWORD
  is '登陆密码';
comment on column STAFF_DICT.DEPT_ID
  is '所属科室
';
comment on column STAFF_DICT.JOB
  is '工作类型
';
comment on column STAFF_DICT.TITLE
  is '职称';
comment on column STAFF_DICT.HOSPITAL_ID
  is '医院';
comment on column STAFF_DICT.EMP_NO
  is '职工工号';
comment on column STAFF_DICT.SEX
  is '性别';
comment on column STAFF_DICT.NATION
  is '民族';
comment on column STAFF_DICT.CITIZENSHIP
  is '国籍';
comment on column STAFF_DICT.DIPLOMA
  is '学历';
comment on column STAFF_DICT.PERSONAL_ID
  is '身份证号';
comment on column STAFF_DICT.WORK_PHONE
  is '工作电话';
comment on column STAFF_DICT.STOP_FLAG
  is '是否停用';
comment on column STAFF_DICT.NAME
  is '用户名';
comment on column STAFF_DICT.ACCT_DEPT_ID
  is '所属核算单元';
comment on column STAFF_DICT.ID_NO
  is '身份证号';
-- Create/Recreate primary, unique and foreign key constraints
alter table STAFF_DICT
  add constraint PK_STAFF_DICT primary key (ID)
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
alter table STAFF_DICT
  add constraint UK_STAFF_DICT unique (LOGIN_NAME)
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
alter table STAFF_DICT
  add constraint FK_STAFF_DI_REFERENCE_DEPT_DIC foreign key (DEPT_ID)
  references DEPT_DICT (ID);
