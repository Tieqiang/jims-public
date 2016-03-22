-- Add/modify columns
alter table QUESTIONNAIRE_VS_SUBJECT modify SERIA_NO varchar2(64);
-- Create/Recreate primary, unique and foreign key constraints
alter table QUESTIONNAIRE_VS_SUBJECT
  add constraint PK_VS primary key (SERIA_NO);