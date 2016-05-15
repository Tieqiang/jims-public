--drop table
drop table OUTPADM.CLINIC_MASTER
-- Create table
create table OUTPADM.CLINIC_MASTER
(
  VISIT_DATE            DATE not null,
  VISIT_NO              NUMBER(5) not null,
  CLINIC_LABEL          VARCHAR2(100),
  VISIT_TIME_DESC       VARCHAR2(8),
  SERIAL_NO             NUMBER(10),
  PATIENT_ID            VARCHAR2(100),
  NAME                  VARCHAR2(40),
  NAME_PHONETIC         VARCHAR2(16),
  SEX                   VARCHAR2(4),
  AGE                   NUMBER(3),
  IDENTITY              VARCHAR2(20),
  CHARGE_TYPE           VARCHAR2(8),
  INSURANCE_TYPE        VARCHAR2(16),
  INSURANCE_NO          VARCHAR2(18),
  UNIT_IN_CONTRACT      VARCHAR2(40),
  CLINIC_TYPE           VARCHAR2(64),
  FIRST_VISIT_INDICATOR NUMBER(1),
  VISIT_DEPT            VARCHAR2(8),
  VISIT_SPECIAL_CLINIC  VARCHAR2(16),
  DOCTOR                VARCHAR2(20),
  MR_PROVIDE_INDICATOR  NUMBER(1),
  REGISTRATION_STATUS   NUMBER(1),
  REGISTERING_DATE      DATE,
  SYMPTOM               VARCHAR2(40),
  REGIST_FEE            NUMBER(5,2),
  CLINIC_FEE            NUMBER(5,2),
  OTHER_FEE             NUMBER(5,2),
  CLINIC_CHARGE         NUMBER(5,2),
  OPERATOR              VARCHAR2(8),
  RETURNED_DATE         DATE,
  RETURNED_OPERATOR     VARCHAR2(8),
  MODE_CODE             CHAR(1),
  CARD_NAME             VARCHAR2(16),
  CARD_NO               VARCHAR2(20),
  ACCT_DATE_TIME        DATE,
  ACCT_NO               VARCHAR2(6),
  PAY_WAY               VARCHAR2(8),
  MR_PROVIDED_INDICATOR NUMBER(1),
  INVOICE_NO            VARCHAR2(20),
  CLINIC_NO             VARCHAR2(15),
  MR_NO                 VARCHAR2(20),
  ISPRN                 VARCHAR2(1) default 'N',
  PAT_TYPE              VARCHAR2(16),
  VALID_DATE            DATE,
  AUTO_FLAG             VARCHAR2(2),
  PRINT_OPERATOR        VARCHAR2(8),
  PE_VISIT_ID           NUMBER(3),
  MAILING_ADDRESS       VARCHAR2(200)
)
tablespace TSP_OUTPADM
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints
alter table OUTPADM.CLINIC_MASTER
  add constraint PK_CLINIC_MASTER primary key (VISIT_DATE, VISIT_NO)
  using index
  tablespace TSP_OUTPADM
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 63M
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Grant/Revoke object privileges
grant select, insert, update, delete on OUTPADM.CLINIC_MASTER to PUBLIC;
