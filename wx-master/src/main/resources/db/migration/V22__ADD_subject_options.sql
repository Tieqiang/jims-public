-- Add/modify columns
alter table SUBJECT_OPTIONS add image varchar2(1024);
-- Add comments to the columns
comment on column SUBJECT_OPTIONS.image
  is '选项图片';

