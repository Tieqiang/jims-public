--/*==============================================================*/
--/* DBMS name:      ORACLE Version 11g                           */
--/* Created on:     2016/3/2 10:46:51                            */
--/*==============================================================*/
--
--
--alter table ACCESS_TOOKEN
--   drop constraint FK_ACCESS_T_REFERENCE_WX_OPEN_;
--
--alter table ANSWER_RESULT
--   drop constraint FK_ANSWER_R_REFERENCE_ANSWER_S;
--
--alter table ANSWER_RESULT
--   drop constraint FK_ANSWER_R_REFERENCE_SUBJECT;
--
--alter table APP_USER
--   drop constraint FK_APP_USER_REFERENCE_APP_USER;
--
--alter table CLINIC_FOR_REGIST
--   drop constraint FK_CLINIC_F_REFERENCE_CLINIC_I;
--
--alter table CLINIC_INDEX
--   drop constraint FK_CLINIC_I_REFERENCE_DOCT_INF;
--
--alter table CLINIC_INDEX
--   drop constraint FK_CLINIC_I_REFERENCE_CLINIC_T;
--
--alter table CLINIC_MASTER
--   drop constraint FK_CLINIC_M_REFERENCE_PAY_WAY_;
--
--alter table CLINIC_SCHEDULE
--   drop constraint FK_CLINIC_S_REFERENCE_CLINIC_I;
--
--alter table CLINIC_TYPE_CHARGE
--   drop constraint FK_CLINIC_T_REFERENCE_CLINIC_T;
--
--alter table OPEN_ACCOUNT_MENU
--   drop constraint FK_OPEN_ACC_REFERENCE_WX_OPEN_;
--
--alter table PAT_VS_USER
--   drop constraint FK_PAT_VS_U_REFERENCE_PAT_INFO;
--
--alter table PAT_VS_USER
--   drop constraint FK_PAT_VS_U_REFERENCE_APP_USER;
--
--alter table QUESTIONNAIRE_VS_SUBJECT
--   drop constraint FK_QUESTION_REFERENCE_QUESTION;
--
--alter table QUESTIONNAIRE_VS_SUBJECT
--   drop constraint FK_QUESTION_REFERENCE_SUBJECT;
--
--alter table RESPONSE_ARTICLE
--   drop constraint FK_RESPONSE_REFERENCE_RESPONSE;
--
--alter table SUBJECT_OPTIONS
--   drop constraint FK_SUBJECT__REFERENCE_SUBJECT;
--
--drop table ACCESS_TOOKEN cascade constraints;
--
--drop table ANSWER_RESULT cascade constraints;
--
--drop table ANSWER_SHEET cascade constraints;
--
--drop table APP_USER cascade constraints;
--
--drop table APP_USER_GROUPS cascade constraints;
--
--drop table ARTICLE cascade constraints;
--
--drop table CLINIC_FOR_REGIST cascade constraints;
--
--drop table CLINIC_INDEX cascade constraints;
--
--drop table CLINIC_MASTER cascade constraints;
--
--drop table CLINIC_SCHEDULE cascade constraints;
--
--drop table CLINIC_TYPE_CHARGE cascade constraints;
--
--drop table CLINIC_TYPE_SETTING cascade constraints;
--
--drop table DOCT_INFO cascade constraints;
--
--drop table FODDER_DICT cascade constraints;
--
--drop table HOSPITAL_INFO cascade constraints;
--
--drop table MENU_TYPE_DICT cascade constraints;
--
--drop table OPEN_ACCOUNT_MENU cascade constraints;
--
--drop table PAT_INFO cascade constraints;
--
--drop table PAT_VS_USER cascade constraints;
--
--drop table PAY_WAY_DICT cascade constraints;
--
--drop table QUESTIONNAIRE_MODEL cascade constraints;
--
--drop table QUESTIONNAIRE_VS_SUBJECT cascade constraints;
--
--drop table REQUEST_MESSAGE cascade constraints;
--
--drop table RESPONSE_ARTICLE cascade constraints;
--
--drop table RESPONSE_MESSAGE cascade constraints;
--
--drop table SUBJECT cascade constraints;
--
--drop table SUBJECT_OPTIONS cascade constraints;
--
--drop table WX_OPEN_ACCOUNT_CONFIG cascade constraints;

/*==============================================================*/
/* Table: ACCESS_TOOKEN                                         */
/*==============================================================*/
create table ACCESS_TOOKEN
(
   ID                   VARCHAR2(64)         not null,
   ACCESS_TOOKEN        VARCHAR2(80),
   APP_ID               VARCHAR2(100),
   START_TIME           LONG,
   constraint PK_ACCESS_TOOKEN primary key (ID)
);

comment on table ACCESS_TOOKEN is
'微信公众号访问凭证';

/*==============================================================*/
/* Table: ANSWER_RESULT                                         */
/*==============================================================*/
create table ANSWER_RESULT
(
   ID                   VARCHAR(64)          not null,
   SUBJECT_ID           VARCHAR(64),
   ANSWER               VARCHAR(20),
   ANSWER_SHEET_ID      VARCHAR(64),
   constraint PK_ANSWER_RESULT primary key (ID)
);

/*==============================================================*/
/* Table: ANSWER_SHEET                                          */
/*==============================================================*/
create table ANSWER_SHEET
(
   ID                   VARCHAR(64)          not null,
   OPEN_ID              VARCHAR(64),
   PAT_ID               VARCHAR(64),
   QUESTIONNAIRE_ID     VARCHAR(64),
   CREATE_TIME          DATE,
   constraint PK_ANSWER_SHEET primary key (ID)
);

comment on table ANSWER_SHEET is
'答题记录表';

comment on column ANSWER_SHEET.OPEN_ID is
'答题人微信号';

comment on column ANSWER_SHEET.PAT_ID is
'答卷人';

comment on column ANSWER_SHEET.QUESTIONNAIRE_ID is
'问卷模板';

comment on column ANSWER_SHEET.CREATE_TIME is
'答题时间';

/*==============================================================*/
/* Table: APP_USER                                              */
/*==============================================================*/
create table APP_USER
(
   ID                   VARCHAR(64)          not null,
   SUBSCRIBE            NUMBER,
   OPEN_ID              VARCHAR(64),
   NICK_NAME            VARCHAR(64),
   SEX                  NUMBER,
   CITY                 VARCHAR(30),
   COUNTRY              VARCHAR(30),
   PROVINCE             VARCHAR(30),
   LANGUAGE             VARCHAR(20),
   HEAD_IMG_URL         VARCHAR(1024),
   SUBSCRBE_TIME        LONG,
   GROUP_ID             VARCHAR(64),
   REMARK               VARCHAR(100),
   APP_ID               VARCHAR(64),
   constraint PK_APP_USER primary key (ID),
   constraint AK_KEY_2_APP_USER unique (OPEN_ID)
);

comment on table APP_USER is
'用户关注人表';

comment on column APP_USER.SUBSCRIBE is
'用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，正常情况下值为1.';

comment on column APP_USER.OPEN_ID is
'微信用户标识';

comment on column APP_USER.NICK_NAME is
'昵称';

comment on column APP_USER.SEX is
'性别 1：男，2：女，0：未知';

comment on column APP_USER.CITY is
'用户所在城市';

comment on column APP_USER.COUNTRY is
'用户所在国家';

comment on column APP_USER.PROVINCE is
'用户所在省份';

comment on column APP_USER.LANGUAGE is
'用户语言';

comment on column APP_USER.HEAD_IMG_URL is
'用户头像';

comment on column APP_USER.SUBSCRBE_TIME is
'用户关注时间';

comment on column APP_USER.GROUP_ID is
'用户所在分组id';

comment on column APP_USER.REMARK is
'备注';

comment on column APP_USER.APP_ID is
'用户关注的公众平台号';

/*==============================================================*/
/* Table: APP_USER_GROUPS                                       */
/*==============================================================*/
create table APP_USER_GROUPS
(
   ID                   VARCHAR(64)          not null,
   NAME                 VARCHAR(20),
   COUNT                NUMBER,
   GROUP_STATUS         VARCHAR(2),
   APP_ID               VARCHAR(64),
   CREATE_TIME          DATE,
   OPERATOR             VARCHAR(64),
   UPDATE_TIME          DATE,
   UPDATE_OPERATOR      VARCHAR(64),
   constraint PK_APP_USER_GROUPS primary key (ID)
);

comment on table APP_USER_GROUPS is
'微信关注人用户分组表';

comment on column APP_USER_GROUPS.ID is
'微信分组id，由微信分配';

comment on column APP_USER_GROUPS.NAME is
'分组名称';

comment on column APP_USER_GROUPS.COUNT is
'组内用户数量';

comment on column APP_USER_GROUPS.GROUP_STATUS is
'分组状态';

comment on column APP_USER_GROUPS.APP_ID is
'微信id';

/*==============================================================*/
/* Table: ARTICLE                                               */
/*==============================================================*/
create table ARTICLE
(
   ID                   VARCHAR(64)          not null,
   TITLE                VARCHAR(50),
   THUMB_MEDIA_ID       VARCHAR(64),
   AUTHOR               VARCHAR(20),
   DIGEST               VARCHAR(500),
   SHOW_COVER_PIC       VARCHAR(2),
   CONTENT              BLOB,
   CONTENT_SOURCE_URL   VARCHAR(1024),
   APP_ID               VARCHAR(64),
   constraint PK_ARTICLE primary key (ID)
);

comment on table ARTICLE is
'图文素材表';

comment on column ARTICLE.SHOW_COVER_PIC is
'是否显示封面0不显示，1显示';

comment on column ARTICLE.CONTENT_SOURCE_URL is
'图文消息的原文地址，点击阅读原文的链接地址';

comment on column ARTICLE.APP_ID is
'所属公众号';

/*==============================================================*/
/* Table: CLINIC_FOR_REGIST                                     */
/*==============================================================*/
create table CLINIC_FOR_REGIST
(
   ID                   VARCHAR(64)          not null,
   CLINIC_INDEX_ID      VARCHAR(64),
   CLINIC_DATE          DATE,
   TIME_DESC            VARCHAR(10),
   REGISTRATION_LIMITS  NUMBER,
   APPOINTMENT_LIMITS   NUMBER,
   CURRENT_NO           NUMBER,
   REGISTRATION_NUM     NUMBER,
   REGIST_PRICE         NUMBER(5,2),
   constraint PK_CLINIC_FOR_REGIST primary key (ID)
);

comment on table CLINIC_FOR_REGIST is
'号表';

comment on column CLINIC_FOR_REGIST.CLINIC_INDEX_ID is
'所属号别';

comment on column CLINIC_FOR_REGIST.CLINIC_DATE is
'号表日期';

comment on column CLINIC_FOR_REGIST.TIME_DESC is
'描述';

comment on column CLINIC_FOR_REGIST.REGISTRATION_LIMITS is
'限号数';

comment on column CLINIC_FOR_REGIST.APPOINTMENT_LIMITS is
'限约号数';

comment on column CLINIC_FOR_REGIST.CURRENT_NO is
'当前号';

comment on column CLINIC_FOR_REGIST.REGISTRATION_NUM is
'当日已经挂号数';

/*==============================================================*/
/* Table: CLINIC_INDEX                                          */
/*==============================================================*/
create table CLINIC_INDEX
(
   ID                   VARCHAR(64)          not null,
   CLINIC_LABEL         VARCHAR(20),
   CLINIC_DEPT          VARCHAR(10),
   DOCTOR_ID            VARCHAR(64),
   CLINIC_TYPE_ID       VARCHAR(20),
   constraint PK_CLINIC_INDEX primary key (ID),
   constraint AK_KEY_2_CLINIC_I unique (DOCTOR_ID)
);

comment on table CLINIC_INDEX is
'号别';

comment on column CLINIC_INDEX.CLINIC_LABEL is
'号别名称';

comment on column CLINIC_INDEX.CLINIC_DEPT is
'所属科室';

comment on column CLINIC_INDEX.DOCTOR_ID is
'所属医生';

comment on column CLINIC_INDEX.CLINIC_TYPE_ID is
'所属号类';

/*==============================================================*/
/* Table: CLINIC_MASTER                                         */
/*==============================================================*/
create table CLINIC_MASTER
(
   ID                   VARCHAR(64)          not null,
   VISIT_DATE           DATE,
   CLINC_REGIST_ID      VARCHAR(64),
   PATIENT_ID           VARCHAR(64),
   PAY_WAY_ID           VARCHAR(64),
   REGIST_FEE           NUMBER(5,2),
   CLINIC_FEE           NUMBER(5,2),
   OTHER_FEE            NUMBER(5,2),
   CLINIC_CHARGE        NUMBER(5,2),
   TAKE_STATUS          VARCHAR(20)          not null,
   TAKE_TIME            DATE,
   REGIST_DATE          DATE,
   ACCT_DATE_TIME       DATE,
   ACCT_NO              VARCHAR(64),
   ACCT_OPERATOR        VARCHAR(64),
   constraint PK_CLINIC_MASTER primary key (ID)
);

comment on column CLINIC_MASTER.VISIT_DATE is
'就诊日期';

comment on column CLINIC_MASTER.TAKE_STATUS is
'取号状态，0未取号，1已经取号';

comment on column CLINIC_MASTER.REGIST_DATE is
'挂号时间';

comment on column CLINIC_MASTER.ACCT_DATE_TIME is
'结账时间';

/*==============================================================*/
/* Table: CLINIC_SCHEDULE                                       */
/*==============================================================*/
create table CLINIC_SCHEDULE
(
   ID                   VARCHAR(64)          not null,
   CLINIC_INDEX_ID      VARCHAR(64),
   DAY_OF_WEEK          VARCHAR(10),
   REGISTRATION_LIMITS  NUMBER,
   constraint PK_CLINIC_SCHEDULE primary key (ID)
);

comment on table CLINIC_SCHEDULE is
'出诊安排';

comment on column CLINIC_SCHEDULE.ID is
'主键';

comment on column CLINIC_SCHEDULE.CLINIC_INDEX_ID is
'所属号别';

comment on column CLINIC_SCHEDULE.DAY_OF_WEEK is
'挂号时间';

comment on column CLINIC_SCHEDULE.REGISTRATION_LIMITS is
'限号数';

/*==============================================================*/
/* Table: CLINIC_TYPE_CHARGE                                    */
/*==============================================================*/
create table CLINIC_TYPE_CHARGE
(
   ID                   VARCHAR(64)          not null,
   CLINIC_TYPE_NAME     VARCHAR(100),
   CLINIC_TYPE_CODE     VARCHAR(10),
   CHARGE_ITEM          VARCHAR(20),
   PRICE_ITEM           VARCHAR(20),
   PRICE                NUMBER,
   CLINIC_TYPE_ID       VARCHAR(64),
   constraint PK_CLINIC_TYPE_CHARGE primary key (ID)
);

comment on table CLINIC_TYPE_CHARGE is
'号类收费定义';

comment on column CLINIC_TYPE_CHARGE.CLINIC_TYPE_NAME is
'诊疗项目名称';

comment on column CLINIC_TYPE_CHARGE.CLINIC_TYPE_CODE is
'诊疗项目代码';

comment on column CLINIC_TYPE_CHARGE.CHARGE_ITEM is
'收费项目名称';

comment on column CLINIC_TYPE_CHARGE.PRICE_ITEM is
'收费项目代码';

/*==============================================================*/
/* Table: CLINIC_TYPE_SETTING                                   */
/*==============================================================*/
create table CLINIC_TYPE_SETTING
(
   ID                   VARCHAR(64)          not null,
   CLINIC_TYPE          VARCHAR(8),
   HOSPITAL_ID          VARCHAR(64),
   APP_ID               VARCHAR(64),
   constraint PK_CLINIC_TYPE_SETTING primary key (ID),
   constraint AK_KEY_2_CLINIC_T unique (CLINIC_TYPE)
);

comment on table CLINIC_TYPE_SETTING is
'门诊号类表';

comment on column CLINIC_TYPE_SETTING.CLINIC_TYPE is
'号类名称';

comment on column CLINIC_TYPE_SETTING.APP_ID is
'所属公众号';

/*==============================================================*/
/* Table: DOCT_INFO                                             */
/*==============================================================*/
create table DOCT_INFO
(
   ID                   VARCHAR(64)          not null,
   NAME                 VARCHAR(20)          not null,
   TITLE                VARCHAR(10),
   HOSPITAL_ID          VARCHAR(64),
   HEAD_URL             VARCHAR(1024),
   DESCRIPTION          BLOB,
   constraint PK_DOCT_INFO primary key (ID)
);

comment on table DOCT_INFO is
'医生信息表';

/*==============================================================*/
/* Table: FODDER_DICT                                           */
/*==============================================================*/
create table FODDER_DICT
(
   ID                   VARCHAR(64),
   MEDIA_ID             VARCHAR(64),
   TYPE                 VARCHAR(20),
   CREATED_AT           LONG,
   STORE_STATUS         VARCHAR(2)
);

comment on column FODDER_DICT.STORE_STATUS is
'存储状态永久存储，临时存储';

/*==============================================================*/
/* Table: HOSPITAL_INFO                                         */
/*==============================================================*/
create table HOSPITAL_INFO
(
   ID                   VARCHAR(64)          not null,
   HOSPITAL_ID          VARCHAR(64),
   APP_ID               VARCHAR(64),
   INFO_URL             VARCHAR(1024),
   CONTENT              BLOB,
   constraint PK_HOSPITAL_INFO primary key (ID)
);

/*==============================================================*/
/* Table: MENU_TYPE_DICT                                        */
/*==============================================================*/
create table MENU_TYPE_DICT
(
   ID                   VARCHAR2(64)         not null,
   TYPE_DESIGN          VARCHAR2(100),
   TYPE_CODE            VARCHAR(50),
   constraint PK_MENU_TYPE_DICT primary key (ID)
);

comment on table MENU_TYPE_DICT is
'自定义菜单类型字典';

comment on column MENU_TYPE_DICT.TYPE_DESIGN is
'类型描述';

comment on column MENU_TYPE_DICT.TYPE_CODE is
'类型码';

/*==============================================================*/
/* Table: OPEN_ACCOUNT_MENU                                     */
/*==============================================================*/
create table OPEN_ACCOUNT_MENU
(
   ID                   VARCHAR(64)          not null,
   PARENT_ID            VARCHAR(64),
   TYPE                 VARCHAR(50),
   NAME                 VARCHAR(40),
   KEY                  VARCHAR(128),
   URL                  VARCHAR(1024),
   MEDIA_ID             VARCHAR(100),
   HOSPITAL_ID          VARCHAR(64),
   USE_STATUS           VARCHAR(2),
   CREATE_TIME          DATE,
   OPERATOR             VARCHAR(64),
   LAST_UPDATE_TIME     DATE,
   LAST_UPDAT_OPERATOR  VARCHAR(64),
   APP_ID               VARCHAR(64)          not null,
   constraint PK_OPEN_ACCOUNT_MENU primary key (ID)
);

comment on table OPEN_ACCOUNT_MENU is
'公众号自定义菜单';

comment on column OPEN_ACCOUNT_MENU.ID is
'
主键';

comment on column OPEN_ACCOUNT_MENU.PARENT_ID is
'父菜单';

comment on column OPEN_ACCOUNT_MENU.TYPE is
'菜单类型';

comment on column OPEN_ACCOUNT_MENU.NAME is
'菜单名称';

comment on column OPEN_ACCOUNT_MENU.KEY is
'文本信息';

comment on column OPEN_ACCOUNT_MENU.URL is
'链接地址';

comment on column OPEN_ACCOUNT_MENU.MEDIA_ID is
'音频视频资源';

/*==============================================================*/
/* Table: PAT_INFO                                              */
/*==============================================================*/
create table PAT_INFO
(
   ID                   VARCHAR(64)          not null,
   ID_CARD              VARCHAR(20),
   CELLPHONE            VARCHAR(20),
   NAME                 VARCHAR(30),
   DEFAULT_FLAG         VARCHAR(2),
   SEX                  VARCHAR(2),
   BIRTHDAY             DATE,
   constraint PK_PAT_INFO primary key (ID)
);

comment on table PAT_INFO is
'患者和关注人关联表';

comment on column PAT_INFO.ID_CARD is
'身份证号';

comment on column PAT_INFO.CELLPHONE is
'手机号';

comment on column PAT_INFO.NAME is
'患者姓名';

comment on column PAT_INFO.DEFAULT_FLAG is
'是否为默认';

/*==============================================================*/
/* Table: PAT_VS_USER                                           */
/*==============================================================*/
create table PAT_VS_USER
(
   PAT_ID               VARCHAR(64),
   USER_ID              VARCHAR(64),
   constraint AK_KEY_1_PAT_VS_U unique (PAT_ID, USER_ID)
);

comment on table PAT_VS_USER is
'病人和微信关注者对照关系表';

/*==============================================================*/
/* Table: PAY_WAY_DICT                                          */
/*==============================================================*/
create table PAY_WAY_DICT
(
   ID                   VARCHAR(64)          not null,
   PAY_WAY_NAME         VARCHAR(20),
   constraint PK_PAY_WAY_DICT primary key (ID)
);

comment on table PAY_WAY_DICT is
'支付方式字典表';

/*==============================================================*/
/* Table: QUESTIONNAIRE_MODEL                                   */
/*==============================================================*/
create table QUESTIONNAIRE_MODEL
(
   ID                   VARCHAR(64)          not null,
   TITLE                VARCHAR(100),
   MEMO                 VARCHAR(400),
   CREATE_PERSON        VARCHAR(20),
   TOTAL_NUMBERS        NUMBER,
   APP_ID               VARCHAR(64),
   constraint PK_QUESTIONNAIRE_MODEL primary key (ID)
);

comment on table QUESTIONNAIRE_MODEL is
'问卷调查';

comment on column QUESTIONNAIRE_MODEL.TITLE is
'问卷名称';

comment on column QUESTIONNAIRE_MODEL.MEMO is
'问卷描述';

comment on column QUESTIONNAIRE_MODEL.TOTAL_NUMBERS is
'总题数';

comment on column QUESTIONNAIRE_MODEL.APP_ID is
'所属公众号';

/*==============================================================*/
/* Table: QUESTIONNAIRE_VS_SUBJECT                              */
/*==============================================================*/
create table QUESTIONNAIRE_VS_SUBJECT
(
   QUESTIONNAIRE_ID     VARCHAR(64),
   SUBJECT_ID           VARCHAR(64),
   SERIA_NO             NUMBER,
   constraint AK_KEY_1_QUESTION unique (QUESTIONNAIRE_ID, SUBJECT_ID, SERIA_NO)
);

/*==============================================================*/
/* Table: REQUEST_MESSAGE                                       */
/*==============================================================*/
create table REQUEST_MESSAGE
(
   ID                   VARCHAR(64)          not null,
   TO_USER_NAME         VARCHAR(64),
   FROM_USER_NAME       VARCHAR(64),
   CREATE_TIME          LONG,
   MSG_TYPE             VARCHAR(64),
   MSG_ID               VARCHAR(64),
   CONTENT              VARCHAR(1200),
   PIC_URL              VARCHAR(1024),
   MEDIA_ID             VARCHAR(64),
   FORMAT               VARCHAR(10),
   LOCATION_X           VARCHAR(20),
   LOCATION_Y           VARCHAR(20),
   SCALE                VARCHAR(10),
   LABEL                VARCHAR(200),
   TITLE                varCHAR(100),
   DESCRIPTION          VARCHAR(200),
   URL                  VARCHAR(1024),
   THUMB_MEDIA_ID       VARCHAR(64),
   APP_ID               VARCHAR(64),
   RESPONSE_STATUS      VARCHAR(2),
   EVENT_KEY            VARCHAR(100),
   EVENT                VARCHAR(10),
   TICKET               VARCHAR(100),
   LATITUDE             VARCHAR(20),
   LONGITUDE            VARCHAR(20),
   PRECISION            VARCHAR(20),
   constraint PK_REQUEST_MESSAGE primary key (ID)
);

comment on table REQUEST_MESSAGE is
'收到的消息';

comment on column REQUEST_MESSAGE.TO_USER_NAME is
'接受者';

comment on column REQUEST_MESSAGE.FROM_USER_NAME is
'发送者';

comment on column REQUEST_MESSAGE.CREATE_TIME is
'发送时间';

comment on column REQUEST_MESSAGE.MSG_TYPE is
'消息类型';

comment on column REQUEST_MESSAGE.MSG_ID is
'消息ID';

comment on column REQUEST_MESSAGE.CONTENT is
'文本内容';

comment on column REQUEST_MESSAGE.PIC_URL is
'图文地址';

comment on column REQUEST_MESSAGE.MEDIA_ID is
'媒体ID';

comment on column REQUEST_MESSAGE.FORMAT is
'语音格式';

comment on column REQUEST_MESSAGE.LOCATION_X is
'地理位置纬度';

comment on column REQUEST_MESSAGE.LOCATION_Y is
'地理位置经度';

comment on column REQUEST_MESSAGE.SCALE is
'地图缩放比例';

comment on column REQUEST_MESSAGE.TITLE is
'消息标题';

comment on column REQUEST_MESSAGE.DESCRIPTION is
'消息描述';

comment on column REQUEST_MESSAGE.URL is
'消息链接';

comment on column REQUEST_MESSAGE.THUMB_MEDIA_ID is
'视频消息的缩略图的媒体ID';

comment on column REQUEST_MESSAGE.EVENT_KEY is
'事件KEY值，qrscene_为前缀，后面为二维码的参数值';

comment on column REQUEST_MESSAGE.EVENT is
'事件类型，subscribe(订阅)、unsubscribe(取消订阅)';

comment on column REQUEST_MESSAGE.LATITUDE is
'地理位置维度';

comment on column REQUEST_MESSAGE.LONGITUDE is
'地理位置经度';

comment on column REQUEST_MESSAGE.PRECISION is
'地理位置精度';

/*==============================================================*/
/* Table: RESPONSE_ARTICLE                                      */
/*==============================================================*/
create table RESPONSE_ARTICLE
(
   ID                   VARCHAR(64)          not null,
   TITLE                VARCHAR(100),
   DESCRIPTION          VARCHAR(400),
   PIC_URL              VARCHAR(1024),
   URL                  VARCHAR(1024),
   OPERATOR             VARCHAR(64),
   CREATE_TIME          LONG,
   RESPONSE_ID          VARCHAR(64),
   constraint PK_RESPONSE_ARTICLE primary key (ID)
);

comment on table RESPONSE_ARTICLE is
'图文消息';

/*==============================================================*/
/* Table: RESPONSE_MESSAGE                                      */
/*==============================================================*/
create table RESPONSE_MESSAGE
(
   ID                   VARCHAR(64)          not null,
   TO_USER_NAME         VARCHAR(64),
   FROM_USER_NAME       VARCHAR(64),
   CREATE_TIME          LONG,
   MSG_TYPE             VARCHAR(64),
   CONTENT              VARCHAR(1200),
   MEDIA_ID             VARCHAR(64),
   TITLE                varCHAR(100),
   DESCRIPTION          VARCHAR(200),
   URL                  VARCHAR(1024),
   THUMB_MEDIA_ID       VARCHAR(64),
   APP_ID               VARCHAR(64),
   RESPONSE_STATUS      VARCHAR(2),
   MUSIC_URL            VARCHAR(1024),
   HQ_MUSIC_URL         VARCHAR(1024),
   constraint PK_RESPONSE_MESSAGE primary key (ID)
);

comment on table RESPONSE_MESSAGE is
'响应消息';

comment on column RESPONSE_MESSAGE.TO_USER_NAME is
'接受者';

comment on column RESPONSE_MESSAGE.FROM_USER_NAME is
'发送者';

comment on column RESPONSE_MESSAGE.CREATE_TIME is
'发送时间';

comment on column RESPONSE_MESSAGE.MSG_TYPE is
'消息类型';

comment on column RESPONSE_MESSAGE.CONTENT is
'文本内容';

comment on column RESPONSE_MESSAGE.MEDIA_ID is
'媒体ID';

comment on column RESPONSE_MESSAGE.TITLE is
'消息标题';

comment on column RESPONSE_MESSAGE.DESCRIPTION is
'消息描述';

comment on column RESPONSE_MESSAGE.URL is
'消息链接';

comment on column RESPONSE_MESSAGE.THUMB_MEDIA_ID is
'视频消息的缩略图的媒体ID';

comment on column RESPONSE_MESSAGE.MUSIC_URL is
'音乐链接';

comment on column RESPONSE_MESSAGE.HQ_MUSIC_URL is
'高质量音乐链接';

/*==============================================================*/
/* Table: SUBJECT                                               */
/*==============================================================*/
create table SUBJECT
(
   ID                   VARCHAR(64)          not null,
   QUESTION_CONTENT     VARCHAR(500),
   QUESTION_TYPE        VARCHAR(2),
   PRE_ANSWER           VARCHAR(20),
   constraint PK_SUBJECT primary key (ID)
);

comment on table SUBJECT is
'题干表';

comment on column SUBJECT.QUESTION_TYPE is
'问题类型，单选为0 ，多选为1';

/*==============================================================*/
/* Table: SUBJECT_OPTIONS                                       */
/*==============================================================*/
create table SUBJECT_OPTIONS
(
   ID                   VARCHAR(64)          not null,
   SUBJECT_ID           VARCHAR(64),
   OPT_CONTENT          VARCHAR(70),
   OPT_STATUS           VARCHAR(2),
   constraint PK_SUBJECT_OPTIONS primary key (ID)
);

comment on table SUBJECT_OPTIONS is
'题目选项';

comment on column SUBJECT_OPTIONS.OPT_STATUS is
'删除状态';

/*==============================================================*/
/* Table: WX_OPEN_ACCOUNT_CONFIG                                */
/*==============================================================*/
create table WX_OPEN_ACCOUNT_CONFIG
(
   ID                   varchar2(64)         not null,
   OPEN_NAME            varchar2(100),
   APP_ID               varchar2(64),
   APP_SECRET           varchar2(64),
   HOSPITAL_ID          varchar2(64),
   JS_ROUT              varchar2(100),
   URL                  VARCHAR(100),
   TOOKEN               VARCHAR(64),
   constraint PK_WX_OPEN_ACCOUNT_CONFIG primary key (ID),
   constraint AK_WX_OPEN_ID_UK_WX_OPEN_ unique (APP_ID)
);

comment on table WX_OPEN_ACCOUNT_CONFIG is
'微信公众号';

comment on column WX_OPEN_ACCOUNT_CONFIG.OPEN_NAME is
'公众号名称';

comment on column WX_OPEN_ACCOUNT_CONFIG.APP_ID is
'公众号ID
';

comment on column WX_OPEN_ACCOUNT_CONFIG.APP_SECRET is
'公众号唯一码';

comment on column WX_OPEN_ACCOUNT_CONFIG.HOSPITAL_ID is
'所属医院';

comment on column WX_OPEN_ACCOUNT_CONFIG.JS_ROUT is
'js域名';

comment on column WX_OPEN_ACCOUNT_CONFIG.URL is
'公众号接口地址';

alter table ACCESS_TOOKEN
   add constraint FK_ACCESS_T_REFERENCE_WX_OPEN_ foreign key (APP_ID)
      references WX_OPEN_ACCOUNT_CONFIG (APP_ID);

alter table ANSWER_RESULT
   add constraint FK_ANSWER_R_REFERENCE_ANSWER_S foreign key (ANSWER_SHEET_ID)
      references ANSWER_SHEET (ID);

alter table ANSWER_RESULT
   add constraint FK_ANSWER_R_REFERENCE_SUBJECT foreign key (SUBJECT_ID)
      references SUBJECT (ID);

alter table APP_USER
   add constraint FK_APP_USER_REFERENCE_APP_USER foreign key (GROUP_ID)
      references APP_USER_GROUPS (ID);

alter table CLINIC_FOR_REGIST
   add constraint FK_CLINIC_F_REFERENCE_CLINIC_I foreign key (CLINIC_INDEX_ID)
      references CLINIC_INDEX (ID);

alter table CLINIC_INDEX
   add constraint FK_CLINIC_I_REFERENCE_DOCT_INF foreign key (DOCTOR_ID)
      references DOCT_INFO (ID);

alter table CLINIC_INDEX
   add constraint FK_CLINIC_I_REFERENCE_CLINIC_T foreign key (CLINIC_TYPE_ID)
      references CLINIC_TYPE_SETTING (ID);

alter table CLINIC_MASTER
   add constraint FK_CLINIC_M_REFERENCE_PAY_WAY_ foreign key (PAY_WAY_ID)
      references PAY_WAY_DICT (ID);

alter table CLINIC_SCHEDULE
   add constraint FK_CLINIC_S_REFERENCE_CLINIC_I foreign key (CLINIC_INDEX_ID)
      references CLINIC_INDEX (ID);

alter table CLINIC_TYPE_CHARGE
   add constraint FK_CLINIC_T_REFERENCE_CLINIC_T foreign key (CLINIC_TYPE_ID)
      references CLINIC_TYPE_SETTING (ID);

alter table OPEN_ACCOUNT_MENU
   add constraint FK_OPEN_ACC_REFERENCE_WX_OPEN_ foreign key (APP_ID)
      references WX_OPEN_ACCOUNT_CONFIG (APP_ID);

alter table PAT_VS_USER
   add constraint FK_PAT_VS_U_REFERENCE_PAT_INFO foreign key (PAT_ID)
      references PAT_INFO (ID);

alter table PAT_VS_USER
   add constraint FK_PAT_VS_U_REFERENCE_APP_USER foreign key (USER_ID)
      references APP_USER (ID);

alter table QUESTIONNAIRE_VS_SUBJECT
   add constraint FK_QUESTION_REFERENCE_QUESTION foreign key (QUESTIONNAIRE_ID)
      references QUESTIONNAIRE_MODEL (ID);

alter table QUESTIONNAIRE_VS_SUBJECT
   add constraint FK_QUESTION_REFERENCE_SUBJECT foreign key (SUBJECT_ID)
      references SUBJECT (ID);

alter table RESPONSE_ARTICLE
   add constraint FK_RESPONSE_REFERENCE_RESPONSE foreign key (RESPONSE_ID)
      references RESPONSE_MESSAGE (ID);

alter table SUBJECT_OPTIONS
   add constraint FK_SUBJECT__REFERENCE_SUBJECT foreign key (SUBJECT_ID)
      references SUBJECT (ID);

