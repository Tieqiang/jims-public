create or replace view wx.pat_visit_q as
select  visit_id,next_of_kin,admission_date_time,discharge_date_time from MEDREC.pat_visit ;