-- Create table
create table ACCESS_TOOKEN
(
  ID            VARCHAR2(64),
  ACCESS_TOOKEN VARCHAR2(100),
  ACCESS_TIME   LONG,
  MP_ID         VARCHAR2(64)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table ACCESS_TOOKEN
  is '用于存储特定公众号的ACCESS_TOOKEN';
-- Add comments to the columns
comment on column ACCESS_TOOKEN.ID
  is '主键';
comment on column ACCESS_TOOKEN.ACCESS_TOOKEN
  is '获取到的access_tooken';
comment on column ACCESS_TOOKEN.ACCESS_TIME
  is '获取到ACCESS_TOOKEN的时间';
comment on column ACCESS_TOOKEN.MP_ID
  is '所属公众号';
