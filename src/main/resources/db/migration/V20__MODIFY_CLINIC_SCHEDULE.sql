-- Add/modify columns
alter table CLINIC_SCHEDULE add time_of_day varchar2(10);
-- Add comments to the columns
comment on column CLINIC_SCHEDULE.time_of_day
  is '时间段';