create or replace view wx.pat_master_index as
select patient_id,name,sex,id_no from MEDREC.pat_master_index where id_no is not null  ;