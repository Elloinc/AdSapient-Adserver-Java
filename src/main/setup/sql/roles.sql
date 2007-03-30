DROP TABLE roles;
CREATE TABLE  roles (
   ROLE_ID bigint NOT NULL  ,
   ROLE_NAME  varchar (200) NOT NULL  ,
  ALLOW varchar(10),
  PRIMARY KEY (ROLE_ID)
);
insert into roles (ROLE_ID,ROLE_NAME,ALLOW) values (1,'publisher','true');
insert into roles (ROLE_ID,ROLE_NAME,ALLOW) values (2,'advertiser','true');

