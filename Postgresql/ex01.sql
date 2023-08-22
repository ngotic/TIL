-- 현재 만들어진 테이블 목록 확인 
select * from pg_tables;

-- 이건 public 스키마에 만들어진다. 
create table sample_table(id integer);

-- 이건 test 스키마에 만들어진다.
create table test.sample_table(id integer);

-- 현재 시간 출력 
select now();
--2023-08-20 17:54:30.942 +0900

-- 테이블 생성 
create table aaaaa(
	id integer
);

-- ::자료형 과 같은 명시로 자료형을 지정할 수 있다. 
select now()::timestamp as t;
select now() ::timestamp as t;
-- 2023-08-20 18:08:20.948

select now()::date as t; -- 2023-08-20
select now()::time as t; -- 18:01:40


select now() ::timestamp        as date1
     , now() + interval '3 day' as date2
     , now() + '3 day' as date2;
-- interval을 생략가능 
-- 2023-08-20 18:03:07.120	2023-08-23 18:03:07.120 +0900	2023-08-23 18:03:07.120 +0900
    
    
select now() + interval '3 d'  
 - now() as interval;
-- 차이만 빼도 된다.
-- 3 days

-- DataType
-- 1. 문자형(Character) 데이터 타입
--   * 다른 데이터베이스랑 유사한데 다름 
--   * char, text, varchar
--   * char(고정길이), varchar(가변길이) : 문자의 크기를 미리 지정해주어야 함. (ex. char(10), varchar(10))
--   * 주의 : char(10)은 10byte가 아니고 10length임. 10자리의 한글이 들어감.
create table bbbbbb( col1 varchar(10) );
insert into bbbbbb (col1) values('일이삼사오륙칠팔구십');
select * from bbbbbb;

create table strtest (str1 varchar(10), str2 char(10));
insert into strtest values('kuma', 'kuma');
insert into strtest values('  kuma  ', '  kuma  ');
select str1, char_length(str1), str2, char_length(str2) from strtest;


-- 가변길이 문자열 str1은 문자열 앞뒤의 공백을 포함하나
-- 고정길이 문자열 str2는 문자열 앞의 공백은 그대로이나, 뒤의 공백은 제거된다. 


-- 2. 숫자형(Numeric) 데이터타입
--   * interger(정수)과 Floating-point numbers(부동 소숫점 숫자)로 나뉨
-- 
/*

smallint: 2byte정수, 범위 : -32,768~32,767
integer : 4byte정수, 범위 : -2,147,483,648 ~ 2.147,483,647
bigint : 8byte정수

numeric이라는 자료형도 있다. 
numeric(p,s) : 소수점 이하의 s 자리수가 있는 p자리의 실수 
> ex.) numeric(10,2)` = 99999999.99
p는 precision, s는 scale이다. 
*/

-- numeric(5, 2) 형이므로 저장할 수 있는 값의 범위는 -999.99에서 +999.99까지이다. 
create table numtest2 (num numeric(5, 2));
select * from numtest2;
-- 이렇게 ,로 한방에 여러개 넣을 수 있다. 
insert into numtest2 values (45), (34.25), (-752.4);
-- values옆에 () 부분만 콤마로 처리해주면 된다. 

-- 범위를 초과하면 오버플로우 에러가 뜬다. 
insert into numtest2 values (1500.2);
--   Detail: 전체 자릿수 5, 소수 자릿수 2의 필드는 10^3보다 작은 절대 값으로 반올림해야 합니다.


-- 3. 부동 소수점 데이터 타입 ( real, double precision )
--   * real	4 바이트	가변 정밀도, 부정확	최소 6 자리의 정밀도 (적어도 1E-37에서 1E + 37)	float4
--   * double precision	8 바이트	가변 정밀도, 부정확	최소 15 자리의 정밀도 (약 1E-307에서 1E + 308)	float8


create table numtest3 (num1 real, num2 double precision);
insert into numtest3 values (15.775, 812.5532245);

-- 4. 시리얼 타입 
--   * smallserial, serial, bigserial 등 
--   * 시리얼은 PostgreSQL이 자동으로 값을 생성해 serial열에 채우는 것을 제외하고는 정수와 같음. auto_increment와 유사하게 동작함

create table numtest4 (id serial, name varchar(10), address varchar(10));
insert into numtest4 (name, address) values ('hello', 'world');
-- values와 테이블 이름 사이에 () 컬럼명 생략가능, 써도 되고 다른 디비랑 비슷 
select * from numtest4;

insert into numtest4 (name, address) values ('hello2', 'world2');
select * from numtest4;
-- 자동으로 id가 증가한다. 

insert into numtest4 values ('hello2', 'world2');
-- 이건 에러가 난다. SQL Error [22P02]: 오류: 정수 자료형 대한 잘못된 입력 구문: "hello2"  Position: 30
-- 일단 생략한다는 것 자체가 모두를 insert 한다는 것을 전제함

insert into numtest4 (id, name, address) values (3,'hello2', 'world2'); -- 직접 숫자 넣는거 가능

insert into numtest4 (name, address) values ('hello3', 'world3'); -- 시리얼이 3부터 시작 직접 넣는것은 인정 안함

-- 5. 날짜 시간형식

-- 아래처럼 넣는다. 
/*
> 날짜
January 8, 1999
1999-01-08
1999-Jan-08
Jan-08-1999
08-Jan-1999
19990108
990108
1999.008      ※ 연도와 그 일까지의 합계

> 시간 
04:05:06.789
04:05:06
04:05
040506
04:05 AM     ※ = 04:05
04:05 PM     ※ = 16:05

04:05:06 PST
04:05:06+09:00
04:05:06+0900
04:05:06+09

January     Jan         ※ 1월
February    Feb         ※ 2월
March       Mar         ※ 3월
April       Apr         ※ 4월
May                     ※ 5월
June        Jun         ※ 6월
July        Jul         ※ 7월
August      Aug         ※ 8월
September   Sep、Sept   ※ 9월
October     Oct         ※10월
November    Nov         ※11월
December    Dec         ※12월

timestamp                     '2004-10-19 10:23:54'
timestamp with time zone      '2004-10-19 10:23:54+09'
date                          '2004-10-19'
time                          '10:23:54'
time with time zone           '10:23:54+09'
 * */

-- 타임존 조회
show timezone;

select now(), current_date, current_time, timeofday(), current_timestamp, timestamp 'now';


select now(), now() - interval'30 minute';
select now(), now() - interval'30 second';
select now(), now() - interval'30 day';
select now(), now() - interval'30 month';
select now(), now() - interval'30 year';

-- date_part 함수로 날짜를 추출할 수 있다. 
select date_part('year', timestamp '2020-07-30 20:38:40');
select date_part('year', current_timestamp);

select extract('year' from date '2006-01-01'); -- 2006
select extract('isoyear' from date '2006-01-01'); -- 2005
select extract('isoyear' from current_timestamp); -- 2023

-- * isoyear
--    > 국제표준시와 그레고리안 달력 간의 차이 때문에 다름



select date_trunc('year', timestamp '2020-07-30 20:38:40');
select date_trunc('year', current_timestamp);
-- trunc 해버려서 나머지 영역은 초기값이 된다. 


select date_part('month', timestamp '2020-07-30 20:38:40');
select date_part('month', current_timestamp);
select extract('month' from timestamp '2020-07-30 20:38:40');

select extract('month' from interval '2 years 3 months'); -- 3 
select extract('year' from interval '2 years 3 months'); -- 2
select extract('month' from interval '2 years 13 months'); -- 1

select date_part('day', timestamp '2020-07-30 20:38:40'); -- 30
select date_part('hour', timestamp '2013-07-30 20:38:40');  -- 20
select date_part('minute', timestamp '2020-07-30 20:38:40');   -- 38
select date_part('second', timestamp '2013-07-30 20:38:40');    -- 40
select extract('century' from timestamp '2000-12-16 12:21:13'); -- 20세기
select extract('week' from timestamp '2000-12-16 12:21:13'); -- 50
select extract('QUARTER' from timestamp '2000-12-16 12:21:13'); -- 4



select extract('doy' from timestamp '2023-01-01 00:38:40'); -- 1 이다.
select extract('isodow' from current_timestamp); -- 월요일(1), 일요일(7) -- 7출력
select extract('doy' from current_timestamp); -- 월요일(1), 일요일(7)     -- 2023-08-20기준 > 232나온다. > 그 해에서 몇번째일인지

-- 6. 논리값 

-- 아래처럼 넣는다. 
/*

TRUE
't'
'true'
'y'
'yes'
'on'
'1'

FALSE
'f'
'false'
'n'
'no'
'off'
'0'

 * 
 * 
 * */

create table booltest (flag boolean);
insert into booltest values (TRUE), ('no'), ('0'), ('yes'), (FALSE);
select * from booltest; -- 테이블에 t, f, f, t, f 이렇게 들어간다. 


-- 7. 네트워크 주소형식도 있다. 


-- mac 주소형식 아래와 같다. 
'08002b:010203'
'08002b-010203'
'0800.2b01.0203'
'08-00-2b-01-02-03'
'08:00:2b:01:02:03'



