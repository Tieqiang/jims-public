-- Create table
create table FEED_BACK_TARGET
(
  ID               VARCHAR2(64) not null,
  FEED_TARGET      VARCHAR2(20),
  QUESTION_CONTENT VARCHAR2(64),
  OPTION_CONTENT   VARCHAR2(64)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table FEED_BACK_TARGET
  is '反馈目标表';
-- Add comments to the columns 
comment on column FEED_BACK_TARGET.ID
  is '主键';
comment on column FEED_BACK_TARGET.FEED_TARGET
  is '反馈的目标';
comment on column FEED_BACK_TARGET.QUESTION_CONTENT
  is '调查的问题内容';
comment on column FEED_BACK_TARGET.OPTION_CONTENT
  is '选项的内容';
-- Create/Recreate primary, unique and foreign key constraints 
alter table FEED_BACK_TARGET
  add constraint FEED_BACK_TARGET_FORE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
insert into feed_back_target values('123123123','医院','','');
insert into feed_back_target values('3452345','科室','','');
insert into feed_back_target values('3452341234','医生','','');
