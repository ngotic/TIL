create table test_user_my(
	user_id varchar(20),
	name varchar(10)
);

insert into test_user_my values('test','nam');
insert into test_user_my values('test2');

select * from test_user_my;

select coalesce(name, '익명') from test_user_my; -- null이면 두번쨰 인자로 매칭 

select * from payment limit 5; -- 5개만 출력 




-- * CASE문, ORACLE의 DECODE는 없다. 
-- case와 end는 쌍
select payment_id 
	, case when amount > 5.00 then '많아'
		   when amount < 5.00 and amount > 3.00 then '중간'
		   else '적어'
	  end as "정도"
	from payment ; 

select *
from staff s 
inner join payment p
on p.staff_id=s.staff_id ;

-- inner join인데 inner 생략가능
select *
from staff s 
join payment p
on p.staff_id=s.staff_id ;


/*
SELECT [ ALL | DISTINCT [ ON ( expression [, ...] ) ] ]
    [ * | expression [ [ AS ] output_name ] [, ...] ]
    [ FROM from_item [, ...] ]
    [ WHERE condition ]
    [ GROUP BY expression [, ...] ]
    [ HAVING condition [, ...] ]
    [ WINDOW window_name AS ( window_definition ) [, ...] ]
    [ { UNION | INTERSECT | EXCEPT } [ ALL | DISTINCT ] select ]
    [ ORDER BY expression [ ASC | DESC | USING operator ] [ NULLS { FIRST | LAST } ] [, ...] 
    [ LIMIT { count | ALL } ]
    [ OFFSET start [ ROW | ROWS ] ]
*/

-- 문자열 매칭 like
select * from customer c where last_name like 'Y%';

create table numTest(num integer);

insert into numTest values(11),(12),(13),(14),(15), (16), (17), (18), (19), (20);


-- * LIMIT 
--   > 정렬 후 리밋 걸기 
select * from payment order by rental_id  asc limit 20; -- 20 개만 출력

-- offset은 '이정도'는 건너뛰어라를 말한다. 
-- order by로 한 턴 정렬하고 리밋
select * from numTest order by num limit 5 offset 2; -- 2+1 번째 부터 5개 리밋까지 출력 

-- mysql은 limit N, N이 되나 postgresql은 안된다. 

-- 문자열 길이는 char_length를 쓴다. 
select char_length('hello') ;



