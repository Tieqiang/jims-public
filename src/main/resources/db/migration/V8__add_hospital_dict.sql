-- Create table
create table HOSPITAL_DICT
(
  ID                     VARCHAR2(64) not null,
  HOSPITAL_NAME          VARCHAR2(100),
  UNIT_CODE              VARCHAR2(11),
  LOCATION               VARCHAR2(100),
  ZIP_CODE               VARCHAR2(6),
  ORGANIZATION_FULL_CODE VARCHAR2(30),
  PARENT_HOSPITAL        VARCHAR2(64)
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
comment on table HOSPITAL_DICT
  is '医疗机构信息表';
-- Add comments to the columns
comment on column HOSPITAL_DICT.ID
  is '医院主键
';
comment on column HOSPITAL_DICT.HOSPITAL_NAME
  is '医院名称';
comment on column HOSPITAL_DICT.UNIT_CODE
  is '医院编码';
comment on column HOSPITAL_DICT.LOCATION
  is '医院地址
';
comment on column HOSPITAL_DICT.ZIP_CODE
  is '邮政编码
';
comment on column HOSPITAL_DICT.ORGANIZATION_FULL_CODE
  is '医院组织结构完整代码';
-- Create/Recreate primary, unique and foreign key constraints
alter table HOSPITAL_DICT
  add constraint PK_HOSPITAL_DICT primary key (ID)
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
alter table DEPT_DICT
  add constraint FK_DEPT_HOSP foreign key (HOSPITAL_ID)
  references HOSPITAL_DICT (ID);
