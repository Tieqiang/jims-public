-- Add/modify columns
alter table REQUEST_MESSAGE modify CREATE_TIME long;

-- Drop columns
alter table APP_USER drop column APP_ID;


-- Create table
create table APP_USER_VS_GROUP
(
  OPEN_ID  VARCHAR2(64),
  GROUP_ID VARCHAR2(64),
  ID       VARCHAR2(64) not null
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the columns
comment on column APP_USER_VS_GROUP.OPEN_ID
  is '用户id';
comment on column APP_USER_VS_GROUP.GROUP_ID
  is '用户分组';
comment on column APP_USER_VS_GROUP.ID
  is '主键';
-- Create/Recreate primary, unique and foreign key constraints
alter table APP_USER_VS_GROUP
  add constraint APP_VS_GROUP_PK primary key (ID)
  using index
  tablespace JIMS_DATA
  pctfree 10
  initrans 2
  maxtrans 255;
