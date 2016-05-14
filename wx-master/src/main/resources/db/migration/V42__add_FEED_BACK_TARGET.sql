-- Add/modify columns
alter table WX_OPEN_ACCOUNT_CONFIG add METCH_ID varchar2(64);
alter table WX_OPEN_ACCOUNT_CONFIG add key varchar2(100);
-- Add comments to the columns
comment on column WX_OPEN_ACCOUNT_CONFIG.METCH_ID
  is '商号';
comment on column WX_OPEN_ACCOUNT_CONFIG.key
  is '商号key';
