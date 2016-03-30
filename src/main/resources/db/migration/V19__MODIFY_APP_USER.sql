-- Create/Recreate primary, unique and foreign key constraints
alter table APP_USER
  drop constraint FK_APP_USER_REFERENCE_APP_USER;
alter table APP_USER
  add constraint FK_APP_USER_REFERENCE_APP_USER foreign key (GROUP_ID)
  references APP_USER_GROUPS (GROUP_ID);
