-- Create table
create table CLINIC_SICKNESS
(
  ID      VARCHAR2(64) not null,
  NAME    VARCHAR2(64) not null,
  DEPT_ID VARCHAR2(64)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table CLINIC_SICKNESS
  is '疾病表';
-- Add comments to the columns
comment on column CLINIC_SICKNESS.ID
  is '主键';
comment on column CLINIC_SICKNESS.NAME
  is '疾病名称';
comment on column CLINIC_SICKNESS.DEPT_ID
  is '科室主键';
-- Create/Recreate primary, unique and foreign key constraints
alter table CLINIC_SICKNESS
  add constraint CLINIC_SICKNESS_ID primary key (ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table CLINIC_SICKNESS
  add constraint CLINIC_SICKNESS_DEPT_ID foreign key (DEPT_ID)
  references DEPT_DICT (ID);
