-- Create table
create table ROLE_VS_MENU
(
  ID      VARCHAR2(64) not null,
  MENU_ID VARCHAR2(64),
  ROLE_ID VARCHAR2(64)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 192
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table ROLE_VS_MENU
  is '角色菜单对照表';
-- Create/Recreate primary, unique and foreign key constraints
alter table ROLE_VS_MENU
  add constraint PK_ROLE_VS_MENU primary key (ID)
  using index
  tablespace JIMS_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 128K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table ROLE_VS_MENU
  add constraint FK_ROLE_VS__REFERENCE_ROLE_DIC foreign key (ROLE_ID)
  references ROLE_DICT (ID);
