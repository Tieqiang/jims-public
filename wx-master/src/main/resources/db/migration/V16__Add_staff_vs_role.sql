-- Create table
create table STAFF_VS_ROLE
(
  ID       VARCHAR2(64) not null,
  ROLE_ID  VARCHAR2(64),
  STAFF_ID VARCHAR2(64)
)
tablespace JIMS_DATA
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 1
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table STAFF_VS_ROLE
  is '用户角色对照表';
-- Create/Recreate primary, unique and foreign key constraints
alter table STAFF_VS_ROLE
  add constraint PK_STAFF_VS_ROLE primary key (ID)
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
alter table STAFF_VS_ROLE
  add constraint FK_STAFF_VS_REFERENCE_ROLE_DIC foreign key (ROLE_ID)
  references ROLE_DICT (ID);
alter table STAFF_VS_ROLE
  add constraint FK_STAFF_VS_REFERENCE_STAFF_DI foreign key (STAFF_ID)
  references STAFF_DICT (ID);
