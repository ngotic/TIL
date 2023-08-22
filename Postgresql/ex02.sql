-- 새 데이터베이스 생성하기 
CREATE DATABASE name; 


/* 형식은 이렇다. 
CREATE DATABASE name
    [ [ WITH ] [ OWNER [=] user_name ]
           [ TEMPLATE [=] template ]
           [ ENCODING [=] encoding ]
           [ LC_COLLATE [=] lc_collate ]
           [ LC_CTYPE [=] lc_ctype ]
           [ TABLESPACE [=] tablespace_name ]
           [ ALLOW_CONNECTIONS [=] allowconn ]
           [ CONNECTION LIMIT [=] connlimit ]
           [ IS_TEMPLATE [=] istemplate ] ]
*/

-- \l로 psql 열고 조회 


create database testdbcreate
template template0
encoding UTF8
lc_collate 'Korean_Korea.949'
lc_ctype 'Korean_Korea.949';
-- 문자 셋으로 UTF8 하고 문자열 정렬 순서과 문자 분류로 ‘Korean_Korea.949’을 설정하여 데이터베이스를 만든다.


-- psql에서 \c testdbcreate 입력하면 연결되고, \c(connect)


-- 현재 연결된 db 조회 
select current_database();


-- 데이터베이스 설정, 이름, 소유자 변경 등 가능하다. ALTER 명령어로 가능 
alter database hamster connection limit 3; -- 커넥션 변경
ALTER DATABASE name RENAME TO new_name  -- 이름 변경 


-- 데이터베이스들 조회가능 
select datname from pg_database;


-- 데이터베이스 삭제 
drop database testdbcreate;
select datname from pg_database;

-- 스키마 생성 
CREATE SCHEMA schema_name;

--psql에서  \dn으로 확인

-- 스키마 삭제
drop schema schema_name;


/*
psql -U postgres -d dev
postgres 유저로 접속 dev 데이터베이스에 연결


create table apple.blog (id integer);

apple이라는 스키마에 블로그 테이블 생성

*/