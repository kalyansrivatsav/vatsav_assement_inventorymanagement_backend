create table factory(
   id  BIGINT auto_increment primary key,
  name varchar(20) not null,
  location varchar(20) not null
 );
  
create table product(
   id  BIGINT auto_increment primary key,
  name varchar(20) not null,
  quantity integer not null,
  type varchar(20) not null,
  filename varchar(256),
  factoryid integer,
  foreign key(factoryid) references factory(id)
 );
