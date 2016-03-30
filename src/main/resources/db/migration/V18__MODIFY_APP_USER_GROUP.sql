-- Add/modify columns
alter table APP_USER_GROUPS add group_id varchar2(64);
-- Add comments to the columns
comment on column APP_USER_GROUPS.group_id
  is '微信公众平台分组ID';

-- Create/Recreate primary, unique and foreign key constraints
alter table APP_USER_GROUPS
  add constraint AK_GROUP_ID unique (GROUP_ID);