-- Create table
create table body_part
(
  id   varchar2(64),
  name varchar2(64) not null,
  flag varchar2(4)
)
tablespace CBSH_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table body_part
  is '身体部位表';
-- Add comments to the columns
comment on column body_part.id
  is '主键';
-- Create/Recreate primary, unique and foreign key constraints
alter table body_part
  add constraint id primary key (ID);
