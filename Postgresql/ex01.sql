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

