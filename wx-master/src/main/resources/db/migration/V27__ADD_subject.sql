-- Add/modify columns
alter table SUBJECT add img VARCHAR2(1024);
-- Add comments to the columns
comment on column SUBJECT.img
  is '图片路径';

