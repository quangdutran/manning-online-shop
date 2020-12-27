create table item (
 id serial primary key,
 sku varchar(32) unique ,
 name varchar(256) not null,
 description text,
 price decimal not null,
 created_by varchar(256),
 updated_by varchar(256),
 created_on date,
 updated_on date
)