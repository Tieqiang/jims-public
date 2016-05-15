-- Create table
create table CLINIC_SYMPTOM
(
  ID           VARCHAR2(64) not null,
  NAME         VARCHAR2(64) not null,
  BODY_PART_ID VARCHAR2(64),
  SEX          VARCHAR2(4)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table CLINIC_SYMPTOM
  is '症状表';
-- Add comments to the columns 
comment on column CLINIC_SYMPTOM.ID
  is '主键';
comment on column CLINIC_SYMPTOM.NAME
  is '症状名称';
comment on column CLINIC_SYMPTOM.BODY_PART_ID
  is '身体部位表主键';
comment on column CLINIC_SYMPTOM.SEX
  is '性别  | 男 or 女';
-- Create/Recreate primary, unique and foreign key constraints 
alter table CLINIC_SYMPTOM
  add constraint CLINIC_SYMPTOM_ID primary key (ID)
  using index 
  tablespace USERS
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
alter table CLINIC_SYMPTOM
  add constraint BODY_SYMPTOM_ID foreign key (BODY_PART_ID)
  references BODY_PART (ID);
