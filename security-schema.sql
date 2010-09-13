--Schema for spring security authorization
create table users(
      username varchar2(50) not null primary key ,
      password varchar2(50) ,
      description varchar2(50),
      enabled number(1,0)  not null check  ( enabled in (1, 0))      );

create table groups (
  id number(19,0)  primary key,
  group_name varchar2(50) not null ,
  description varchar2(50),
  unique(group_name)
  );

create table group_authorities (
  id number(19,0) primary key,
  group_id number(19,0) not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
  id number(19,0)  primary key,
  username varchar2(50) not null,
  group_id number(19,0) not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id),
  constraint fk_group_members_users foreign key(username) references users(username));

CREATE SEQUENCE groups_seq ;
CREATE SEQUENCE group_authorities_seq ;
CREATE SEQUENCE group_members_seq ;  