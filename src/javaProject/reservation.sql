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
insert into student values('admin','1234','관리자');
-- 등록할것...
insert into student values('202402123','1234','김말숙');
insert into student values('202403290','1234','안소은');
insert into student values('202401010','1234','강서현');

select * from student;


-- 연습실 목록
create table studio (
	studioIdx int not null auto_increment primary key,	/* 고유번호 */
	studioName varchar(10) not null			/* 연습실 이름 */
);

desc studio;
drop table studio;

insert into studio values(default,'피아노A');
insert into studio values(default,'피아노B');
insert into studio values(default,'기타A');
insert into studio values(default,'기타B');
insert into studio values(default,'드럼A');
insert into studio values(default,'드럼B');

select * from studio;


-- 예약 목록
create table reservation(
	studentID char(9) not null,
	studioIdx int not null,
	reservedDate datetime,		/* 예약 날짜, 시간 */
	foreign key (studentID) references student (studentID),
	foreign key (studioIdx) references studio (studioIdx)
);

desc reservation;
drop table reservation;

insert into reservation values(202401001,1,'2024-03-28 17:00:00');
insert into reservation values(202401001,1,'2024-4-2 17:00:00');
select * from reservation;
select date_format(reservedDate,'%y-%m-%d') as selectDate from reservation;
select substring(reservedDate,12,5) as selectTime from reservation;


select reservedDate from reservation where studioIdx = 1;

-- 달력테이블(콤보박스)
create table calendar(
	today datetime default now()		/* 오늘 날짜 */
);
desc calendar;
drop table calendar;
insert into calendar values(default);
select * from calendar;
select concat(year(today),'-',month(today),'-',day(today)) as cbToday from calendar;
select day(last_day(cal)) as lastDay from calendar;		/* 달의 마지막 일자 */

select concat(year(now()),'-',month(now()),'-',day(now()));

select day(last_day('2024-2-1')) as lastDay;

select r.* from reservation r, student s where s.studentID = r.studentID and s.studentID = '202401001' order by reservedDate;
select concat(year(reservedDate),'-',month(reservedDate),'-',day(reservedDate)) as cbreservedDate from reservation;