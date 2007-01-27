
#
# Table structure for table 'roles'
#
DROP TABLE if exists roles;
CREATE TABLE  roles (
   ROLE_ID int(11) NOT NULL  ,
   ROLE_NAME  varchar (200) NOT NULL  ,
  ALLOW varchar(2), 
  PRIMARY KEY (ROLE_ID)
);


#
# Dumping data for table 'roles'
#
insert into roles values (1,'publisher',1);
insert into roles values (2,'advertiser',1);
#() these roles are available for registration, but since we don't need them we should comment out them here
#insert into roles values (3,'advertiserpublisher',1);
#insert into roles values (4,'admin',1);
