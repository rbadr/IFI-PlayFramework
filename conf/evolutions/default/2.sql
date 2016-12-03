# --- Sample dataset

# --- !Ups

insert into program (id,name) values (  1,'E-SERVICES');
insert into program (id,name) values (  2,'IAGL');
insert into program (id,name) values (  3,'IVI');
insert into program (id,name) values (  4,'MOCAD');
insert into program (id,name) values (  5,'TIIR');

insert into student (id,name,birthdate,entrydate,program_id) values (  1,'Hedi',null,null,2);
insert into student (id,name,birthdate,entrydate,program_id) values (  2,'Badr',null,null,2);
insert into student (id,name,birthdate,entrydate,program_id) values (  3,'Junior',null,null,2);


# --- !Downs

delete from student;
delete from program;
