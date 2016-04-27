-- Create table
create table HOSPITAL_STAFF
(
  id        VARCHAR2(64) not null,
  open_id   VARCHAR2(64),
  person_id VARCHAR2(64),
  name      VARCHAR2(10)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table HOSPITAL_STAFF
  is '医院员工表';
-- Add comments to the columns 
comment on column HOSPITAL_STAFF.open_id
  is '答题人微信号';
comment on column HOSPITAL_STAFF.person_id
  is '答题人身份证号';
comment on column HOSPITAL_STAFF.name
  is '答题人';
-- Create/Recreate primary, unique and foreign key constraints 
alter table HOSPITAL_STAFF
  add constraint PK_HOSPITAL_STAFF primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table HOSPITAL_STAFF
  add constraint AK_KEY_2_HOSPITAL_S unique (PERSON_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
