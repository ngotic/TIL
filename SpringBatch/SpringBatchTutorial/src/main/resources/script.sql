-- person 테이블의 데이터를 person_copy 테이블로 이동시킨다. 
 
drop table person;
drop table person_copy;

CREATE TABLE person(
personId serial not null primary key,
firstName varchar(20) not null, 
lastName varchar(20) not null
);

CREATE TABLE person_copy(
personId serial not null primary key,
firstName varchar(20) not null, 
lastName varchar(20) not null
);

select * from person_copy;

INSERT INTO person (firstName, lastName)
VALUES
('민준', '김'),
('서연', '김'),
('지훈', '김'),
('은지', '김'),
('동현', '김'),
('서준', '김'),
('지원', '김'),
('민서', '김'),
('유진', '김'),
('재원', '김');

INSERT INTO person (firstName, lastName)
VALUES
('승훈', '이'),
('지민', '이'),
('수현', '이'),
('재현', '이'),
('하은', '이'),
('준서', '이'),
('소민', '이'),
('지윤', '이'),
('동진', '이'),
('하린', '이');

INSERT INTO person (firstName, lastName)
VALUES
('준호', '박'),
('현우', '박'),
('서현', '박'),
('민지', '박'),
('도훈', '박'),
('예진', '박'),
('주영', '박'),
('준영', '박'),
('민성', '박'),
('정원', '박');

select * from person;
select * from person_copy;

SELECT *  FROM  person
		WHERE personid <= 10;
		
	
	
	SELECT *  FROM  person
		ORDER BY lastname
		LIMIT 5
	    offset 1