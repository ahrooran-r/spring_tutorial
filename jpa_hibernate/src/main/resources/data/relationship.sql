# drop database if exists spring_jpa;
create database if not exists spring_jpa;
use spring_jpa;
drop table if exists subjects, students, passports, reviews, subject_student;

create table subjects
(
    id   int auto_increment,
    name varchar(100) not null unique,
    primary key (id)
);

# https://stackoverflow.com/a/223491/10582056
create table passports
(
    id     int auto_increment,
    number varchar(14) not null unique,
    primary key (id)
);

# relationship between student and passport is one-to-one
# a student can have one passport
# and a passport has only one student

create table students
(
    id          int auto_increment,
    name        varchar(100) not null,
    passport_id int          not null unique,
    primary key (id),
    foreign key (passport_id) references passports (id)
);

# `students` table owns `passports` table because foreign key is in `students` table

# one subject can have many reviews
# but one review only associated with 1 subject
# so one to many -> 1...n -> subjects..reviews
# because review has `foreign key`, it is the owning side of this relationship
create table reviews
(
    id          int           not null auto_increment,
    rating      decimal(1, 0) not null,
    description varchar(255),
    subject_id  int,
    primary key (id),
    foreign key (subject_id) references subjects (id)
);

# one subject has many students
# and one student can take many subjects
# so need a new table
create table subject_student
(
    subject_id int not null,
    student_id int not null,
    foreign key (subject_id) references subjects (id),
    foreign key (student_id) references students (id)
);


insert into subjects(id, name)
values (10001, 'Arts'),
       (10002, 'History'),
       (10003, 'Comp. Sci.'),
       (10004, 'Commerce');

insert into passports(id, number)
values (30001, 'E12345'),
       (30002, 'J54321'),
       (30003, 'B67890');

# because `students` table has `passport_id` as its column, it now owns the relationship
insert into students(id, name, passport_id)
values (20001, 'Adam', 30001),
       (20002, 'Eve', 30002),
       (20003, 'SomeOtherDude', 30003);

insert into reviews(id, rating, description, subject_id)
values (40001, 5, 'Excellent', 10001),
       (40002, 3, 'Good', 10001),
       (40003, 1, 'Wtf! is this :(', 10003);

SET FOREIGN_KEY_CHECKS = 0;
insert into subject_student
values (10001, 20002),
       (10001, 20003),
       (10003, 20002),
       (10002, 20001);
SET FOREIGN_KEY_CHECKS = 1;

# following are equivalent MySQL queries for JPQL ones in JOQLRelationshipTest.class

# select subject which does not have any student
select *
from subjects
where subjects.id not in (select distinct subject_id from subject_student);

select * from subjects join students s on subjects.name = s.name;