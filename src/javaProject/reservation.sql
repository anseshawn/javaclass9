show tables;

-- 학생 목록
create table student (
	studentID char(9) not null primary key,		/* 학번 */
	studentPW varchar(20) not null,				/* 비밀번호 */
	studentName varchar(10) not null			/* 이름 */
);

desc student;
drop table student;

insert into student values('202401001','1234','홍길동');
insert into student values('admin','1234','관리자')

select * from student;


-- 연습실 목록
create table studio (
	studioIdx int not null auto_increment primary key,	/* 고유번호 */
	studioName varchar(10) not null			/* 연습실 이름 */
);

desc studio;
drop table studio;

insert into studio values(default,'피아노A');

select * from studio;


-- 예약 목록
create table reservation(
	studentID char(9) not null,
	studioIdx int not null,
	reservedTime datetime,		/* 예약 날짜, 시간 */
	foreign key (studentID) references student (studentID),
	foreign key (studioIdx) references studio (studioIdx)
);

desc reservation;
drop table reservation;

insert into reservation values(202401001,1,'2024-03-28 17:00:00');
select * from reservation;
