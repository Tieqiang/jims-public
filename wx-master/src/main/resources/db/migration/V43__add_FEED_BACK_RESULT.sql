-- Create table
create table FEED_BACK_RESULT
(
  ID                    VARCHAR2(64) not null,
  PAT_NAME              VARCHAR2(20),
  FEED_TIME             VARCHAR2(64),
  FEED_BACK_TARGET_ID   VARCHAR2(64),
  FEED_BACK_TARGET_NAME VARCHAR2(64),
  FEED_BACK_CONTENT     VARCHAR2(64)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table 
comment on table FEED_BACK_RESULT
  is '反馈结果表';
-- Add comments to the columns 
comment on column FEED_BACK_RESULT.ID
  is '主键';
comment on column FEED_BACK_RESULT.PAT_NAME
  is '反馈人姓名';
comment on column FEED_BACK_RESULT.FEED_TIME
  is '反馈时间';
comment on column FEED_BACK_RESULT.FEED_BACK_TARGET_ID
  is '反馈对象Id 外键';
comment on column FEED_BACK_RESULT.FEED_BACK_TARGET_NAME
  is '反馈对象名称';
comment on column FEED_BACK_RESULT.FEED_BACK_CONTENT
  is '反馈内容';
-- Create/Recreate primary, unique and foreign key constraints 
alter table FEED_BACK_RESULT
  add constraint FEED_BACK_RESULT_ID_PRIMARY primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table FEED_BACK_RESULT
  add constraint FEED_BACK_FORE foreign key (FEED_BACK_TARGET_ID)
  references FEED_BACK_TARGET (ID);
