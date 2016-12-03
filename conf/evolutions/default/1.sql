# --- First database schema

# --- !Ups

set ignorecase true;

create table program (
  id                        bigint not null,
  name                      varchar(255) not null,
  constraint pk_program primary key (id))
;

create table student (
  id                        bigint not null,
  name                      varchar(255) not null,
  birthdate                timestamp,
  entrydate              timestamp,
  program_id                bigint,
  constraint pk_student primary key (id))
;

create sequence program_seq start with 1000;

create sequence student_seq start with 1000;

alter table student add constraint fk_student_program_1 foreign key (program_id) references program (id) on delete restrict on update restrict;
create index ix_student_program_1 on student (program_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists program;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists program_seq;

drop sequence if exists student_seq;
