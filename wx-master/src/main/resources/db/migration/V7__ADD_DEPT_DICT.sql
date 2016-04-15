-- Create table
create table DEPT_DICT
(
  DEPT_CODE        VARCHAR2(9),
  DEPT_NAME        VARCHAR2(40),
  DEPT_ALIS        VARCHAR2(40),
  DEPT_ATTR        VARCHAR2(2),
  DEPT_OUTP_INP    VARCHAR2(1),
  INPUT_CODE       VARCHAR2(20),
  DEPT_DEVIDE_ATTR VARCHAR2(24),
  DEPT_LOCATION    VARCHAR2(80),
  DEPT_OTHER       VARCHAR2(50),
  DEPT_STOP_FLAG   VARCHAR2(1),
  DEPT_INFO        VARCHAR2(500),
  HOSPITAL_ID      VARCHAR2(64),
  ID               VARCHAR2(64) not null,
  PARENT_ID        VARCHAR2(64),
  DEPT_TYPE        VARCHAR2(50),
  DEPT_CLASS       VARCHAR2(50),
  END_DEPT         VARCHAR2(1)
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
comment on table DEPT_DICT
  is '医院科室字典表';
-- Add comments to the columns
comment on column DEPT_DICT.DEPT_CODE
  is '科室代码';
comment on column DEPT_DICT.DEPT_NAME
  is '科室名称';
comment on column DEPT_DICT.DEPT_ALIS
  is '科室别名';
comment on column DEPT_DICT.DEPT_ATTR
  is '科系属性';
comment on column DEPT_DICT.DEPT_OUTP_INP
  is '门诊住院科室标志如果是非末级科室不要填写该字段';
comment on column DEPT_DICT.INPUT_CODE
  is '拼音输入码';
comment on column DEPT_DICT.DEPT_DEVIDE_ATTR
  is '分类属性';
comment on column DEPT_DICT.DEPT_LOCATION
  is '科室位置';
comment on column DEPT_DICT.DEPT_OTHER
  is '用户自定标识';
comment on column DEPT_DICT.DEPT_STOP_FLAG
  is '停用标志';
comment on column DEPT_DICT.DEPT_INFO
  is '科室简介';
comment on column DEPT_DICT.HOSPITAL_ID
  is '所属医院';
comment on column DEPT_DICT.DEPT_TYPE
  is '科室类型，一般将科室分为：直接医疗类科室、医疗技术类科室、医疗辅助类科室、管理类科室、未纳入成本';
comment on column DEPT_DICT.DEPT_CLASS
  is '科室类别，一般为经营科室和其他';
comment on column DEPT_DICT.END_DEPT
  is '是否莫急科室';
-- Create/Recreate primary, unique and foreign key constraints
alter table DEPT_DICT
  add constraint PK_DEPT_DICT primary key (ID)
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
