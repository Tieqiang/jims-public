-- Add/modify columns
alter table APP_USER add PAT_ID varchar2(64);
-- Add comments to the columns
comment on column APP_USER.PAT_ID is '当前挂号患者ID';