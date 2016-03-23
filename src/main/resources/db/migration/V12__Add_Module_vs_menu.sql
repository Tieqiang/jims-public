-- Create table
create table MODULE_VS_MENU
(
  ID        VARCHAR2(64) not null,
  MENU_ID   VARCHAR2(64),
  MODULE_ID VARCHAR2(64)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table MODULE_VS_MENU
  is '模块与菜单的对照';
-- Create/Recreate primary, unique and foreign key constraints
alter table MODULE_VS_MENU
  add constraint PK_MODULE_VS_MENU primary key (ID)
  using index
  tablespace JIMS_DATA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table MODULE_VS_MENU
  add constraint FK_MODULE_V_REFERENCE_MODUL_DI foreign key (MODULE_ID)
  references MODUL_DICT (ID);
