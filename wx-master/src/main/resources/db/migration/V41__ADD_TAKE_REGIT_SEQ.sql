-- Create table
create table TAKE_REGIST_SEQ
(
  ID   VARCHAR2(64) not null,
  MATH VARCHAR2(10),
  TIME VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table TAKE_REGIST_SEQ
  is '取号序列';
-- Add comments to the columns 
comment on column TAKE_REGIST_SEQ.ID
  is '主键';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TAKE_REGIST_SEQ
  add constraint TAKE_REGIST_SEQ_FORE primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
