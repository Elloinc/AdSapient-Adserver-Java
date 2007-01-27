# Table structure for table 'publish_types'
#

drop table publish_types;
CREATE TABLE publish_types (
   ID bigint not null,
   TYPE varchar (240),
   PRIMARY KEY (ID)
);


#
# Dumping data for table 'plugins'
#
insert into publish_types(ID,TYPE) values(1,'web');
