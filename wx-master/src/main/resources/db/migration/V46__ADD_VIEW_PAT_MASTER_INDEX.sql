create or replace view wx.pat_master_index as
select * from MEDREC.pat_master_index where id_no is not null  ;