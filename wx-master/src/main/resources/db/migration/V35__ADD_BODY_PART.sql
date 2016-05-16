-- Create table
create table body_part
(
  id   varchar2(64),
  name varchar2(64) not null,
  flag varchar2(4)
)
tablespace CBSH_DATA
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table
comment on table body_part
  is '身体部位表';
-- Add comments to the columns
comment on column body_part.id
  is '主键';
-- Create/Recreate primary, unique and foreign key constraints
alter table body_part
  add constraint id primary key (ID);

insert into body_part values('17','口腔','0');
insert into body_part values('16','面部','0');
insert into body_part values('15','臀部','0');
insert into body_part values('14','腰部','0');
insert into body_part values('13','会阴','0');
insert into body_part values('12','皮肤','0');
insert into body_part values('11','上肢','0');
insert into body_part values('10','颈部','0');
insert into body_part values('9','五官','0');
insert into body_part values('8','眼','0');
insert into body_part values('7','全身','0');
insert into body_part values('6','背部','0');
insert into body_part values('5','下肢','0');
insert into body_part values('4','颅部','0');
insert into body_part values('3','胸部','0');
insert into body_part values('2','腹部','0');
insert into body_part values('18','四肢','0');
