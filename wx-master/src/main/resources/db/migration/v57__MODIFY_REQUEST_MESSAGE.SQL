--CHENXY
--修改rquest——message createTime 字段类型为Number
-- Add/modify columns
alter table REQUEST_MESSAGE add reply_flag varchar2(4);
-- Add comments to the columns
comment on column REQUEST_MESSAGE.reply_flag
  is '是否回复';
ALTER TABLE REQUEST_MESSAGE MODIFY(CREATE_TIME,NUMBER );
-- Add/modify columns
alter table REQUEST_MESSAGE add reply_content varchar2(2000);
-- Add comments to the columns
comment on column REQUEST_MESSAGE.reply_content
  is '回复内容';