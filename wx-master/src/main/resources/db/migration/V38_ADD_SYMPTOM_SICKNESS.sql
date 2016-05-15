-- Create table
create table SYMPTOM_SICKNESS
(
  ID          VARCHAR2(64) not null,
  SICKNESS_ID VARCHAR2(64),
  SYMPTOM_ID  VARCHAR2(64),
  SEX         VARCHAR2(4)
)
tablespace USERS
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
comment on table SYMPTOM_SICKNESS
  is '症状疾病中间表';
-- Add comments to the columns
comment on column SYMPTOM_SICKNESS.ID
  is '主键';
comment on column SYMPTOM_SICKNESS.SICKNESS_ID
  is '疾病表的主键';
comment on column SYMPTOM_SICKNESS.SYMPTOM_ID
  is '症状表主键';
comment on column SYMPTOM_SICKNESS.SEX
  is '性别 男or 女';
-- Create/Recreate primary, unique and foreign key constraints
alter table SYMPTOM_SICKNESS
  add constraint SYMPTOM_SICKNESS_ID primary key (ID)
  disable;
alter table SYMPTOM_SICKNESS
  add constraint SICKNESS_ID foreign key (SICKNESS_ID)
  references CLINIC_SICKNESS (ID)
  disable;
alter table SYMPTOM_SICKNESS
  add constraint SYMPTOM_ID foreign key (SYMPTOM_ID)
  references CLINIC_SYMPTOM (ID)
  disable;
