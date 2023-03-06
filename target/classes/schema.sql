create table factory(
   id  BIGINT IDENTITY(1,1) primary key,
  name varchar(20) not null,
  location varchar(20) not null
 );
  
create table product(
   id  BIGINT IDENTITY(1,1) primary key,
  name varchar(20) not null,
  quantity integer not null,
  ordered_quantity integer,
  type varchar(20) not null,
  filename varchar(256),
  factoryid integer,
  foreign key(factoryid) references factory(id)
 );

create table [order](
  id BIGINT IDENTITY(1,1) primary key,
  productid bigint not null,
  factoryid bigint not null,
  ordered_quantity integer,
  CreatedDate datetime,
  foreign key(productid) references product(id),
  foreign key(factoryid) references factory(id)
 )