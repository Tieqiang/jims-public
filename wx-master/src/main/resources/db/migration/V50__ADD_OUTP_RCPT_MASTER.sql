create or replace view wx.outp_rcpt_master as  select RCPT_NO,TOTAL_COSTS,TOTAL_CHARGES,NAME from OUTPBILL.outp_rcpt_master;



create or replace view wx.OUTP_BILL_ITEMS as  select ITEM_NAME,AMOUNT,UNITS,COSTS,CHARGES from OUTPBILL.OUTP_BILL_ITEMS
