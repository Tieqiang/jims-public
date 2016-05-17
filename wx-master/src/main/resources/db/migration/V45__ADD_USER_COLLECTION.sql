-- Create table
create table USER_COLLECTION
(
  ID              VARCHAR2(64) not null,
  OPENID          VARCHAR2(64),
  DOC_ID          VARCHAR2(64),
  CLINIC_INDEX_ID VARCHAR2(64)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255;
-- Add comments to the table
comment on table USER_COLLECTION
  is '用户收藏医生表';
-- Add comments to the columns
comment on column USER_COLLECTION.ID
  is '主键';
comment on column USER_COLLECTION.OPENID
  is 'openId';
comment on column USER_COLLECTION.DOC_ID
  is '医生表 的主键';
comment on column USER_COLLECTION.CLINIC_INDEX_ID
  is '号别表的主键';
-- Create/Recreate primary, unique and foreign key constraints
alter table USER_COLLECTION
  add constraint USER_COLLECT_PRIMARY_KEY_ID primary key (ID)
  using index
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table USER_COLLECTION
  add constraint USER_COLLECT_CLINIC_INDEX foreign key (CLINIC_INDEX_ID)
  references CLINIC_INDEX (ID);
alter table USER_COLLECTION
  add constraint USER_COLLECT_FOREIGN_KEY foreign key (DOC_ID)
  references DOCT_INFO (ID);
